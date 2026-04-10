package ru.kpfu.itis.Kolodeznikova.controller.note;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kpfu.itis.Kolodeznikova.model.Note;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.service.security.CustomUserDetails;
import ru.kpfu.itis.Kolodeznikova.service.NoteService;

@Controller
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/notes")
    public String getMyNotes(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("notes", noteService.getNotesByUser(getUser()));
        return "notes";
    }

    @GetMapping("/notes/public")
    public String getPublicNotes(Model model) {
        model.addAttribute("notes", noteService.getPublicNotes());
        return "public_notes";
    }

    @GetMapping("/notes/create")
    public String createNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "note_form";
    }

    @PostMapping("/notes/create")
    public String createNote(@ModelAttribute Note note, @AuthenticationPrincipal User user) {
        noteService.createNote(note, getUser());
        return "redirect:/notes";
    }

    @GetMapping("/notes/{id}/edit")
    public String editNoteForm(@PathVariable("id") Long id, @AuthenticationPrincipal User user, Model model) {
        Note note = noteService.getNoteById(id);
        User currentUser = getUser();
        if (!note.getAuthor().getId().equals(currentUser.getId())) {
            return "redirect:/notes";
        }
        model.addAttribute("note", note);
        return "note_form";
    }

    @PostMapping("/notes/{id}/edit")
    public String editNote(@PathVariable("id") Long id, @ModelAttribute Note note, @AuthenticationPrincipal User user) {
        noteService.editNote(id, note, getUser());
        return "redirect:/notes";
    }

    @PostMapping("/notes/{id}/delete")
    public String deleteNote(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        noteService.deleteNoteById(id, getUser(), false);
        return "redirect:/notes";
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        Object principal = auth.getPrincipal();
        if (principal instanceof User) {
            currentUser = (User) principal;
        } else if (principal instanceof CustomUserDetails) {
            currentUser = ((CustomUserDetails) principal).getUser();
        }

        return currentUser;
    }

}
