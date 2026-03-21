package ru.kpfu.itis.Kolodeznikova.controller;

import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.Kolodeznikova.dto.NoteDto;
import ru.kpfu.itis.Kolodeznikova.service.NoteService;

import java.util.List;

@RestController
@RequestMapping("/admin/notes")
public class AdminNoteController {

    private final NoteService noteService;

    public AdminNoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<NoteDto> getAllNotes() {
        return noteService.getAllNotes().stream()
                .map(NoteDto::new)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable("id") Long id) {
        noteService.deleteNoteById(id, null, true);
    }
}
