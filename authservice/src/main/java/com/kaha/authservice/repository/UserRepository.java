package com.kaha.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kaha.authservice.model.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {

	Optional<AppUser> findByUsername(String username);

}
