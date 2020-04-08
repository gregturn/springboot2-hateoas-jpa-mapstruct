package com.asimio.demo.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Relation(value = "film", collectionRelation = "films")
public class FilmResource extends RepresentationModel<FilmResource> {

    @JsonProperty("id")
    private int filmId;
    private String lang;
    private String title;
    private String description;
    private BigDecimal rentalRate;
    private Short rentalDuration;
    private Short length;
    private String releaseYear;
}
