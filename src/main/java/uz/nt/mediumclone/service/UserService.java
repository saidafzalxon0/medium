package uz.nt.mediumclone.service;
import org.springframework.http.ResponseEntity;
import uz.nt.mediumclone.dto.FollowDto;
import uz.nt.mediumclone.dto.UserDto;

public interface UserService {

    ResponseEntity<String> addUser(UserDto userDto);

    ResponseEntity<String> signIn(String username, String password);

    ResponseEntity<UserDto> getUser(Integer id);

    ResponseEntity<UserDto> updateUser(UserDto userDto);

    ResponseEntity<FollowDto> followUser(Integer following);

    ResponseEntity<String> unfollowUser(Integer following);

}

