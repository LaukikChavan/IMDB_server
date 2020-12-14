package com.dbmsproject.imdb;

import com.dbmsproject.imdb.enums.COLLECTIONS;
import com.dbmsproject.imdb.enums.STATUS_CODE;
import com.dbmsproject.imdb.model.Actor;
import com.dbmsproject.imdb.model.Movie;
import com.dbmsproject.imdb.model.User;
import com.dbmsproject.imdb.repositories.ActorRepository;
import com.dbmsproject.imdb.repositories.MovieRepository;
import com.dbmsproject.imdb.repositories.UserRepository;
import com.dbmsproject.imdb.requestbodies.LikeEntryBody;
import com.dbmsproject.imdb.requestbodies.SignInEntryBody;
import com.dbmsproject.imdb.requestbodies.UserEntryBody;
import com.dbmsproject.imdb.responsebody.EndBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.EndDocument;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class ImdbDatabaseHandler {

    @Autowired
    public MovieRepository movieRepository;

    @Autowired
    public ActorRepository actorRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public MongoOperations mongoOperations;

    @GetMapping(value = "/")
    public List<Actor> connectionToMongo() {
        return actorRepository.findAll();
    }

    @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EndBody signUp(@RequestBody UserEntryBody userEntryBody) {
        EndBody responseBody = new EndBody();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = mongoOperations.findOne(
                Query.query(Criteria.where("email").is(userEntryBody.getEmail())),
                User.class,
                COLLECTIONS.USERS.getValue());
        if(user  == null) {
            user = userRepository.insert(new User(userEntryBody.getName(), userEntryBody.getLikedMovies(), encoder.encode(userEntryBody.getPassword()), userEntryBody.getEmail()));
            responseBody.setStatus(STATUS_CODE.INSERTED.getI());
            responseBody.setMessage(user.get_id());
        } else {
            responseBody.setStatus(STATUS_CODE.ALREADY_FOUND.getI());
            responseBody.setMessage("User is Already Present please try to sign in");
        }
        return responseBody;
    }

    @PostMapping(value = "/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EndBody signIn(@RequestBody SignInEntryBody signInEntryBody) {
        EndBody responseBody = new EndBody();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = mongoOperations.findOne(
                Query.query(Criteria.where("email").is(signInEntryBody.getEmail())),
                User.class,
               COLLECTIONS.USERS.getValue());
        if(user  != null) {
            if(encoder.matches(signInEntryBody.getPassword(), user.getPassword())) {
                responseBody.setStatus(STATUS_CODE.INSERTED.getI());
                responseBody.setMessage(user.get_id());
            } else {
                responseBody.setStatus(STATUS_CODE.WRONG.getI());
                responseBody.setMessage("Password is Wrong");
            }
        } else {
            responseBody.setStatus(STATUS_CODE.NOT_FOUND.getI());
            responseBody.setMessage("There is No one with this Email id");
        }
        return responseBody;
    }

    @PostMapping(value = "/like", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EndBody like(@RequestBody LikeEntryBody likeEntryBody) {
        Optional<User> optionalUser = userRepository.findById(likeEntryBody.getUserId());
        Optional<Movie> optionalMovie = movieRepository.findById(likeEntryBody.getMovieId());
        if(!optionalUser.isPresent() && !optionalMovie.isPresent()) {
            return new EndBody(STATUS_CODE.NOT_FOUND.getI(), "Movie and User not Found");
        }else if(!optionalUser.isPresent()) {
            return new EndBody(STATUS_CODE.NOT_FOUND.getI(), "User not Found");
        }else if(!optionalMovie.isPresent()) {
            return new EndBody(STATUS_CODE.NOT_FOUND.getI(), "Movie not Found");
        } else {
            User user = optionalUser.get();
            Movie movie = optionalMovie.get();
            if(!user.getLikedMovies().contains(movie.get_id())) {
                user.getLikedMovies().add(movie.get_id());
                movie.setLikes(movie.getLikes() + 1);
                userRepository.save(user);
                movieRepository.save(movie);
                return new EndBody(STATUS_CODE.INSERTED.getI(), "Like is added");
            } else {
                return new EndBody(STATUS_CODE.NOT_FOUND.getI(), "There is no a like with some data");
            }
        }
    }

    @PostMapping(value = "/dislike", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EndBody unlike(@RequestBody LikeEntryBody likeEntryBody) {
        Optional<User> optionalUser = userRepository.findById(likeEntryBody.getUserId());
        Optional<Movie> optionalMovie = movieRepository.findById(likeEntryBody.getMovieId());
        if(!optionalUser.isPresent() && !optionalMovie.isPresent()) {
            return new EndBody(STATUS_CODE.NOT_FOUND.getI(), "Movie and User not Found");
        }else if(!optionalUser.isPresent()) {
            return new EndBody(STATUS_CODE.NOT_FOUND.getI(), "User not Found");
        }else if(!optionalMovie.isPresent()) {
            return new EndBody(STATUS_CODE.NOT_FOUND.getI(), "Movie not Found");
        } else {
            User user = optionalUser.get();
            Movie movie = optionalMovie.get();
            if(user.getLikedMovies().contains(movie.get_id())) {
                user.getLikedMovies().remove(movie.get_id());
                movie.setLikes(movie.getLikes() - 1);
                userRepository.save(user);
                movieRepository.save(movie);
                return new EndBody(STATUS_CODE.DELETED.getI(), "Like is removed from database");
            } else {
                return new EndBody(STATUS_CODE.NOT_FOUND.getI(), "There is no a like with some data");
            }
        }
    }

}
