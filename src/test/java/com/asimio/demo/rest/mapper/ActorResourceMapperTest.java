package com.asimio.demo.rest.mapper;

import com.asimio.demo.domain.Actor;
import com.asimio.demo.fixtures.ActorFixtures;
import com.asimio.demo.rest.model.ActorResource;
import org.junit.Test;
import org.springframework.hateoas.IanaLinkRelations;

import static org.assertj.core.api.Assertions.*;

public class ActorResourceMapperTest {

    @Test
    public void shouldMapFromActorToActorResource() {
        // Given
        Actor actor = ActorFixtures.createActor();
        // When
        ActorResource actual = ActorResourceMapper.INSTANCE.map(actor);
        // Then
        assertActorResource(actual);
    }

    private void assertActorResource(ActorResource actual) {
        assertThat(actual.getActorId()).isEqualTo(1);
        assertThat(actual.getFirst()).isEqualTo("First");
        assertThat(actual.getLast()).isEqualTo("Last");
        assertThat(actual.getLinks()).hasSize(2);
        assertThat(actual.getRequiredLink(IanaLinkRelations.SELF).getHref()).isEqualTo("/api/actors/1");
        assertThat(actual.getRequiredLink("films").getHref()).isEqualTo("/api/actors/1/films");
    }
}
