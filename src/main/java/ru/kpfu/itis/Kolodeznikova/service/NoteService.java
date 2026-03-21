package ru.kpfu.itis.Kolodeznikova.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.Kolodeznikova.model.Note;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.repository.NoteRepository;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Transactional(readOnly = true)
    public List<Note> getNotesByUser(User user) {
        return noteRepository.findByAuthor(user);
    }

    @Transactional(readOnly = true)
    public List<Note> getPublicNotes() {
        return noteRepository.findByIsPublicTrue();
    }

    @Transactional(readOnly = true)
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Заметка не найдена"));
    }

    @Transactional
    public void createNote(Note note, User author) {
        System.out.println(note);
        System.out.println(author);
        note.setAuthor(author);
        System.out.println(note);
        noteRepository.save(note);
    }

    @Transactional
    public void editNote(Long id, Note updatedNote, User user) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Заметка не найдена"));
        if (!note.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("Можно редактировать только свои заметки");
        }
        note.setTitle(updatedNote.getTitle());
        note.setContent(updatedNote.getContent());
        note.setPublic(updatedNote.isPublic());
        noteRepository.save(note);
    }

    @Transactional
    public void deleteNoteById(Long id, User user, boolean isAdmin) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Заметка не найдена"));

        if (!isAdmin && !note.getAuthor().getId().equals(user.getId())) {
            throw new RuntimeException("Можно удалить только свои заметки");
        }

        noteRepository.delete(note);
    }
}
