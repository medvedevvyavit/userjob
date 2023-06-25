package ru.medvedev.userjob.resource.request;

import lombok.Getter;
import lombok.Setter;
import ru.medvedev.userjob.resource.dto.CompanyDto;
import ru.medvedev.userjob.resource.dto.UserDto;
import ru.medvedev.userjob.resource.dto.UserJobInfoDto;

@Getter
@Setter
public class UserJobRequest {

    private UserDto user;
    private CompanyDto company;
    private UserJobInfoDto userJobInfo;
}
