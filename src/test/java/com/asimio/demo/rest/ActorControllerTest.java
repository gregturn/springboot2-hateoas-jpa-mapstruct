package com.asimio.demo.rest;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.asimio.demo.domain.Actor;
import com.asimio.demo.rest.mapper.ActorResourceMapper;
import com.asimio.demo.rest.model.ActorResource;
import com.asimio.demo.service.DvdRentalService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        ActorResourceMapper.class,
        ServletUriComponentsBuilder.class,
        UriComponentsBuilder.class,
        UriComponents.class,
        Link.class
})
public class ActorControllerTest {

    @Mock
    private DvdRentalService mockDvdRentalService;

    @Mock
    private ResourceAssembler<Actor, ActorResource> mockActorResourceAssembler;

    private ActorController actorController;

    @Before
    public void setup() {
        this.actorController = new ActorController(this.mockDvdRentalService, this.mockActorResourceAssembler);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldRetrieveActors() {
        // Given
        PowerMockito.mockStatic(ServletUriComponentsBuilder.class);
        PowerMockito.mockStatic(UriComponentsBuilder.class);
        PowerMockito.mockStatic(UriComponents.class);
        PowerMockito.mockStatic(Link.class);

        int pageNo = 3;
        int pageSize = 5;
        Pageable pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Actor> actorsPage = Mockito.mock(Page.class);
        ServletUriComponentsBuilder mockServletUriComponentsBuilder = Mockito.mock(ServletUriComponentsBuilder.class);
        UriComponentsBuilder mockUriComponentBuilder = Mockito.mock(UriComponentsBuilder.class); 
        UriComponents mockUriComponents = Mockito.mock(UriComponents.class);
        PagedResources<ActorResource> mockPagedResources = Mockito.mock(PagedResources.class);
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        PagedResourcesAssembler<Actor> mockPagedResourcesAssembler = Mockito.mock(PagedResourcesAssembler.class);

        Mockito.when(ServletUriComponentsBuilder.fromCurrentRequest())
            .thenReturn(mockServletUriComponentsBuilder);
        Mockito.when(mockServletUriComponentsBuilder.build())
            .thenReturn(mockUriComponents);
        Mockito.when(mockUriComponents.toUriString())
            .thenReturn("/api/actors");
        Mockito.when(UriComponentsBuilder.fromUriString("/api/actors"))
            .thenReturn(mockUriComponentBuilder);
        Mockito.when(mockUriComponentBuilder.build())
            .thenReturn(mockUriComponents);
        Mockito.when(mockUriComponents.getQueryParams())
            .thenReturn(multiValueMap);
        Mockito.when(this.mockDvdRentalService.retrieveActors(Mockito.eq(pageRequest)))
            .thenReturn(actorsPage);
        Mockito.when(mockPagedResourcesAssembler.toResource(Mockito.eq(actorsPage), Mockito.eq(this.mockActorResourceAssembler), Mockito.any(Link.class)))
            .thenReturn(mockPagedResources);

        // When
        ResponseEntity<PagedResources<ActorResource>> actualResponse = this.actorController.retrieveActors(pageRequest, mockPagedResourcesAssembler);

        // Then
        Assert.assertThat(actualResponse.getStatusCode(), Matchers.equalTo(HttpStatus.OK));
        Assert.assertThat(actualResponse.getBody(), Matchers.is(mockPagedResources));
    }
}