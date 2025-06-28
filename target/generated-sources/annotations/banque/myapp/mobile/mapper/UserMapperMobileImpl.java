package banque.myapp.mobile.mapper;

import banque.myapp.data.models.entity.User;
import banque.myapp.mobile.dto.response.UserResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-24T21:39:31+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class UserMapperMobileImpl implements UserMapperMobile {

    @Override
    public UserResponseDto toUserResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId( user.getId() );
        userResponseDto.setLogin( user.getLogin() );
        userResponseDto.setNom( user.getNom() );
        userResponseDto.setPrenom( user.getPrenom() );
        userResponseDto.setTelephone( user.getTelephone() );
        userResponseDto.setAdresse( user.getAdresse() );
        userResponseDto.setRole( user.getRole() );
        userResponseDto.setSolde( user.getSolde() );

        return userResponseDto;
    }
}
