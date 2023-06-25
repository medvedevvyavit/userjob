package ru.medvedev.userjob.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.medvedev.userjob.entity.UserJobInfo;
import ru.medvedev.userjob.resource.dto.UserJobInfoDto;

@Mapper
public interface UserJobInfoMapper {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "companyId", target = "company.id")
    UserJobInfo toEntity(UserJobInfoDto dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "company.id", target = "companyId")
    UserJobInfoDto toDto(UserJobInfo entity);
}
