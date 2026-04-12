package ru.kpfu.itis.Kolodeznikova.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.kpfu.itis.Kolodeznikova.model.Note;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.repository.NoteRepository;
import ru.kpfu.itis.Kolodeznikova.repository.UserRepository;
import ru.kpfu.itis.Kolodeznikova.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
class NoteServiceImplTest {

    @Autowired
    private NoteServiceImpl noteService;

    @MockitoBean
    private NoteRepository noteRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private UserService userService;

    @Test
    void getNotesByUser_returnsNotes() {
        User user = new User();
        Note note = new Note();
        note.setAuthor(user);
        given(noteRepository.findByAuthor(user)).willReturn(List.of(note));

        List<Note> result = noteService.getNotesByUser(user);

        assertEquals(1, result.size());
        assertEquals(note, result.get(0));
    }

    @Test
    void getPublicNotes_returnsOnlyPublicNotes() {
        Note note = new Note();
        note.setPublic(true);
        given(noteRepository.findByIsPublicTrue()).willReturn(List.of(note));

        List<Note> result = noteService.getPublicNotes();

        assertEquals(1, result.size());
        assertTrue(result.get(0).isPublic());
    }

    @Test
    void getNoteById_existingId_returnsNote() {
        Note note = new Note();
        given(noteRepository.findById(1L)).willReturn(Optional.of(note));

        Note result = noteService.getNoteById(1L);

        assertEquals(note, result);
    }

    @Test
    void getNoteById_nonExistingId_throwsException() {
        given(noteRepository.findById(1L)).willReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> noteService.getNoteById(1L));
        assertEquals("Заметка не найдена", exception.getMessage());
    }

    @Test
    void createNote_setsAuthorAndSaves() {
        Note note = new Note();
        User author = new User();
        noteService.createNote(note, author);

        assertEquals(author, note.getAuthor());
        verify(noteRepository).save(note);
    }

    @Test
    void deleteNoteById_asOwner_deletesNote() {
        User user = new User();
        user.setId(1L);
        Note note = new Note();
        note.setAuthor(user);

        given(noteRepository.findById(1L)).willReturn(Optional.of(note));

        noteService.deleteNoteById(1L, user, false);
        verify(noteRepository).delete(note);
    }

    @Test
    void editNote_success_updatesNote() {
        User user = new User();
        user.setId(1L);
        Note note = new Note();
        note.setId(1L);
        note.setAuthor(user);
        note.setTitle("Old");
        note.setContent("Old content");
        note.setPublic(false);

        Note updated = new Note();
        updated.setTitle("New");
        updated.setContent("New content");
        updated.setPublic(true);

        given(noteRepository.findById(1L)).willReturn(Optional.of(note));

        noteService.editNote(1L, updated, user);

        assertEquals("New", note.getTitle());
        assertEquals("New content", note.getContent());
        assertTrue(note.isPublic());
        verify(noteRepository).save(note);
    }

    @Test
    void editNote_notAuthor_throwsException() {
        User author = new User();
        author.setId(1L);
        User other = new User();
        other.setId(2L);

        Note note = new Note();
        note.setId(1L);
        note.setAuthor(author);

        Note updated = new Note();
        given(noteRepository.findById(1L)).willReturn(Optional.of(note));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                noteService.editNote(1L, updated, other));
        assertEquals("Можно редактировать только свои заметки", ex.getMessage());
    }

    @Test
    void editNote_notFound_throwsException() {
        Note updated = new Note();
        given(noteRepository.findById(1L)).willReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                noteService.editNote(1L, updated, new User()));
        assertEquals("Заметка не найдена", ex.getMessage());
    }

    @Test
    void deleteNoteById_admin_canDeleteAny() {
        User author = new User();
        author.setId(1L);
        User admin = new User();
        admin.setId(2L);

        Note note = new Note();
        note.setId(1L);
        note.setAuthor(author);

        given(noteRepository.findById(1L)).willReturn(Optional.of(note));

        noteService.deleteNoteById(1L, admin, true);
        verify(noteRepository).delete(note);
    }

    @Test
    void deleteNoteById_notAuthor_notAdmin_throwsException() {
        User author = new User();
        author.setId(1L);
        User other = new User();
        other.setId(2L);

        Note note = new Note();
        note.setId(1L);
        note.setAuthor(author);

        given(noteRepository.findById(1L)).willReturn(Optional.of(note));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                noteService.deleteNoteById(1L, other, false));
        assertEquals("Можно удалить только свои заметки", ex.getMessage());
    }

    @Test
    void deleteNoteById_author_canDeleteOwn() {
        User author = new User();
        author.setId(1L);

        Note note = new Note();
        note.setId(1L);
        note.setAuthor(author);

        given(noteRepository.findById(1L)).willReturn(Optional.of(note));

        noteService.deleteNoteById(1L, author, false);
        verify(noteRepository).delete(note);
    }

    @Test
    void deleteNoteById_notFound_throwsException() {
        given(noteRepository.findById(1L)).willReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                noteService.deleteNoteById(1L, new User(), false));
        assertEquals("Заметка не найдена", ex.getMessage());
    }

    @Test
    void getAllNotes_returnsAllNotes() {
        Note note1 = new Note();
        Note note2 = new Note();
        given(noteRepository.findAll()).willReturn(List.of(note1, note2));

        List<Note> result = noteService.getAllNotes();
        assertEquals(2, result.size());
        assertTrue(result.contains(note1));
        assertTrue(result.contains(note2));
    }

    @Test
    void editNoteForm_noteNotFound_redirectsOrThrows() {
        Long noteId = 1L;
        User user = new User();
        given(noteRepository.findById(noteId)).willReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                noteService.editNote(noteId, new Note(), user));
        assertEquals("Заметка не найдена", ex.getMessage());
    }

    @Test
    void editNoteForm_notAuthor_throwsException() {
        Long noteId = 1L;
        User author = new User();
        author.setId(1L);

        User other = new User();
        other.setId(2L);

        Note note = new Note();
        note.setId(noteId);
        note.setAuthor(author);

        given(noteRepository.findById(noteId)).willReturn(Optional.of(note));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                noteService.editNote(noteId, new Note(), other));
        assertEquals("Можно редактировать только свои заметки", ex.getMessage());
    }
}
