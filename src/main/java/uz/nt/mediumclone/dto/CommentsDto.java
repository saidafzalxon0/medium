package uz.nt.mediumclone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.nt.mediumclone.dto.UserDto;
import uz.nt.mediumclone.model.Article;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {

    private Integer id;
    private String body;
    private UserDto author;

}
