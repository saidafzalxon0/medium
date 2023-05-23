package uz.nt.mediumclone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.nt.mediumclone.security.JwtService;
import uz.nt.mediumclone.dto.FollowDto;
import uz.nt.mediumclone.dto.UserDto;
import uz.nt.mediumclone.exeption.UserNotFoundException;
import uz.nt.mediumclone.exeption.UserNotSavedException;
import uz.nt.mediumclone.model.Follows;
import uz.nt.mediumclone.model.User;
import uz.nt.mediumclone.repository.FollowsRepository;
import uz.nt.mediumclone.repository.UserRepository;
import uz.nt.mediumclone.security.SecurityServices;
import uz.nt.mediumclone.service.UserService;
import uz.nt.mediumclone.service.mapper.FollowMapper;
import uz.nt.mediumclone.service.mapper.UserMapper;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FollowMapper followMapper;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private FollowsRepository followsRepository;

    @Autowired
    private SecurityServices securityServices;

    @Autowired
    private AuthenticationManager authenticationManager;


    public ResponseEntity<String> addUser(UserDto userDto) {
        try {
            var jwt = jwtService.generateToken(userRepository.save(userMapper.toEntity(userDto)));
            return new ResponseEntity<>(jwt, HttpStatus.CREATED);
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new UserNotSavedException("database connection failed. user is not saved");
        } catch (DataIntegrityViolationException e) {
            throw new UserNotFoundException("username or email already exists");
        }
    }


    public ResponseEntity<String> signIn(String username,String password){

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            var user = userRepository.findFirstByUsername(username).orElseThrow();
            var jwt = jwtService.generateToken(user);
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new UserNotSavedException("database connection failed. user is not saved");
        } catch (DataIntegrityViolationException e) {
            throw new UserNotFoundException("username or email already exists");
        }
    }

    public ResponseEntity<UserDto> getUser(Integer id) {
        try {
            return new ResponseEntity<>(userRepository.findFirstById(id).map((m) -> userMapper.toDto(m)).orElseThrow(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("user is not found");
        }
    }

    public ResponseEntity<UserDto> updateUser(UserDto userDto) {
        if (userDto.getId() == null) {
            throw new UserNotFoundException("user id not found");
        } else {
            try {
                if (userRepository.existsById(userDto.getId())) {
                    User userEntity = new User();
                    userEntity.setId(userDto.getId());
                    if (userDto.getUsername() != null) userEntity.setUsername(userDto.getUsername());
                    if (userDto.getPassword() != null) userEntity.setPassword(userDto.getPassword());
                    if (userDto.getEmail() != null) userEntity.setEmail(userDto.getEmail());
                    if (userDto.getBio() != null) userEntity.setBio(userDto.getBio());
                    try {
                        return new ResponseEntity<>(userMapper.toDto(userRepository.save(userEntity)), HttpStatus.OK);
                    } catch (NoSuchElementException e) {
                        throw new UserNotFoundException("user is not found");

                    } catch (InvalidDataAccessResourceUsageException e) {
                        throw new UserNotSavedException("database connection failed. User is not updated");
                    }
                } else {
                    throw new UserNotFoundException("User is not found");
                }
            } catch (InvalidDataAccessResourceUsageException e) {
                throw new UserNotSavedException("User is not updated");
            }


        }

    }


    @Override
    public ResponseEntity<FollowDto> followUser(Integer following) {

        User user = securityServices.getLoggedUser();
        User followingEntity = User.builder().id(following).build();

        try {
            return new ResponseEntity<>(followMapper.toDto(followsRepository.save(Follows.builder()
                    .follower(user)
                    .following(followingEntity)
                    .build())), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("user is not found");
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new UserNotSavedException("user is not updated");
        }


    }

    @Override
    public ResponseEntity<String> unfollowUser(Integer following) {
        User user = securityServices.getLoggedUser();
        User followingEntity = User.builder().id(following).build();

        try {
            followsRepository.delete(Follows.builder()
                    .follower(user)
                    .following(followingEntity)
                    .build());
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("user is not found");
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new UserNotSavedException("user is not unfollowed");
        }
    }


}
