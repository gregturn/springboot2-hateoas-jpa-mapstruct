package com.asimio.demo.rest.mapper;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.asimio.demo.domain.Actor;
import com.asimio.demo.fixtures.ActorFixtures;
import com.asimio.demo.rest.model.ActorResource;

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
        Assert.assertThat(actual.getActorId(), Matchers.equalTo(1));
        Assert.assertThat(actual.getFirst(), Matchers.equalTo("First"));
        Assert.assertThat(actual.getLast(), Matchers.equalTo("Last"));
        Assert.assertThat(actual.getLinks().size(), Matchers.equalTo(2));
        LinkUtil.assertLink(actual, "self", "/api/actors/1");
        LinkUtil.assertLink(actual, "films", "/api/actors/1/films");
    }
}