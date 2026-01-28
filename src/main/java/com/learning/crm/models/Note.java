package com.learning.crm.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Table(name = "notes")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String content;
    @CreationTimestamp
    private Instant createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @Setter(AccessLevel.PACKAGE)
    private Client client;

    public  Note(String id, String content) {
        this.id = id;
        this.content = content;
    }

}
