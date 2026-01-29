package com.learning.crm.repository;

import com.learning.crm.models.Client;
import com.learning.crm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, String> {

    boolean existsByEmailOrPhone(String email, String phone);

    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.notes WHERE c.user = :user")
    List<Client> findAllByUserWithNotes(User user);

}
