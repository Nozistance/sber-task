package io.nozistance.sbertask.service;

import io.nozistance.sbertask.entity.Note;
import io.nozistance.sbertask.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private Note note;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        note = new Note(UUID.randomUUID(), "Test Title", "Test Content", OffsetDateTime.now());
    }

    @Test
    void testFindAll() {
        when(noteRepository.findAll()).thenReturn(Collections.singletonList(note));
        assertEquals(1, noteService.findAll().size());
    }

    @Test
    void testFindById() {
        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));
        assertEquals(note, noteService.findById(note.getId()));
    }

    @Test
    void testFindPaginatedAndSorted() {
        Page<Note> page = new PageImpl<>(Collections.singletonList(note));
        when(noteRepository.findAll(any(PageRequest.class))).thenReturn(page);
        assertEquals(1, noteService.findPaginatedAndSorted(0, 5).getTotalElements());
    }

    @Test
    void testSave() {
        when(noteRepository.save(any(Note.class))).thenReturn(note);
        assertEquals(note, noteService.save(note));
    }

    @Test
    void testUpdate() {
        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));
        when(noteRepository.save(any(Note.class))).thenReturn(note);
        assertEquals(note, noteService.update(note.getId(), note));
    }

    @Test
    void testDelete() {
        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));
        doNothing().when(noteRepository).delete(note);
        noteService.delete(note.getId());
        verify(noteRepository, times(1)).delete(note);
    }
}
