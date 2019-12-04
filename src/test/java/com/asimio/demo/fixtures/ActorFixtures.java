package com.asimio.demo.fixtures;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

import org.mockito.internal.util.collections.Sets;

import com.asimio.demo.domain.Actor;
import com.asimio.demo.domain.Film;
import com.asimio.demo.domain.FilmActor;

public class ActorFixtures {

    private static final Integer DEFAULT_ACTOR_ID = Integer.valueOf(1);
    private static final String DEFAULT_FIRST_NAME = "First";
    private static final String DEFAULT_LAST_NAME = "Last";
    private static final Date DEFAULT_LAST_UPDATED_TMSTP = Date.from(Instant.ofEpochMilli(1514768400));

    public static Actor createActor() {
        return createActor(DEFAULT_ACTOR_ID, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_LAST_UPDATED_TMSTP,
                Sets.newSet(FilmFixtures.createFilm()));
    }

    public static Actor createActor(Set<Film> films) {
        return createActor(DEFAULT_ACTOR_ID, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_LAST_UPDATED_TMSTP, films);
    }

    public static Actor createActor(int id, String firstName, String lastName, Date lastUpdated, Set<Film> films) {
        Actor result = new Actor();
        result.setActorId(id);
        result.setFirstName(firstName);
        result.setLastName(lastName);
        result.setLastUpdate(lastUpdated);
        Set<FilmActor> filmActors = Sets.newSet();
        for (Film film : films) {
            filmActors.add(FilmFixtures.createFilmActor(result, film));
        }
        result.setFilmActors(filmActors);
        return result;
    }
}