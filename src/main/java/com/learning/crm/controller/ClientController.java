package com.learning.crm.controller;

import com.learning.crm.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/clients")
public class ClientController {

    @GetMapping
    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();

        return clients;
    }

}
