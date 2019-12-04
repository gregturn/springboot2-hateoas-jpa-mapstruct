package com.asimio.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asimio.demo.domain.Film;

@Repository
public interface FilmDao extends JpaRepository<Film, Integer> {
}