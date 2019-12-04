package com.asimio.demo.rest;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.asimio.demo.domain.Film;
import com.asimio.demo.rest.exception.ResourceNotFoundException;
import com.asimio.demo.rest.mapper.FilmResourceMapper;
import com.asimio.demo.rest.model.FilmResource;
import com.asimio.demo.service.DvdRentalService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/films", produces = { MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE })
public class FilmController {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 20;

    private final DvdRentalService dvdRentalService;
    private final ResourceAssembler<Film, FilmResource> filmResourceAssembler;

    @GetMapping(path = "")
    public ResponseEntity<PagedResources<FilmResource>> retrieveFilms(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE) Pageable pageRequest,
            PagedResourcesAssembler<Film> pagedResourcesAssembler) {

        Page<Film> films = this.dvdRentalService.retrieveFilms(pageRequest);
        Link selfLink = new Link(ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString());
        PagedResources<FilmResource> result = pagedResourcesAssembler.toResource(films, this.filmResourceAssembler, selfLink);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FilmResource> retrieveFilm(@PathVariable Integer id) {
        Optional<Film> optionalFilm = this.dvdRentalService.retrieveFilm(id);
        return optionalFilm.map(film -> {
            FilmResource resource = FilmResourceMapper.INSTANCE.map(film);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Film with id=%s not found", id)));
    }
}