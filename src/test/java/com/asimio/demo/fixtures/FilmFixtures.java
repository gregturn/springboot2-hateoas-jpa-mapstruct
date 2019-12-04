package com.asimio.demo.fixtures;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

import org.mockito.internal.util.collections.Sets;

import com.asimio.demo.domain.Actor;
import com.asimio.demo.domain.Film;
import com.asimio.demo.domain.FilmActor;
import com.asimio.demo.domain.FilmActorId;

public class FilmFixtures {

    private static final Integer DEFAULT_FILM_ID = Integer.valueOf(10);
    private static final String DEFAULT_TITLE = "Title";
    private static final String DEFAULT_DESCRIPTION = "Description";
    private static final String DEFAULT_REL_YEAR = "1990";
    private static final Short DEFAULT_LENGHT = 90; // minutes
    private static final BigDecimal DEFAULT_RENTAL_RATE = BigDecimal.valueOf(4.50); // daily
    private static final Short DEFAULT_RENTAL_DURATION = 48; // hours
    private static final Date DEFAULT_LAST_UPDATED_TMSTP = Date.from(Instant.ofEpochMilli(1514768400));

    public static Film createFilm() {
        return createFilm(DEFAULT_FILM_ID, DEFAULT_TITLE, DEFAULT_DESCRIPTION, DEFAULT_REL_YEAR, DEFAULT_LENGHT,
                DEFAULT_RENTAL_RATE, DEFAULT_RENTAL_DURATION, DEFAULT_LAST_UPDATED_TMSTP);
    }

    public static Film createFilm(int id, String title, String description, String relYear, Short lenght,
            BigDecimal rentalRate, Short rentalDuration, Date lastUpdated) {

        Film result = new Film();
        result.setFilmId(id);
        result.setTitle(title);
        result.setDescription(description);
        result.setReleaseYear(relYear);
        result.setLength(lenght);
        result.setRentalRate(rentalRate);
        result.setRentalDuration(rentalDuration);
        Set<Film> films = Sets.newSet(result);
        result.setLanguage(LanguageFixtures.createLanguage(films));
        result.setLastUpdate(lastUpdated);
        // Film actors
        Actor actor1 = ActorFixtures.createActor(2, "First2", "Last2", DEFAULT_LAST_UPDATED_TMSTP, films);
        FilmActor filmActor1 = createFilmActor(actor1, result);
        Actor actor2 = ActorFixtures.createActor(3, "First3", "Last3", DEFAULT_LAST_UPDATED_TMSTP, films);
        FilmActor filmActor2 = createFilmActor(actor2, result);
        result.setFilmActors(Sets.newSet(filmActor1, filmActor2));
        // FIXME
//        result.setFulltext(fullText);
//        result.setRating(rating);
//        result.setReplacementCost(replacementCost);
//        result.setSpecialFeatures(specialFeatures);
//        result.setFilmCategories(filmCategories);
//        result.setInventories(inventories);
        return result;
    }

    public static FilmActor createFilmActor(Actor actor, Film film) {
        FilmActor result = new FilmActor();
        result.setActor(actor);
        result.setFilm(film);
        result.setLastUpdate(DEFAULT_LAST_UPDATED_TMSTP);
        result.setId(createFilmActorId(actor, film));
        return result;
    }

    private static FilmActorId createFilmActorId(Actor actor, Film film) {
        FilmActorId result = new FilmActorId();
        result.setActorId(Short.valueOf(actor.getId().shortValue()));
        result.setFilmId(film.getId().shortValue());
        return result;
    }
}