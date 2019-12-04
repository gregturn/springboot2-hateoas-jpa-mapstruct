package com.asimio.demo.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asimio.demo.domain.Actor;
import com.asimio.demo.domain.FilmActor;
import com.asimio.demo.rest.exception.ResourceNotFoundException;
import com.asimio.demo.rest.mapper.ActorResourceMapper;
import com.asimio.demo.rest.mapper.FilmActorResourceMapper;
import com.asimio.demo.rest.model.ActorResource;
import com.asimio.demo.rest.model.FilmResource;
import com.asimio.demo.service.DvdRentalService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/actors", produces = { MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE })
public class ActorController {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;

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
    public Resources<FilmResource> retrieveActorFilms(@PathVariable Integer id,
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE) Pageable pageRequest,
            PagedResourcesAssembler<FilmActor> pagedResourcesAssembler) {

        Actor actor = this.dvdRentalService.retrieveActor(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Actor with id=%s not found", id)));
        List<FilmResource> resources = FilmActorResourceMapper.INSTANCE.map(actor.getFilmActors());
        Resources<FilmResource> result = new Resources<>(resources);
        // Adding root level links: self=/api/actors/1/films, parent=/api/actors/1 in addition to each film's self link
        this.addLinks(result, actor);
        return result;
    }

    private void addLinks(Resources<FilmResource> resources, Actor actor) {
        // parent link
        Link parentLink = ControllerLinkBuilder.linkTo(ActorController.class)
                .slash(actor)
                .withRel("parent");
        // self link
        Link selfLink = ControllerLinkBuilder.linkTo(ActorController.class)
                .slash(actor)
                .slash("films")
                .withSelfRel();
        resources.add(parentLink, selfLink);
    }
}