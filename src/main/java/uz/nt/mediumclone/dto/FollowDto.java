package uz.nt.mediumclone.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowDto {
    private Integer id;
    private UserDto follower;
    private UserDto following;
}
