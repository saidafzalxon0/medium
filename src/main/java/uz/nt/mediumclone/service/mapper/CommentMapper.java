package uz.nt.mediumclone.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.nt.mediumclone.dto.CommentsDto;
import uz.nt.mediumclone.model.Comments;

@Mapper(componentModel = "spring")
public abstract class CommentMapper implements CommonMapper<CommentsDto, Comments> {

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected ArticleMapper articleMapper;

    @Mapping(target = "author", expression = "java(userMapper.toEntity(commentsDto.getAuthor()))")
//    @Mapping(target = "article", expression = "java(articleMapper.toEntity(commentsDto.getArticle()))")
    public abstract Comments toEntity(CommentsDto commentsDto);
}
