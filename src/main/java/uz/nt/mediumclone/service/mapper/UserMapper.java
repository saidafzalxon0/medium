package uz.nt.mediumclone.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.nt.mediumclone.dto.UserDto;
import uz.nt.mediumclone.model.User;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements CommonMapper<UserDto, User> {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    public abstract User toEntity(UserDto dto);
}
