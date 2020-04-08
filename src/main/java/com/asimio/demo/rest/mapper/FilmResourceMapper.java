package com.asimio.demo.rest.mapper;

import com.asimio.demo.domain.Film;
import com.asimio.demo.rest.ActorController;
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
public interface FilmResourceMapper extends ResourceMapper<Film, FilmResource> {

    FilmResourceMapper INSTANCE = Mappers.getMapper(FilmResourceMapper.class);

    @Mappings({
        @Mapping(expression = "java(film.getLanguage().getName().trim())", target = "lang"),
    })
    FilmResource map(Film film);

    @AfterMapping
    default void addLinks(@MappingTarget FilmResource resource, Film entity) {
        // self link
        resource.add(linkTo(methodOn(FilmController.class).retrieveFilm(entity.getId())).withSelfRel());

        // actors links
        entity.getFilmActors().forEach(filmActor -> {
            resource.add(
                    linkTo(
                        methodOn(ActorController.class)
                            .retrieveActor(filmActor.getActor().getActorId()))
                        .withRel("actors")
            );
        });
    }
}
