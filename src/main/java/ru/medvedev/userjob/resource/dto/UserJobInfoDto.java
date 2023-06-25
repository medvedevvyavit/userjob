package ru.medvedev.userjob.resource.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJobInfoDto extends BaseDto {

    private Boolean isActivity;
    private Long userId;
    private Long companyId;
}
