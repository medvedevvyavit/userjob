package ru.medvedev.userjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.medvedev.userjob.entity.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
