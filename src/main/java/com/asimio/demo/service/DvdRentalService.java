package com.asimio.demo.service;

import java.util.Optional;

import com.asimio.demo.domain.Actor;
import com.asimio.demo.domain.Film;

public interface DvdRentalService {

    Optional<Actor> retrieveActor(Integer id);

    Optional<Film> retrieveFilm(Integer id);
}