package com.irusol.distributed_lovable.account_service.mapper;

import com.irusol.distributed_lovable.account_service.dto.auth.SignupRequest;
import com.irusol.distributed_lovable.account_service.dto.auth.UserProfileResponse;
import com.irusol.distributed_lovable.account_service.entity.User;
import com.irusol.distributed_lovable.common_lib.dto.UserDto;
import com.irusol.distributed_lovable.common_lib.security.JwtUserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(SignupRequest signupRequest);

    @Mapping(source = "userId", target = "id")
    UserProfileResponse toUserProfileResponse(JwtUserPrincipal user);

    UserDto toUserDto(User user);

}
