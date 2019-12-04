package com.asimio.demo.rest.model;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Relation(value = "actor", collectionRelation = "actors")
public class ActorResource extends ResourceSupport {

    @JsonProperty("id")
    private int actorId;
    private String first;
    private String last;
}