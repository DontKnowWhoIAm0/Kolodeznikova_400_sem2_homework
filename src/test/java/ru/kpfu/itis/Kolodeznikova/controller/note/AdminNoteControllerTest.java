package ru.kpfu.itis.Kolodeznikova.controller.note;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.Kolodeznikova.model.Note;
import ru.kpfu.itis.Kolodeznikova.model.User;
import ru.kpfu.itis.Kolodeznikova.service.NoteService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminNoteController.class)
class AdminNoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllNotes() throws Exception {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Test note");

        User author = new User();
        author.setUsername("testuser");
        note.setAuthor(author);

        given(noteService.getAllNotes()).willReturn(List.of(note));

        mockMvc.perform(get("/admin/notes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test note"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteNote() throws Exception {
        mockMvc.perform(delete("/admin/notes/1").with(csrf()))
                .andExpect(status().isOk());

        verify(noteService).deleteNoteById(1L, null, true);
    }
}
