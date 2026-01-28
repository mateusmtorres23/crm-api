package com.learning.crm.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Table(name = "clients")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter(AccessLevel.PACKAGE)
    private User user;
    @Enumerated(EnumType.STRING)
    private ClientStatus status;
    private String name;
    private String email;
    private String phone;
    @CreationTimestamp
    Instant createdAt;
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();

    public Client(String id, ClientStatus status, String name, String email, String phone) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void addNote(Note note) {
        if (note == null) {
            throw new IllegalArgumentException("Note cannot be empty");
        }
        notes.add(note);
        note.setClient(this);
    }

    public void removeNote(Note note) {
        notes.remove(note);
        note.setClient(null);
    }

    public List<Note> getNotes() {
        return Collections.unmodifiableList(notes);
    }

}
