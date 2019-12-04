package com.asimio.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asimio.demo.domain.Actor;

@Repository
public interface ActorDao extends JpaRepository<Actor, Integer> {
}