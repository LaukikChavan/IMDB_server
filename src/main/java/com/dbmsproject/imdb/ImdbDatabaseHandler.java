package com.dbmsproject.imdb;

import com.dbmsproject.imdb.enums.DB_NAMES;
import com.dbmsproject.imdb.enums.RESPONSE_CODE;
import com.dbmsproject.imdb.model.Actor;
import com.dbmsproject.imdb.model.Movie;
import com.dbmsproject.imdb.model.User;
import com.dbmsproject.imdb.repositories.ActorRepository;
import com.dbmsproject.imdb.repositories.MovieRepository;
import com.dbmsproject.imdb.repositories.UserRepository;
import com.dbmsproject.imdb.requestbodies.LikeEntryBody;
import com.dbmsproject.imdb.requestbodies.SignInEntryBody;
import com.dbmsproject.imdb.requestbodies.UserEntryBody;
import com.dbmsproject.imdb.responsebody.UserResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
    public UserResponseBody signUp(@RequestBody UserEntryBody userEntryBody) {
        UserResponseBody responseBody = new UserResponseBody();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = mongoOperations.findOne(
                Query.query(Criteria.where("email").is(userEntryBody.getEmail())),
                User.class,
                DB_NAMES.USERS.toString());
        if(user  == null) {
            user = userRepository.insert(new User(userEntryBody.getName(), userEntryBody.getLikedMovies(), encoder.encode(userEntryBody.getPassword()), userEntryBody.getEmail()));
            responseBody.setCode(RESPONSE_CODE.SUCCESSFUL);
            responseBody.setRes(user.get_id());
        } else {
            responseBody.setCode(RESPONSE_CODE.ALREADYPRESENT);
            responseBody.setRes("User is Already Present please try to sign in");
        }
        return responseBody;
    }

    @PostMapping(value = "/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResponseBody signIn(@RequestBody SignInEntryBody signInEntryBody) {
        UserResponseBody responseBody = new UserResponseBody();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = mongoOperations.findOne(
                Query.query(Criteria.where("email").is(signInEntryBody.getEmail())),
                User.class,
                DB_NAMES.USERS.toString());
        if(user  != null) {
            if(encoder.matches(signInEntryBody.getPassword(), user.getPassword())) {
                responseBody.setCode(RESPONSE_CODE.SUCCESSFUL);
                responseBody.setRes(user.get_id());
            } else {
                responseBody.setCode(RESPONSE_CODE.ERROR);
                responseBody.setRes("Password is Wrong");
            }
        } else {
            responseBody.setCode(RESPONSE_CODE.ERROR);
            responseBody.setRes("There is No one with this Email _id");
        }
        return responseBody;
    }

    @PostMapping(value = "/like", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String like(@RequestBody LikeEntryBody likeEntryBody) {
        Optional<User> optionalUser = userRepository.findById(likeEntryBody.getUserId());
        Optional<Movie> optionalMovie = movieRepository.findById(likeEntryBody.getMovieId());
        if(!optionalUser.isPresent() && !optionalMovie.isPresent()) {
            return "Movie and User not found";
        }else if(!optionalUser.isPresent()) {
            return "User Not found";
        }else if(!optionalMovie.isPresent()) {
            return "Movie Not found";
        } else {
            User user = optionalUser.get();
            Movie movie = optionalMovie.get();
            if(!user.getLikedMovies().contains(movie.get_id())) {
                user.getLikedMovies().add(movie.get_id());
                movie.setLikes(movie.getLikes() + 1);
                userRepository.save(user);
                movieRepository.save(movie);
            }
        }
        return "done";
    }

    @PostMapping(value = "/dislike", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String unlike(@RequestBody LikeEntryBody likeEntryBody) {
        Optional<User> optionalUser = userRepository.findById(likeEntryBody.getUserId());
        Optional<Movie> optionalMovie = movieRepository.findById(likeEntryBody.getMovieId());
        if(!optionalUser.isPresent() && !optionalMovie.isPresent()) {
            return "Movie and User not found";
        }else if(!optionalUser.isPresent()) {
            return "User Not found";
        }else if(!optionalMovie.isPresent()) {
            return "Movie Not found";
        } else {
            User user = optionalUser.get();
            Movie movie = optionalMovie.get();
            if(user.getLikedMovies().contains(movie.get_id())) {
                user.getLikedMovies().remove(movie.get_id());
                movie.setLikes(movie.getLikes() - 1);
                userRepository.save(user);
                movieRepository.save(movie);
            }
        }
        return "done";
    }

}
