package com.learning.crm.repository;

import com.learning.crm.models.Client;
import com.learning.crm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserByEmail(String email);

}
