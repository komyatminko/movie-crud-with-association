package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.entity.Actor;

import jakarta.transaction.Transactional;

public interface ActorDao extends JpaRepository<Actor, Long>{

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM actor WHERE id = ?1", nativeQuery = true)
	public int deleteActorById(Long id);
	
}
