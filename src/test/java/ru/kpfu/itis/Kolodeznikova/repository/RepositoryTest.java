package ru.kpfu.itis.Kolodeznikova.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ru.kpfu.itis.Kolodeznikova.model.Note;
import ru.kpfu.itis.Kolodeznikova.model.Role;
import ru.kpfu.itis.Kolodeznikova.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class RepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    private Note createNote(String title, User author) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent("Content");
        note.setAuthor(author);
        note.setPublic(false);
        return note;
    }

    @Test
    void testNoteRepositoryFindByAuthor() {
        User author = createUser("testuser", "test@gmail.com", "testpassword");
        Note note1 = createNote("Note 1", author);
        Note note2 = createNote("Note 2", author);
        entityManager.persistAndFlush(author);
        entityManager.persistAndFlush(note1);
        entityManager.persistAndFlush(note2);

        List<Note> notes = noteRepository.findByAuthor(author);

        assertEquals(2, notes.size());
        assertEquals("Note 1", notes.get(0).getTitle());
    }

    @Test
    void testNoteRepositoryFindByIsPublicTrue() {
        User author = createUser("publicuser", "publicuser@gmail.com", "publicpassword");
        Note publicNote = createNote("Public Note", author);
        publicNote.setPublic(true);
        Note privateNote = createNote("Private Note", author);
        privateNote.setPublic(false);

        entityManager.persistAndFlush(author);
        entityManager.persistAndFlush(publicNote);
        entityManager.persistAndFlush(privateNote);

        List<Note> publicNotes = noteRepository.findByIsPublicTrue();

        assertEquals(1, publicNotes.size());
        assertTrue(publicNotes.get(0).isPublic());
    }

    @Test
    void testRoleRepositoryFindByName() {
        Role role = new Role();
        role.setName("ADMIN");
        entityManager.persistAndFlush(role);

        Optional<Role> foundRole = roleRepository.findByName("ADMIN");

        assertTrue(foundRole.isPresent());
        assertEquals("ADMIN", foundRole.get().getName());
    }

    @Test
    void testUserRepositoryFindByUsername() {
        User user = createUser("testuser", "test@gmail.com", "testpassword");
        entityManager.persistAndFlush(user);

        Optional<User> foundUser = userRepository.findByUsername("testuser");

        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void testUserRepositoryJPQLQuery() {
        User user = createUser("jpqluser", "jpq@gmail.com", "jpqlpassword");
        entityManager.persistAndFlush(user);

        Optional<User> foundUser = userRepository.getByUsername("jpqluser");

        assertTrue(foundUser.isPresent());
        assertEquals("jpqluser", foundUser.get().getUsername());
    }

    @Test
    void testUserRepositoryNativeQuery() {
        User user = createUser("nativeuser", "native@gmail.com", "nativepassword");
        entityManager.persistAndFlush(user);

        Optional<User> foundUser = userRepository.getByUsernameNative("nativeuser");

        assertTrue(foundUser.isPresent());
        assertEquals("nativeuser", foundUser.get().getUsername());
    }
}
