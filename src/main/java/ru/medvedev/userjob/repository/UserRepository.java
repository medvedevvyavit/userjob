package ru.medvedev.userjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.medvedev.userjob.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
}
