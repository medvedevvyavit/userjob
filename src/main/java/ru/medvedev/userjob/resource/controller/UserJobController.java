package ru.medvedev.userjob.resource.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.medvedev.userjob.resource.request.UserJobRequest;
import ru.medvedev.userjob.resource.response.UserJobResponse;
import ru.medvedev.userjob.service.UserJobService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(UserJobController.BASE_PATH)
@RequiredArgsConstructor
public class UserJobController {

    public static final String BASE_PATH = "api/v1/userjob";

    private final UserJobService service;

    @PostMapping("/create-userjob")
    public ResponseEntity<?> createUserJob(@RequestBody UserJobRequest request) {
        service.createUserJob(request);
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("update-userjob")
    public ResponseEntity<?> updateUserJob(@RequestBody UserJobRequest request) {
        service.updateUserJob(request);
        return ResponseEntity.status(OK).build();
    }

    @GetMapping("get-userjob")
    public ResponseEntity<UserJobResponse> getUserJob(@Param("user") Long userId,
                                                      @Param("company") Long companyId) {
        return new ResponseEntity<>(service.getUserJob(userId, companyId), OK);
    }
}
