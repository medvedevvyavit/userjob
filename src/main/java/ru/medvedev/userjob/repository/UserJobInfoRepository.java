package ru.medvedev.userjob.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.medvedev.userjob.entity.UserJobInfo;

import java.util.Optional;

public interface UserJobInfoRepository extends JpaRepository<UserJobInfo, Long> {
    Optional<UserJobInfo> getUserJobInfoByUserIdAndCompanyId(Long userId, Long companyId);
    Optional<Long> findIdByUserIdAndCompanyId(Long userId, Long companyId);
}
