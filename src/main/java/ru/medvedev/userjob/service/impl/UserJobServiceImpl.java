package ru.medvedev.userjob.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.QueryParameterException;
import org.springframework.stereotype.Service;
import ru.medvedev.userjob.entity.Company;
import ru.medvedev.userjob.entity.User;
import ru.medvedev.userjob.entity.UserJobInfo;
import ru.medvedev.userjob.mapper.CompanyMapper;
import ru.medvedev.userjob.mapper.UserJobInfoMapper;
import ru.medvedev.userjob.mapper.UserMapper;
import ru.medvedev.userjob.repository.CompanyRepository;
import ru.medvedev.userjob.repository.UserJobInfoRepository;
import ru.medvedev.userjob.repository.UserRepository;
import ru.medvedev.userjob.resource.dto.UserDto;
import ru.medvedev.userjob.resource.dto.UserJobInfoDto;
import ru.medvedev.userjob.resource.request.UserJobRequest;
import ru.medvedev.userjob.resource.response.UserJobResponse;
import ru.medvedev.userjob.service.UserJobService;

import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserJobServiceImpl implements UserJobService {

    public static final String RELATIONSHIP_ALREADY_EXISTS_MSG = "The user company relationship already exists in the database";
    public static final String ENTITY_NOT_FOUND_BY_ID_MSG = "Entity %s not found by id = %s";
    public static final String ENTITY_NOT_FOUND_OR_MISSING_ID_MSG = "Entity %s not found or missing parameter id";
    public static final String QUERY_PARAMETER_NOT_FOUND_MSG = "Query parameter %s is missing";
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserJobInfoRepository userJobInfoRepository;
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;
    private final UserJobInfoMapper userJobInfoMapper;

    @Override
    public void createUserJob(UserJobRequest request) {
        User user;
        Company company;
        Long userId = request.getUser().getId();
        Long companyId = request.getCompany().getId();

        if (ofNullable(userId).isPresent() && ofNullable(companyId).isPresent() && (
                userJobInfoRepository.getUserJobInfoByUserIdAndCompanyId(
                        request.getUser().getId(),
                        request.getCompany().getId()).isPresent() ||
                        userJobInfoRepository.findById(request.getUserJobInfo().getId()).isPresent())) {
            throw new EntityExistsException(RELATIONSHIP_ALREADY_EXISTS_MSG);
        }

        UserJobInfo userJobInfo = userJobInfoMapper.toEntity(request.getUserJobInfo());

        if (ofNullable(userId).isPresent()) {
            user = userRepository.findById(userId).orElse(userMapper.toEntity(request.getUser()));
        } else {
            user = userMapper.toEntity(request.getUser());
        }

        if (ofNullable(companyId).isPresent()) {
            company = companyRepository.findById(companyId).orElse(companyMapper.toEntity(request.getCompany()));
        } else {
            company = companyMapper.toEntity(request.getCompany());
        }

        userRepository.save(user);
        companyRepository.save(company);
        userJobInfo.setUser(user);
        userJobInfo.setCompany(company);
        userJobInfoRepository.save(userJobInfo);
        log.info("Data saved successfully");
    }

    @Override
    public void updateUserJob(UserJobRequest request) {
        User user = userMapper.toEntity(request.getUser());
        Company company = companyMapper.toEntity(request.getCompany());
        UserJobInfo userJobInfo = userJobInfoMapper.toEntity(request.getUserJobInfo());

        if (ofNullable(user.getId()).isPresent() && userRepository.existsById(user.getId())) {
            User originUser = userRepository.getReferenceById(user.getId());
            fillFieldsUser(user, originUser);
            userRepository.save(user);
        } else {
            throw new EntityNotFoundException(format(ENTITY_NOT_FOUND_OR_MISSING_ID_MSG, "user"));
        }

        if (ofNullable(company.getId()).isPresent() && companyRepository.existsById(company.getId())) {
            Company originCompany = companyRepository.getReferenceById(company.getId());
            fillFieldsCompany(company, originCompany);
            companyRepository.save(company);
        } else {
            throw new EntityNotFoundException(format(ENTITY_NOT_FOUND_OR_MISSING_ID_MSG, "company"));
        }

        if (ofNullable(userJobInfo.getId()).isPresent() && userJobInfoRepository.existsById(userJobInfo.getId())) {
            UserJobInfo originUserJobInfo = userJobInfoRepository.getReferenceById(userJobInfo.getId());
            fillFieldsUserJobInfo(userJobInfo, originUserJobInfo);
            userJobInfoRepository.save(userJobInfo);
        } else if (ofNullable(userJobInfo.getId()).isEmpty()) {
            UserJobInfo originUserJobInfo = userJobInfoRepository.getUserJobInfoByUserIdAndCompanyId(user.getId(), company.getId()).orElse(null);
            if (ofNullable(originUserJobInfo).isPresent()) {
                userJobInfo.setUser(user);
                userJobInfo.setCompany(company);
                fillFieldsUserJobInfo(userJobInfo, originUserJobInfo);
                userJobInfoRepository.save(userJobInfo);
            } else {
                throw new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID_MSG, "UserJobInfo", userJobInfo.getId()));
            }
        } else {
            throw new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID_MSG, "UserJobInfo", userJobInfo.getId()));
        }
        log.info("Data changed successfully");
    }

    @Override
    public UserJobResponse getUserJob(Long userId, Long companyId) {
        UserJobResponse userJobResponse = new UserJobResponse();

        if (ofNullable(userId).isPresent()) {
            UserDto userDto = userMapper.toDto(userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID_MSG, "user", userId))));
            userDto.setUserJobInfos(userDto.getUserJobInfos().stream().filter(UserJobInfoDto::getIsActivity).collect(Collectors.toList()));
            userJobResponse.setUser(userDto);
        } else if (ofNullable(companyId).isEmpty()) {
            throw new QueryParameterException(format(QUERY_PARAMETER_NOT_FOUND_MSG, "userId and companyId"));
        }

        if (ofNullable(companyId).isPresent()) {
            userJobResponse.setCompany(companyMapper.toDto(companyRepository.findById(companyId)
                    .orElseThrow(() -> new EntityNotFoundException(format(ENTITY_NOT_FOUND_BY_ID_MSG, "company", companyId)))));
        }

        return userJobResponse;
    }

    private void fillFieldsUser(User updateUser, User originUser) {
        if (updateUser.getFirstName() == null) updateUser.setFirstName(originUser.getFirstName());
        if (updateUser.getFamilyName() == null) updateUser.setFamilyName(originUser.getFamilyName());
        if (updateUser.getMiddleName() == null) updateUser.setMiddleName(originUser.getMiddleName());
        if (updateUser.getGender() == null) updateUser.setGender(originUser.getGender());
        if (updateUser.getAge() == null) updateUser.setAge(originUser.getAge());
        if (updateUser.getIsBlocked() == null) updateUser.setIsBlocked(originUser.getIsBlocked());
        if (updateUser.getDescription() == null) updateUser.setDescription(originUser.getDescription());
    }

    private void fillFieldsCompany(Company updateCompany, Company originCompany) {
        if (updateCompany.getCompanyName() == null) updateCompany.setCompanyName(originCompany.getCompanyName());
        if (updateCompany.getIsActivity() == null) updateCompany.setIsActivity(originCompany.getIsActivity());
        if (updateCompany.getDescription() == null) updateCompany.setDescription(originCompany.getDescription());
    }

    private void fillFieldsUserJobInfo(UserJobInfo updateUserJobInfo, UserJobInfo originUserJobInfo) {
        if (updateUserJobInfo.getId() == null) updateUserJobInfo.setId(originUserJobInfo.getId());
        if (updateUserJobInfo.getIsActivity() == null) updateUserJobInfo.setIsActivity(originUserJobInfo.getIsActivity());
        if (updateUserJobInfo.getDescription() == null) updateUserJobInfo.setDescription(originUserJobInfo.getDescription());
    }
}
