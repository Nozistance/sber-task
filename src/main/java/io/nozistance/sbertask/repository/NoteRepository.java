package io.nozistance.sbertask.repository;

import io.nozistance.sbertask.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {

}
