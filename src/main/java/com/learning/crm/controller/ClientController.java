package com.learning.crm.controller;

import com.learning.crm.dto.client.request.AddNoteRequest;
import com.learning.crm.dto.client.request.CreateClientRequest;
import com.learning.crm.dto.client.response.ClientDetailsResponse;
import com.learning.crm.dto.client.response.ClientInfoResponse;
import com.learning.crm.dto.client.response.CreateClientResponse;
import com.learning.crm.models.Client;
import com.learning.crm.models.User;
import com.learning.crm.repository.ClientRepository;
import com.learning.crm.repository.UserRepository;
import com.learning.crm.security.JWTUserData;
import com.learning.crm.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.learning.crm.models.ClientStatus.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    public ClientController(ClientService clientService, UserRepository userRepository, ClientRepository clientRepository) {
        this.clientService = clientService;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public List<ClientInfoResponse> getClients(@AuthenticationPrincipal JWTUserData userData) {

        User user = userRepository.findById(userData.userId())
                .orElseThrow(() -> new IllegalArgumentException("Your account no longer exists"));

        List<Client> clients = clientRepository.findAllByUserWithNotes(user);

        return clients.stream()
                .map(client -> new ClientInfoResponse(
                        client.getId(),
                        client.getName(),
                        client.getPhone(),
                        client.getEmail(),
                        client.getStatus(),
                        client.getNotes().size()
                ))
                .toList();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDetailsResponse> getClientById(@PathVariable String clientId) {

        return ResponseEntity.ok().body(clientService.getClientDetails(clientId));

    }

    @PostMapping
    public ResponseEntity<CreateClientResponse> postClient(@RequestBody CreateClientRequest request,
                                        @AuthenticationPrincipal JWTUserData userData) {

            String userId = userData.userId();
            Client client = clientService.createClient(request, userId);

            return ResponseEntity.status(HttpStatus.CREATED).body(new CreateClientResponse(
                    client.getName(),
                    client.getEmail(),
                    client.getPhone()
            ));
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<String> deleteClient(@PathVariable String clientId) {
        clientService.deleteClient(clientId);

        return  ResponseEntity.ok().body("Client deleted successfully");
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<CreateClientResponse> editClient(@RequestBody CreateClientRequest request,
                           @PathVariable String clientId) {
        clientService.editClient(request, clientId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CreateClientResponse(
                        request.name(),
                        request.email(),
                        request.phone()
                )
        );
    }

    @PostMapping("/{clientId}/notes")
    public ResponseEntity<String> addNoteToClient(@PathVariable String clientId,
                                                  @RequestBody AddNoteRequest request) {
        clientService.addNoteToClient(clientId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Note added successfully");
    }

    @PatchMapping("/{clientId}/status")
    public ResponseEntity<String> changeStatus(@PathVariable String clientId,
                                               @RequestParam String status) {
        clientService.changeStatus(clientId, switch (status.toUpperCase()) {
            case "LEAD" -> LEAD;
            case "PROSPECT" -> PROSPECT;
            case "CUSTOMER" -> CUSTOMER;
            case "ARCHIVED" -> ARCHIVED;
            default -> throw new IllegalArgumentException("Invalid status value");
        });

        return ResponseEntity.ok().body("Client status updated successfully");
    }

}
