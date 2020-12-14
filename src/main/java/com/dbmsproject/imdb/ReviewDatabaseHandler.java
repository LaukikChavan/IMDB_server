package com.dbmsproject.imdb;

import com.dbmsproject.imdb.enums.COLLECTIONS;
import com.dbmsproject.imdb.enums.STATUS_CODE;
import com.dbmsproject.imdb.model.Movie;
import com.dbmsproject.imdb.model.Review;
import com.dbmsproject.imdb.model.User;
import com.dbmsproject.imdb.repositories.MovieRepository;
import com.dbmsproject.imdb.repositories.ReviewRepository;
import com.dbmsproject.imdb.repositories.UserRepository;
import com.dbmsproject.imdb.requestbodies.PostRequestBody;
import com.dbmsproject.imdb.responsebody.EndBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class ReviewDatabaseHandler {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public MovieRepository movieRepository;

    @Autowired
    public ReviewRepository reviewRepository;

    @Autowired
    public MongoOperations mongoOperations;

    @PostMapping(value = "/review", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EndBody post(@RequestBody PostRequestBody postRequestBody) {
        Optional<User> optionalUser = userRepository.findById(postRequestBody.getUserId());
        Optional<Movie> optionalMovie = movieRepository.findById(postRequestBody.getMovieId());
        if(!optionalUser.isPresent() && !optionalMovie.isPresent()) {
            return new EndBody(STATUS_CODE.NOT_FOUND.getI(), "User and Movie not found");
        } else if(!optionalUser.isPresent()) {
            return new EndBody(STATUS_CODE.NOT_FOUND.getI(), "User not found");
        } else if(!optionalMovie.isPresent()) {
            return new EndBody(STATUS_CODE.NOT_FOUND.getI(), "Movie not found");
        } else {
            Review already = mongoOperations.findOne(Query.query(Criteria.where("userId").is(postRequestBody.getUserId()))
                        .addCriteria(Criteria.where("movieId").is(postRequestBody.getMovieId())), Review.class, COLLECTIONS.REVIEWS.getValue());
            Movie movie = optionalMovie.get();
            if(already == null) {
                Review review = new Review(postRequestBody);
                review = reviewRepository.insert(review);
                movie.getReviews().add(review.get_id());
                ArrayList<Review> reviews = (ArrayList<Review>) reviewRepository.findAllById(movie.getReviews());
                double rating = 0.0;
                for(Review e : reviews) {
                    try {
                        rating += Double.parseDouble(e.getRating());
                    } catch (Exception exception) {
                        return new EndBody(STATUS_CODE.ERROR.getI(), "Rating should be in Double given is not a Double : " + e.getRating());
                    }
                }
                rating /= reviews.size();
                movie.setRating(String.valueOf(rating));
                movieRepository.save(movie);
                return new EndBody(STATUS_CODE.INSERTED.getI(), "Review is created");
            } else {
                if(!movie.getReviews().contains(already.get_id())) {
                    movie.getReviews().add(already.get_id());
                    movieRepository.save(movie);
                }
                return new EndBody(STATUS_CODE.ALREADY_FOUND.getI(), "Review was already there");
            }
        }
    }

    @PostMapping(value = "/update-review", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EndBody updateReview(@RequestBody Review review) {
        Optional<Review> optionalReview = reviewRepository.findById(review.get_id());
        if(optionalReview.isPresent()) {
            reviewRepository.save(review);
            return new EndBody(STATUS_CODE.UPDATED.getI(), "Review Updated successfully");
        } else {
            return new EndBody(STATUS_CODE.NOT_FOUND.getI(), "Review With this id not found please create one 1st");
        }
    }
}
