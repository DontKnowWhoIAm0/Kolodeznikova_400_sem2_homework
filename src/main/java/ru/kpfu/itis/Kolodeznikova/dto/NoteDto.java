package ru.kpfu.itis.Kolodeznikova.dto;

import ru.kpfu.itis.Kolodeznikova.model.Note;

public class NoteDto {

    private Long id;
    private String title;
    private String content;
    private boolean isPublic;
    private String authorName;
    private String authorSecondName;

    public NoteDto(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.isPublic = note.isPublic();
        this.authorName = note.getAuthor().getUsername();
        this.authorSecondName = note.getAuthor().getSecondName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSecondName() {
        return authorSecondName;
    }

    public void setAuthorSecondName(String authorSecondName) {
        this.authorSecondName = authorSecondName;
    }
}
