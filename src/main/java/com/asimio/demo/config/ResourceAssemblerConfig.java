package com.asimio.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.asimio.demo.domain.Actor;
import com.asimio.demo.domain.Film;
import com.asimio.demo.rest.ActorController;
import com.asimio.demo.rest.FilmController;
import com.asimio.demo.rest.mapper.ActorResourceMapper;
import com.asimio.demo.rest.mapper.FilmResourceMapper;
import com.asimio.demo.rest.mapper.ResourceAssembler;
import com.asimio.demo.rest.model.ActorResource;
import com.asimio.demo.rest.model.FilmResource;

@Configuration
public class ResourceAssemblerConfig {

    @Bean
    public ResourceAssembler<Actor, ActorResource, ActorController> actorResourceAssembler() {
        return new ResourceAssembler<Actor, ActorResource, ActorController>(ActorController.class, ActorResource.class,
                ActorResourceMapper.INSTANCE);
    }

    @Bean
    public ResourceAssembler<Film, FilmResource, FilmController> filmResourceAssembler() {
        return new ResourceAssembler<Film, FilmResource, FilmController>(FilmController.class, FilmResource.class,
                FilmResourceMapper.INSTANCE);
    }
}