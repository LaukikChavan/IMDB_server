package com.dbmsproject.imdb;

import com.dbmsproject.imdb.model.Actor;
import com.dbmsproject.imdb.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ActorDatabaseHandler {

    @Autowired
    public ActorRepository actorRepository;

    @PostMapping(value = "/add-actor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void addActor(@RequestBody Actor actor) {
        actorRepository.insert(actor);
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
