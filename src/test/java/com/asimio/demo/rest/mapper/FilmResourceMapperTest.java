package com.asimio.demo.rest.mapper;

import com.asimio.demo.domain.Film;
import com.asimio.demo.fixtures.FilmFixtures;
import com.asimio.demo.rest.model.FilmResource;
import org.junit.Test;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class FilmResourceMapperTest {

    @Test
    public void shouldMapFromFilmToFilmResource() {
        // Given
        Film film = FilmFixtures.createFilm();
        // When
        FilmResource actual = FilmResourceMapper.INSTANCE.map(film);
        // Then
        assertFilmResource(actual);
    }

    private void assertFilmResource(FilmResource actual) {
        assertThat(actual.getFilmId()).isEqualTo(10);
        assertThat(actual.getTitle()).isEqualTo("Title");
        assertThat(actual.getDescription()).isEqualTo("Description");
        assertThat(actual.getReleaseYear()).isEqualTo("1990");
        assertThat(actual.getLang()).isEqualTo("English");
        assertThat(actual.getLength()).isEqualTo((short) 90);
        assertThat(actual.getRentalRate()).isEqualTo(new BigDecimal(4.50));
        assertThat(actual.getRentalDuration()).isEqualTo((short) 48);
        assertThat(actual.getLinks()).hasSize(3);
        assertThat(actual.getRequiredLink(IanaLinkRelations.SELF).getHref()).isEqualTo("/api/films/10");
        assertThat(actual.getLinks("actors")).extracting(Link::getHref).containsExactlyInAnyOrder("/api/actors/2", "/api/actors/3");
    }
}
