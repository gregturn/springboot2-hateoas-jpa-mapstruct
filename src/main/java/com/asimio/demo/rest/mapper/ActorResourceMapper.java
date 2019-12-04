package com.asimio.demo.rest.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.asimio.demo.domain.Actor;
import com.asimio.demo.rest.ActorController;
import com.asimio.demo.rest.model.ActorResource;

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
        ControllerLinkBuilder linkBuilder = ControllerLinkBuilder.linkTo(ActorController.class).slash(entity);
        Link selfLink = linkBuilder.withSelfRel();
        Link filmsLink = linkBuilder.slash("films").withRel("films");
        resource.add(selfLink, filmsLink);
    }
}