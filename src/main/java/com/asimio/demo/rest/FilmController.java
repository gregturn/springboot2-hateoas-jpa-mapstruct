package com.asimio.demo.rest;

import java.util.Optional;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asimio.demo.domain.Film;
import com.asimio.demo.rest.exception.ResourceNotFoundException;
import com.asimio.demo.rest.mapper.FilmResourceMapper;
import com.asimio.demo.rest.model.FilmResource;
import com.asimio.demo.service.DvdRentalService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final DvdRentalService dvdRentalService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<FilmResource> retrieveFilm(@PathVariable Integer id) {
        Optional<Film> optionalFilm = this.dvdRentalService.retrieveFilm(id);
        return optionalFilm.map(film -> {
            FilmResource resource = FilmResourceMapper.INSTANCE.map(film);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Film with id=%s not found", id)));
    }
}
