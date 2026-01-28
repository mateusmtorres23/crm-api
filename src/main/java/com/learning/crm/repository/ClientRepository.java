package com.learning.crm.repository;

import com.learning.crm.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {

    boolean existsByEmailOrPhone(String email, String phone);

}
