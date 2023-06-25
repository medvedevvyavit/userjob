package ru.medvedev.userjob.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.medvedev.userjob.entity.User;
import ru.medvedev.userjob.entity.UserJobInfo;
import ru.medvedev.userjob.resource.dto.UserDto;
import ru.medvedev.userjob.resource.dto.UserJobInfoDto;

@Mapper
public interface UserMapper {

    User toEntity(UserDto dto);

    UserDto toDto(User entity);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "company.id", target = "companyId")
    UserJobInfoDto toDto(UserJobInfo entity);
}
