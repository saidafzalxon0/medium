package uz.nt.mediumclone.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.mediumclone.dto.CommentsDto;
import uz.nt.mediumclone.dto.FollowDto;
import uz.nt.mediumclone.model.Comments;
import uz.nt.mediumclone.model.Follows;

@Mapper(componentModel = "spring")
public abstract class FollowMapper implements CommonMapper<FollowDto,Follows>{
    @Autowired
    protected UserMapper userMapper;


    @Mapping(target = "follower", expression = "java(userMapper.toEntity(followDto.getFollower()))")
    @Mapping(target = "following", expression = "java(userMapper.toEntity(followDto.getFollowing()))")
    public abstract Follows toEntity(FollowDto followDto);

    @Mapping(target = "follower", expression = "java(userMapper.toDto(follows.getFollower()))")
    @Mapping(target = "following", expression = "java(userMapper.toDto(follows.getFollowing()))")
    public abstract FollowDto toDto(Follows follows);
}
