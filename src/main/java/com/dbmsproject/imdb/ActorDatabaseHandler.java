package com.dbmsproject.imdb;

import com.dbmsproject.imdb.enums.COLLECTIONS;
import com.dbmsproject.imdb.enums.STATUS_CODE;
import com.dbmsproject.imdb.model.Actor;
import com.dbmsproject.imdb.repositories.ActorRepository;
import com.dbmsproject.imdb.responsebody.EndBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
public class ActorDatabaseHandler {

    @Autowired
    public ActorRepository actorRepository;

    @Autowired
    public MongoOperations mongoOperations;

    @PostMapping(value = "/add-actor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EndBody addActor(@RequestBody Actor actor) {
        Actor already = mongoOperations.findOne(Query.query(Criteria.where("name").is(actor.getName()))
                .addCriteria(Criteria.where("dob").is(actor.getDob())), Actor.class, COLLECTIONS.ACTORS.getValue());
        if(already == null) {
            actorRepository.insert(actor);
            return new EndBody(STATUS_CODE.INSERTED.getI(), "Actor is Added");
        } else {
            return new EndBody(STATUS_CODE.ALREADY_FOUND.getI(), "Actor is already present in database");
        }
    }

    @GetMapping(value = "/get-actor")
    public List<Actor> getActor() {
        return actorRepository.findAll();
    }

    @GetMapping(value = "/get-actor-minified", params = {"name"})
    public List<Actor> getActor(@Param("name") String name) {
        ArrayList<Actor> actors = new ArrayList<>();
        for(Actor actor : actorRepository.findAll()) {
            if(actor.getName().toLowerCase().contains(name.toLowerCase()))
                actors.add(actor);
        }
        return actors;
    }
}
