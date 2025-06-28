package banque.myapp.mobile.mapper;

import banque.myapp.data.models.entity.User;

import banque.myapp.mobile.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapperMobile {
    UserResponseDto toUserResponseDto(User user);
}
