package com.learning.crm.service;

import com.learning.crm.dto.client.request.AddNoteRequest;
import com.learning.crm.dto.client.request.CreateClientRequest;
import com.learning.crm.dto.client.response.ClientDetailsResponse;
import com.learning.crm.dto.client.response.NoteInfoResponse;
import com.learning.crm.models.Client;
import com.learning.crm.models.ClientStatus;
import com.learning.crm.models.Note;
import com.learning.crm.models.User;
import com.learning.crm.repository.ClientRepository;
import com.learning.crm.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public ClientService(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    public Client createClient(CreateClientRequest request, String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Your account no longer exists"));

        if (clientRepository.existsByEmailOrPhone(request.email(), request.phone())) {
            throw new IllegalArgumentException("A client with this email or phone number already exists");
        }

        Client newClient = new Client(
                null,
                ClientStatus.LEAD,
                request.name(),
                request.email(),
                request.phone()
        );

        user.addClient(newClient);
        return clientRepository.save(newClient);
    }

    public ClientDetailsResponse getClientDetails(String clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client doesn't exist"));

        List<NoteInfoResponse> notes = client.getNotes().stream()
                .map(note -> new NoteInfoResponse(
                        note.getId(),
                        note.getContent(),
                        note.getCreatedAt()
                )).toList();

        return new ClientDetailsResponse(
                client.getId(),
                client.getName(),
                client.getPhone(),
                client.getEmail(),
                client.getStatus(),
                notes
        );
    }

    public void deleteClient(String clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client already deleted"));
        clientRepository.delete(client);
    }

    public void editClient(CreateClientRequest  request, String clientId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client doesn't exist"));

        String newEmail = (request.email().equals(client.getEmail())) ? null : request.email();
        String newPhone = (request.phone().equals(client.getPhone())) ? null : request.phone();

        if (clientRepository.existsByEmailOrPhone(newEmail, newPhone)) {
            throw new IllegalArgumentException("Client with this email or phone number already exists");
        }

        client.setName(request.name());
        client.setEmail(request.email());
        client.setPhone(request.phone());

        clientRepository.save(client);

    }

    public void addNoteToClient(String clientId, AddNoteRequest request) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client doesn't exist"));

        Note newNote = new Note(
                null,
                request.content()
        );

        client.addNote(newNote);
        clientRepository.save(client);

    }

    public void changeStatus(String clientId, ClientStatus newStatus) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client doesn't exist"));

        client.setStatus(newStatus);
        clientRepository.save(client);

    }
}
