package com.asimio.demo.rest.mapper;

import com.asimio.demo.domain.Actor;
import com.asimio.demo.rest.ActorController;
import com.asimio.demo.rest.model.ActorResource;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Mapper
public interface ActorResourceMapper extends ResourceMapper<Actor, ActorResource> {

    ActorResourceMapper INSTANCE = Mappers.getMapper(ActorResourceMapper.class);

    @Mappings({
        @Mapping(source = "firstName", target = "first"),
        @Mapping(source = "lastName", target = "last")
    })
    ActorResource map(Actor actor);

    @AfterMapping
    default void addLinks(@MappingTarget ActorResource resource, Actor entity) {
        Link selfLink = linkTo(methodOn(ActorController.class).retrieveActor(entity.getId())).withSelfRel();
        Link filmsLink = linkTo(methodOn(ActorController.class).retrieveActorFilms(entity.getId())).withRel("films");
        resource.add(selfLink, filmsLink);
    }
}
