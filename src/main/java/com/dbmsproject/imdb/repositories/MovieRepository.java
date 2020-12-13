package com.dbmsproject.imdb.repositories;

import com.dbmsproject.imdb.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
