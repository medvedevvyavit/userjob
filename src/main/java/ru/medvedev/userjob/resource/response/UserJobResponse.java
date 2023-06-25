package ru.medvedev.userjob.resource.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import ru.medvedev.userjob.resource.dto.CompanyDto;
import ru.medvedev.userjob.resource.dto.UserDto;
import ru.medvedev.userjob.resource.dto.UserJobInfoDto;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserJobResponse {

    private UserDto user;
    private CompanyDto company;
    private UserJobInfoDto userJobInfo;
}
