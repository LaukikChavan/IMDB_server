package com.dbmsproject.imdb.repositories;

import com.dbmsproject.imdb.model.Actor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActorRepository extends MongoRepository<Actor, String> {
}
