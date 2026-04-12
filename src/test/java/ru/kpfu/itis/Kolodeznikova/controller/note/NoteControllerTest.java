package ru.kpfu.itis.Kolodeznikova.controller.note;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.Kolodeznikova.config.SecurityConfig;
import ru.kpfu.itis.Kolodeznikova.model.Note;
import ru.kpfu.itis.Kolodeznikova.model.Role;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.service.NoteService;
import ru.kpfu.itis.Kolodeznikova.service.security.CustomUserDetails;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
@Import(SecurityConfig.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    private final User testUser = createTestUser(1L, "testuser");

    private User createTestUser(Long id, String username) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        user.setRoles(List.of(role));

        return user;
    }

    private Note createTestNote(Long id, String title, User author) {
        Note note = new Note();
        note.setId(id);
        note.setTitle(title);
        note.setAuthor(author);
        note.setContent("Content");
        return note;
    }

    @Test
    void testGetMyNotes() throws Exception {
        Note note = createTestNote(1L, "Test note", testUser);
        given(noteService.getNotesByUser(testUser)).willReturn(List.of(note));

        mockMvc.perform(get("/notes").with(user("testuser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("notes"))
                .andExpect(model().attributeExists("notes"));
    }

    @Test
    void testGetPublicNotes() throws Exception {
        Note publicNote = createTestNote(1L, "Public note", testUser);
        publicNote.setContent("Public content");
        given(noteService.getPublicNotes()).willReturn(List.of(publicNote));

        mockMvc.perform(get("/notes/public").with(user("testuser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("public_notes"))
                .andExpect(model().attributeExists("notes"));
    }

    @Test
    void testCreateNoteForm() throws Exception {
        mockMvc.perform(get("/notes/create").with(user("testuser").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("note_form"))
                .andExpect(model().attributeExists("note"));
    }

    @Test
    void testCreateNote() throws Exception {
        Note newNote = createTestNote(1L, "New note", testUser);
        doNothing().when(noteService).createNote(eq(newNote), eq(testUser));

        mockMvc.perform(post("/notes/create")
                        .param("title", "New note")
                        .param("content", "Content")
                        .with(csrf())
                        .with(user("testuser").roles("USER")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes"));
    }

    @Test
    void testEditNoteForm_OwnNote() throws Exception {
        Note note = createTestNote(1L, "Edit note", testUser);
        given(noteService.getNoteById(1L)).willReturn(note);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new CustomUserDetails(testUser),
                        null,
                        new CustomUserDetails(testUser).getAuthorities()
                )
        );

        mockMvc.perform(get("/notes/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("note_form"))
                .andExpect(model().attributeExists("note"));
    }

    @Test
    void testEditNoteForm_NotOwnNote() throws Exception {
        User otherUser = createTestUser(2L, "otheruser");
        Note note = createTestNote(1L, "Not own note", testUser);
        given(noteService.getNoteById(1L)).willReturn(note);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new CustomUserDetails(otherUser),
                        null,
                        new CustomUserDetails(otherUser).getAuthorities()
                )
        );

        mockMvc.perform(get("/notes/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes"));
    }

    @Test
    void testEditNote() throws Exception {
        Note updatedNote = createTestNote(1L, "Updated note", testUser);
        doNothing().when(noteService).editNote(eq(1L), eq(updatedNote), eq(testUser));

        mockMvc.perform(post("/notes/1/edit")
                        .param("title", "Updated note")
                        .param("content", "Updated content")
                        .with(csrf())
                        .with(user("testuser").roles("USER")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes"));
    }

    @Test
    void testDeleteNote() throws Exception {
        doNothing().when(noteService).deleteNoteById(eq(1L), eq(testUser), eq(false));

        mockMvc.perform(post("/notes/1/delete")
                        .with(csrf())
                        .with(user("testuser").roles("USER")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes"));
    }

    @Test
    void testEditNoteForm_Unauthenticated() throws Exception {
        Note note = createTestNote(1L, "Note", testUser);
        given(noteService.getNoteById(1L)).willReturn(note);

        SecurityContextHolder.getContext().setAuthentication(null);

        mockMvc.perform(get("/notes/1/edit"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testEditNoteForm_NoteWithoutAuthor() throws Exception {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("No author");
        given(noteService.getNoteById(1L)).willReturn(note);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new CustomUserDetails(testUser),
                        null,
                        new CustomUserDetails(testUser).getAuthorities()
                )
        );

        mockMvc.perform(get("/notes/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/notes"));
    }
}
