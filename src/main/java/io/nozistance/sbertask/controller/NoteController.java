package io.nozistance.sbertask.controller;

import io.nozistance.sbertask.entity.Note;
import io.nozistance.sbertask.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public String listNotes(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Note> notePage = noteService.findPaginatedAndSorted(page, 5);
        model.addAttribute("notes", notePage.getContent());
        model.addAttribute("page", page);
        model.addAttribute("totalPages", notePage.getTotalPages());
        return "notes/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("note", new Note());
        return "notes/create";
    }

    @PostMapping
    public String createNote(@ModelAttribute Note note) {
        noteService.save(note);
        return "redirect:/notes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        model.addAttribute("note", noteService.findById(id));
        return "notes/edit";
    }

    @PostMapping("/update/{id}")
    public String updateNote(@PathVariable UUID id, @ModelAttribute Note note) {
        noteService.update(id, note);
        return "redirect:/notes";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable UUID id) {
        noteService.delete(id);
        return "redirect:/notes";
    }
}
