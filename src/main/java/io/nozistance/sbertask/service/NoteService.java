package io.nozistance.sbertask.service;

import io.nozistance.sbertask.entity.Note;
import io.nozistance.sbertask.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Note findById(UUID id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
    }

    @Transactional
    public Note save(Note note) {
        note.setCreatedAt(OffsetDateTime.now());
        return noteRepository.save(note);
    }

    @Transactional
    public Note update(UUID id, Note noteDetails) {
        Note note = findById(id);
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
        return noteRepository.save(note);
    }

    @Transactional
    public void delete(UUID id) {
        Note note = findById(id);
        noteRepository.delete(note);
    }
}
