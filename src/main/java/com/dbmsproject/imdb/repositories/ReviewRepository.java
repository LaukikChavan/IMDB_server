package com.dbmsproject.imdb.repositories;

import com.dbmsproject.imdb.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {
}
