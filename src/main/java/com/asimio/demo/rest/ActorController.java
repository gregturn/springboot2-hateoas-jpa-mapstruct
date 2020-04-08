package com.asimio.demo.rest;

import com.asimio.demo.domain.Actor;
import com.asimio.demo.rest.exception.ResourceNotFoundException;
import com.asimio.demo.rest.mapper.ActorResourceMapper;
import com.asimio.demo.rest.mapper.FilmActorResourceMapper;
import com.asimio.demo.rest.model.ActorResource;
import com.asimio.demo.rest.model.FilmResource;
import com.asimio.demo.service.DvdRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final DvdRentalService dvdRentalService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ActorResource> retrieveActor(@PathVariable Integer id) {
        Optional<Actor> optionalActor = this.dvdRentalService.retrieveActor(id);
        return optionalActor.map(actor -> {
            ActorResource resource = ActorResourceMapper.INSTANCE.map(actor);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Actor with id=%s not found", id)));
    }

    @GetMapping(value = "/{id}/films")
    public CollectionModel<FilmResource> retrieveActorFilms(@PathVariable Integer id) {
        Actor actor = this.dvdRentalService.retrieveActor(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Actor with id=%s not found", id)));
        List<FilmResource> CollectionModel = FilmActorResourceMapper.INSTANCE.map(actor.getFilmActors());
        CollectionModel<FilmResource> result = new CollectionModel<>(CollectionModel);
        // Adding root level links: self=/api/actors/1/films, parent=/api/actors/1 in addition to each film's self link
        this.addLinks(result, actor);
        return result;
    }

    private void addLinks(CollectionModel<FilmResource> CollectionModel, Actor actor) {
        // parent link
        Link parentLink = linkTo(methodOn(ActorController.class).retrieveActor(actor.getId())).withRel("parent");
        // self link
        Link selfLink = linkTo(methodOn(ActorController.class).retrieveActorFilms(actor.getId())).withSelfRel();
        CollectionModel.add(parentLink, selfLink);
    }
}
