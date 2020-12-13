package com.dbmsproject.imdb.repositories;

import com.dbmsproject.imdb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
