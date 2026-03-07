package ru.kpfu.itis.Kolodeznikova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.Kolodeznikova.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);
    User saveAndFlush(User user);
    Optional<User> findById(Long id);
    Optional<User> findByFirstName(String firstName);
    void deleteById(Long id);
    List<User> findAll();
}