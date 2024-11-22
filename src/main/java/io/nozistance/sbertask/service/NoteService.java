package io.nozistance.sbertask.service;

import io.nozistance.sbertask.entity.Note;
import io.nozistance.sbertask.repository.NoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.by;

/**
 * Service class for managing notes.
 * Provides methods for CRUD operations on notes.
 */
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    /**
     * Retrieves all notes from the repository.
     *
     * @return a list of all notes
     */
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    /**
     * Finds a note by its unique identifier.
     *
     * @param id the UUID of the note
     * @return the note with the specified id
     * @throws RuntimeException if the note is not found
     */
    public Note findById(UUID id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
    }

    /**
     * Retrieves a paginated and sorted list of notes.
     *
     * @param page the page number to retrieve
     * @param size the number of notes per page
     * @return a page of notes sorted by creation date
     */
    public Page<Note> findPaginatedAndSorted(int page, int size) {
        Pageable pageable = of(page, size).withSort(by("createdAt"));
        return noteRepository.findAll(pageable);
    }

    /**
     * Saves a new note to the repository.
     *
     * @param note the note to save
     * @return the saved note
     */
    @Transactional
    public Note save(Note note) {
        note.setCreatedAt(OffsetDateTime.now());
        return noteRepository.save(note);
    }

    /**
     * Updates an existing note with new details.
     *
     * @param id the UUID of the note to update
     * @param noteDetails the new details for the note
     * @return the updated note
     */
    @Transactional
    public Note update(UUID id, Note noteDetails) {
        Note note = findById(id);
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        return noteRepository.save(note);
    }

    /**
     * Deletes a note by its unique identifier.
     *
     * @param id the UUID of the note to delete
     */
    @Transactional
    public void delete(UUID id) {
        Note note = findById(id);
        noteRepository.delete(note);
    }
}
