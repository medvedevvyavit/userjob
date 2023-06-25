package ru.medvedev.userjob.resource.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto extends BaseDto {

    private String companyName;
    private Boolean isActivity;
}
