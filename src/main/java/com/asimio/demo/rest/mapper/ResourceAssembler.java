package com.asimio.demo.rest.mapper;

import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;

public class ResourceAssembler<E extends Identifiable<Integer>, R extends ResourceSupport, C>
        extends IdentifiableResourceAssemblerSupport<E, R> {

    private final ResourceMapper<E, R> resourceMapper;

    public ResourceAssembler(Class<C> controllerClass, Class<R> resourceClass, ResourceMapper<E, R> resourceMapper) {
        super(controllerClass, resourceClass);
        this.resourceMapper = resourceMapper;
    }

    @Override
    public R toResource(E entity) {
        return this.resourceMapper.map(entity);
    }
}