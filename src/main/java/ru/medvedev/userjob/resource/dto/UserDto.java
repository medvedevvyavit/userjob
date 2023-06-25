package ru.medvedev.userjob.resource.dto;

import lombok.Getter;
import lombok.Setter;
import ru.medvedev.userjob.enums.GenderEnum;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserDto extends BaseDto {

    private String familyName;
    private String middleName;
    private String firstName;
    private LocalDate birthday;
    private GenderEnum gender;
    private Integer age;
    private Boolean isBlocked;
    private List<UserJobInfoDto> userJobInfos;
}
