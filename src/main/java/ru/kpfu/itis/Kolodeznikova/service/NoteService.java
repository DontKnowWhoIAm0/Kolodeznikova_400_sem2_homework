package ru.kpfu.itis.Kolodeznikova.service;

import ru.kpfu.itis.Kolodeznikova.model.Note;
import ru.kpfu.itis.Kolodeznikova.model.User;

import java.util.List;

public interface NoteService {

    List<Note> getNotesByUser(User user);
    List<Note> getPublicNotes();
    List<Note> getAllNotes();
    Note getNoteById(Long id);
    void createNote(Note note, User author);
    void editNote(Long id, Note updatedNote, User user);
    void deleteNoteById(Long id, User user, boolean isAdmin);

}
