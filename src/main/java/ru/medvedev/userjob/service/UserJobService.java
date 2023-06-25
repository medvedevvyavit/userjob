package ru.medvedev.userjob.service;

import ru.medvedev.userjob.resource.request.UserJobRequest;
import ru.medvedev.userjob.resource.response.UserJobResponse;

public interface UserJobService {

    void createUserJob(UserJobRequest request);
    void updateUserJob(UserJobRequest request);
    UserJobResponse getUserJob(Long userId, Long companyId);
}
