package com.sazzad.blog.mappers;

import com.sazzad.blog.domain.dtos.RegistrationResponse;
import com.sazzad.blog.domain.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-16T09:45:33+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UserInfoMapperImpl implements UserInfoMapper {

    @Override
    public RegistrationResponse toDto(User user) {
        if ( user == null ) {
            return null;
        }

        RegistrationResponse.RegistrationResponseBuilder registrationResponse = RegistrationResponse.builder();

        if ( user.getId() != null ) {
            registrationResponse.id( user.getId().toString() );
        }
        registrationResponse.name( user.getName() );
        registrationResponse.email( user.getEmail() );

        return registrationResponse.build();
    }
}
