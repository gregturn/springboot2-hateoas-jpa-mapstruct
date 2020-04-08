package com.asimio.demo.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@EqualsAndHashCode(callSuper = false)
@Relation(value = "actor", collectionRelation = "actors")
public class ActorResource extends RepresentationModel<ActorResource> {

    @JsonProperty("id")
    private int actorId;
    private String first;
    private String last;
}
