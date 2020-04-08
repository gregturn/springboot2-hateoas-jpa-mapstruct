package com.asimio.demo.rest.mapper;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public final class LinkUtil {

    private LinkUtil() {
        // NOOP
    }

    public static void assertLink(RepresentationModel actualResource, String rel, String... hrefs) {
        List<Link> actualLinks = actualResource.getLinks(rel);
        Assert.assertThat(actualLinks.size(), Matchers.equalTo(hrefs.length));
        for (Link actualLink : actualLinks) {
            Assert.assertThat(actualLink.getHref(), Matchers.isOneOf(hrefs));
        }
    }
}
