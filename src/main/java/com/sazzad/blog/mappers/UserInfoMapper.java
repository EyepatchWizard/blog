package com.sazzad.blog.mappers;

import com.sazzad.blog.domain.dtos.RegistrationResponse;
import com.sazzad.blog.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserInfoMapper {

    RegistrationResponse toDto(User user);
}
