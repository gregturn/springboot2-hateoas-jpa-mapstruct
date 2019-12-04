package com.asimio.demo.rest.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import com.asimio.demo.domain.Film;
import com.asimio.demo.rest.ActorController;
import com.asimio.demo.rest.FilmController;
import com.asimio.demo.rest.model.FilmResource;

@Mapper
public interface FilmResourceMapper extends ResourceMapper<Film, FilmResource> {

    FilmResourceMapper INSTANCE = Mappers.getMapper(FilmResourceMapper.class);

    @Mappings({
        @Mapping(expression = "java(film.getLanguage().getName().trim())", target = "lang"),
    })
    FilmResource map(Film film);

    @AfterMapping
    default void addLinks(@MappingTarget FilmResource resource, Film entity) {
        // self link
        Link selfLink = ControllerLinkBuilder.linkTo(FilmController.class)
                .slash(entity)
                .withSelfRel();
        resource.add(selfLink);

        // actors links
        entity.getFilmActors().stream()
        .forEach(filmActor -> resource.add(
                ControllerLinkBuilder.linkTo(
                        ControllerLinkBuilder.methodOn(ActorController.class)
                        .retrieveActor(filmActor.getActor().getId()))
                        .withRel("actors")
                ));
    }
}