package com.spring.boot.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.restaurant.model.Chef;

@Repository
public interface ChefRepo extends JpaRepository<Chef, Long> {

}
