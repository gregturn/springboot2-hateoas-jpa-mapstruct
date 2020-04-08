package com.asimio.demo.rest.mapper;

import com.asimio.demo.domain.FilmActor;
import com.asimio.demo.rest.FilmController;
import com.asimio.demo.rest.model.FilmResource;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Mapper
public interface FilmActorResourceMapper extends ResourceMapper<FilmActor, FilmResource> {

    FilmActorResourceMapper INSTANCE = Mappers.getMapper(FilmActorResourceMapper.class);

    @Mappings({
        @Mapping(source = "film.filmId", target = "filmId"),
        @Mapping(expression = "java(filmActor.getFilm().getLanguage().getName().trim())", target = "lang"),
        @Mapping(source = "film.title", target = "title"),
        @Mapping(source = "film.description", target = "description"),
        @Mapping(source = "film.rentalRate", target = "rentalRate"),
        @Mapping(source = "film.rentalDuration", target = "rentalDuration"),
        @Mapping(source = "film.length", target = "length"),
        @Mapping(source = "film.releaseYear", target = "releaseYear"),
    })
    FilmResource map(FilmActor filmActor);

    @AfterMapping
    default void addLinks(@MappingTarget FilmResource resource, FilmActor entity) {
        Link selfLink = linkTo(methodOn(FilmController.class).retrieveFilm(entity.getFilm().getId())).withSelfRel();
        resource.add(selfLink);
    }
}
