package com.asimio.demo.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asimio.demo.dao.ActorDao;
import com.asimio.demo.dao.FilmDao;
import com.asimio.demo.domain.Actor;
import com.asimio.demo.domain.Film;
import com.asimio.demo.service.DvdRentalService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DefaultDvdRentalService implements DvdRentalService {

    private final ActorDao actorDao;
    private final FilmDao filmDao;

    @Override
    public Page<Actor> retrieveActors(Pageable pageable) {
        return this.actorDao.findAll(pageable);
    }

    @Override
    public Optional<Actor> retrieveActor(Integer id) {
        return this.actorDao.findById(id);
    }

    @Override
    public Page<Film> retrieveFilms(Pageable pageable) {
        return this.filmDao.findAll(pageable);
    }

    @Override
    public Optional<Film> retrieveFilm(Integer id) {
        return this.filmDao.findById(id);
    }
}