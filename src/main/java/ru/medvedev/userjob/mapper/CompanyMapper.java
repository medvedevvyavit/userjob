package ru.medvedev.userjob.mapper;

import org.mapstruct.Mapper;
import ru.medvedev.userjob.entity.Company;
import ru.medvedev.userjob.resource.dto.CompanyDto;

@Mapper
public interface CompanyMapper {

    Company toEntity(CompanyDto dto);
    CompanyDto toDto(Company entity);
}
