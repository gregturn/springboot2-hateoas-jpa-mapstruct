package com.asimio.demo.rest.model;

import java.math.BigDecimal;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Relation(value = "film", collectionRelation = "films")
public class FilmResource extends ResourceSupport {

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