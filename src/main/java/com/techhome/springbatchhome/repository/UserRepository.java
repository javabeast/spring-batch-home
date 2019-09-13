package com.techhome.springbatchhome.repository;

import com.techhome.springbatchhome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}