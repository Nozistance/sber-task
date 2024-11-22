package io.nozistance.sbertask.controller;

import io.nozistance.sbertask.entity.Note;
import io.nozistance.sbertask.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @Test
    void testListNotes() throws Exception {
        Page<Note> page = new PageImpl<>(Collections.singletonList(new Note()));
        when(noteService.findPaginatedAndSorted(0, 5)).thenReturn(page);

        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes/list"))
                .andExpect(model().attributeExists("notes"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("totalPages"));
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/notes/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("notes/create"))
                .andExpect(model().attributeExists("note"));
    }

    @Test
    void testCreateNote() throws Exception {
        mockMvc.perform(post("/notes")
                .param("title", "Test Title")
                .param("content", "Test Content"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes"));
    }

    @Test
    void testShowEditForm() throws Exception {
        UUID id = UUID.randomUUID();
        when(noteService.findById(id)).thenReturn(new Note());

        mockMvc.perform(get("/notes/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("notes/edit"))
                .andExpect(model().attributeExists("note"));
    }

    @Test
    void testUpdateNote() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(post("/notes/update/{id}", id)
                .param("title", "Updated Title")
                .param("content", "Updated Content"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes"));
    }

    @Test
    void testDeleteNote() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(get("/notes/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes"));
    }
}