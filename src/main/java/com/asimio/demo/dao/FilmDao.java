package com.asimio.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asimio.demo.domain.Film;

@Repository
public interface FilmDao extends JpaRepository<Film, Integer> {

    // FIXME: Uncomment to fix N+1 Select problem
//    @EntityGraph(
//            type = EntityGraphType.FETCH,
//            attributePaths = { "language", "filmActors", "filmActors.actor" }
//    )
//    Page<Film> findAll(Pageable pageable);
//
//    @EntityGraph(
//            type = EntityGraphType.FETCH,
//            attributePaths = { "language", "filmActors", "filmActors.actor" }
//    )
//    Optional<Film> findById(Integer id);
}