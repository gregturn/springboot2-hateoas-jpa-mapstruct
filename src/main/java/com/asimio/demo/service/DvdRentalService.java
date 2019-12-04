package com.asimio.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asimio.demo.domain.Actor;
import com.asimio.demo.domain.Film;

public interface DvdRentalService {

    Page<Actor> retrieveActors(Pageable pageable);

    Optional<Actor> retrieveActor(Integer id);

    Page<Film> retrieveFilms(Pageable pageable);

    Optional<Film> retrieveFilm(Integer id);
}