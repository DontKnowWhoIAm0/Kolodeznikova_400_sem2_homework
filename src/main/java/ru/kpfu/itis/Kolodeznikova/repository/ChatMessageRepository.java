package ru.kpfu.itis.Kolodeznikova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.Kolodeznikova.model.ChatMessage;
import ru.kpfu.itis.Kolodeznikova.model.User;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findTop50ByOrderBySentAtDesc();
    List<ChatMessage> findByAuthor(User author);

    @Query("SELECT m FROM ChatMessage m WHERE LOWER(m.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ChatMessage> searchByContent(@Param("keyword") String keyword);
}
