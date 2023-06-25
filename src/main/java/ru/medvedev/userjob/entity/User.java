package ru.medvedev.userjob.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.medvedev.userjob.enums.GenderEnum;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {

    private String familyName;

    private String middleName;

    private String firstName;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private Integer age;

    private Boolean isBlocked;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<UserJobInfo> userJobInfos;
}
