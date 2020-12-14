package com.dbmsproject.imdb;

import com.dbmsproject.imdb.enums.COLLECTIONS;
import com.dbmsproject.imdb.enums.STATUS_CODE;
import com.dbmsproject.imdb.model.Actor;
import com.dbmsproject.imdb.model.Movie;
import com.dbmsproject.imdb.model.Review;
import com.dbmsproject.imdb.model.User;
import com.dbmsproject.imdb.repositories.ActorRepository;
import com.dbmsproject.imdb.repositories.MovieRepository;
import com.dbmsproject.imdb.repositories.ReviewRepository;
import com.dbmsproject.imdb.repositories.UserRepository;
import com.dbmsproject.imdb.requestbodies.EntryBody;
import com.dbmsproject.imdb.responsebody.EndBody;
import com.dbmsproject.imdb.responsebody.MiniMovieBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class MovieDatabaseHandler {

    @Autowired
    public MovieRepository movieRepository;

    @Autowired
    public ActorRepository actorRepository;

    @Autowired
    public ReviewRepository reviewRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public MongoOperations mongoOperations;

    @PostMapping(value = "/add-movie", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EndBody addMovie(@RequestBody EntryBody entryBody) {
        Actor actorIds;
        Review reviewIds;
        ArrayList<String> actorIdsString = new ArrayList<>();
        ArrayList<String> reviewIdsString = new ArrayList<>();

        Movie alreadyMovie = mongoOperations.findOne(Query.query(Criteria.where("title").is(entryBody.getTitle()))
                .addCriteria(Criteria.where("released").is(entryBody.getReleased())), Movie.class, COLLECTIONS.MOVIES.getValue());


        if(alreadyMovie == null) {
            for (Actor actor: entryBody.getActors()) {
                Actor already = mongoOperations.findOne(Query.query(Criteria.where("name").is(actor.getName()))
                        .addCriteria(Criteria.where("dob").is(actor.getDob())), Actor.class, COLLECTIONS.ACTORS.getValue());
                if (already == null) {
                    actorIds = actorRepository.insert(actor);
                    actorIdsString.add(actorIds.get_id());
                } else {
                    actorIdsString.add(already.get_id());
                }
            }

            if(entryBody.getReviews() != null) {
                for (Review review: entryBody.getReviews()) {
                    Review already = mongoOperations.findOne(Query.query(Criteria.where("userId").is(review.getUserId()))
                            .addCriteria(Criteria.where("movieId").is(review.getMovieId())), Review.class, COLLECTIONS.REVIEWS.getValue());
                    if(already == null) {
                        reviewIds = reviewRepository.insert(review);
                        reviewIdsString.add(reviewIds.get_id());
                    } else {
                        reviewIdsString.add(already.get_id());
                    }
                }
            }

            Movie movie = new Movie(entryBody);
            movie.setActors(actorIdsString);
            movie.setReviews(reviewIdsString);
            movieRepository.insert(movie);
            return new EndBody(STATUS_CODE.INSERTED.getI(), "Movie is Added");
        } else {
            return new EndBody(STATUS_CODE.ALREADY_FOUND.getI(), "Same movie with title and released date found in database");
        }
    }

    @GetMapping(value = "/get-movie-minified", params = {"title"})
    public ArrayList<MiniMovieBody> getMovieMinified(@Param("title") String title) {
        ArrayList<Movie> movies = (ArrayList<Movie>) movieRepository.findAll();
        ArrayList<MiniMovieBody> miniMovies = new ArrayList<>();
        for(Movie movie : movies) {
            if(movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
                miniMovies.add(new MiniMovieBody(movie.getTitle(), movie.getPoster()));
            }
        }
        return miniMovies;
    }

    @GetMapping(value = "/get-movie")
    public ArrayList<EntryBody> getMovies() {
        ArrayList<Movie> movies = (ArrayList<Movie>) movieRepository.findAll();
        ArrayList<EntryBody> givingBody = new ArrayList<>();

        for(Movie movie : movies) {
            ArrayList<Actor> actors = (ArrayList<Actor>) actorRepository.findAllById(movie.getActors());
            ArrayList<Review> reviews = (ArrayList<Review>) reviewRepository.findAllById(movie.getReviews());
            for(Review review: reviews) {
                Optional<Review> optionalReview =  reviewRepository.findById(review.get_id());
                if(optionalReview.isPresent()) {
                    Optional<User> userOptional = userRepository.findById(optionalReview.get().getUserId());
                    userOptional.ifPresent(user -> review.setUserId(user.getName()));
                }
            }
            givingBody.add(new EntryBody(movie, actors, reviews));
        }
        return givingBody;
    }

    @GetMapping(value = "/get-movie", params = {"title"})
    public ArrayList<EntryBody> getMovies(@Param("title") String title) {
        ArrayList<Movie> movies = (ArrayList<Movie>) movieRepository.findAll();
        ArrayList<EntryBody> givingBody = new ArrayList<>();
        for(Movie movie: movies) {
            if(movie.getTitle().equalsIgnoreCase(title)) {
                ArrayList<Actor> actors = (ArrayList<Actor>) actorRepository.findAllById(movie.getActors());
                ArrayList<Review>  reviews = (ArrayList<Review>) reviewRepository.findAllById(movie.getReviews());
                for(Review review: reviews) {
                    Optional<Review> optionalReview =  reviewRepository.findById(review.get_id());
                    if(optionalReview.isPresent()) {
                        Optional<User> userOptional = userRepository.findById(optionalReview.get().getUserId());
                        userOptional.ifPresent(user -> review.setUserId(user.getName()));
                    }
                }
                givingBody.add(new EntryBody(movie, actors, reviews));
            }
        }
        return givingBody;
    }
}
