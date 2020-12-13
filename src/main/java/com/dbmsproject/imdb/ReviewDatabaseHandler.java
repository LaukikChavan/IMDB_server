package com.dbmsproject.imdb;

import com.dbmsproject.imdb.model.Movie;
import com.dbmsproject.imdb.model.Review;
import com.dbmsproject.imdb.model.User;
import com.dbmsproject.imdb.repositories.MovieRepository;
import com.dbmsproject.imdb.repositories.ReviewRepository;
import com.dbmsproject.imdb.repositories.UserRepository;
import com.dbmsproject.imdb.requestbodies.PostRequestBody;
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
    public String post(@RequestBody PostRequestBody postRequestBody) {
        Optional<User> optionalUser = userRepository.findById(postRequestBody.getUserId());
        Optional<Movie> optionalMovie = movieRepository.findById(postRequestBody.getMovieId());
        if(!optionalUser.isPresent() && !optionalMovie.isPresent()) {
            return "Movie and User not found";
        } else if(!optionalUser.isPresent()) {
            return "User Not found";
        } else if(!optionalMovie.isPresent()) {
            return "Movie Not found";
        } else {
            Review already = mongoOperations.findOne(Query.query(Criteria.where("userId").is(postRequestBody.getUserId()))
                        .addCriteria(Criteria.where("movieId").is(postRequestBody.getMovieId())), Review.class, "reviews");
            Movie movie = optionalMovie.get();
            if(already == null) {
                Review review = new Review(postRequestBody);
                review = reviewRepository.insert(review);
                movie.getReviews().add(review.get_id());
                ArrayList<Review> reviews = (ArrayList<Review>) reviewRepository.findAllById(movie.getReviews());
                double rating = 0.0;
                for(Review e : reviews) {
                    rating += Double.parseDouble(e.getRating());
                }
                rating /= reviews.size();
                movie.setRating(String.valueOf(rating));
                movieRepository.save(movie);
                return "Entre new rating";
            } else {
                if(!movie.getReviews().contains(already.get_id())) {
                    movie.getReviews().add(already.get_id());
                    movieRepository.save(movie);
                }
            }
        }
        return "done";
    }

    @PostMapping(value = "/update-review", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateReview(@RequestBody Review review) {
        reviewRepository.save(review);
        return "Done";
    }
}
