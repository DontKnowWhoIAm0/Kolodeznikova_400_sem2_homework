package ru.kpfu.itis.Kolodeznikova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.Kolodeznikova.model.Note;
import ru.kpfu.itis.Kolodeznikova.model.User;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByAuthor(User author);

    @Query("SELECT n FROM Note n WHERE n.isPublic = true")
    List<Note> findByIsPublicTrue();
}
