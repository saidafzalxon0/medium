package uz.nt.mediumclone.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.nt.mediumclone.dto.FollowDto;
import uz.nt.mediumclone.dto.UserDto;
import uz.nt.mediumclone.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/sign-up")
    private ResponseEntity<String> addUser(@Valid @RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PostMapping("/sign-in")
    private ResponseEntity<String> signIn(@RequestParam String username,@RequestParam String password){
        return userService.signIn(username,password);
    }

    @GetMapping({"/{id}"})
    ResponseEntity<UserDto> getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @PatchMapping
    ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }




    @PostMapping("follow/{following}")
    public ResponseEntity<FollowDto> followUser(@PathVariable Integer following) {
        return userService.followUser(following);
    }
    @DeleteMapping("follow/{following}")
    public ResponseEntity<String> unfollowUser(@PathVariable Integer following) {
        return userService.unfollowUser(following);
    }


}
