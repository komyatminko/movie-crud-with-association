package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.model.entity.Actor;

public interface ActorDao extends JpaRepository<Actor, Long>{

}
