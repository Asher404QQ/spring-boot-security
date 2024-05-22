package ru.kors.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kors.models.MyUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByUsername(String username);
}
