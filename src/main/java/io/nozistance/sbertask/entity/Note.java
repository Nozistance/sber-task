package io.nozistance.sbertask.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Entity class representing a Note.
 * This class is mapped to the "notes" table in the database.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notes")
public class Note {

    /**
     * Unique identifier for the note.
     * Generated automatically using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Title of the note.
     * Must be unique and not null.
     */
    @Column(nullable = false, unique = true)
    private String title;

    /**
     * Content of the note.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String content;

    /**
     * Timestamp of when the note was created.
     * Cannot be updated after creation.
     */
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}
