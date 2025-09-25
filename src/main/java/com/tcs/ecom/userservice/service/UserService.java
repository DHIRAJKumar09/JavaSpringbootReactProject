package com.tcs.ecom.userservice.service;
import java.util.List;
import com.tcs.ecom.userservice.dto.UserDto;
import com.tcs.ecom.userservice.model.User;
import com.tcs.ecom.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
@Service
public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo){
        this.repo = repo;

    }
    public UserDto createUser(User user){
        User saved = repo.save(user);
        return mapToDto(saved);
    }
    public UserDto getUserById(Long id){
        return repo.findById(id).map(this::mapToDto).orElse(null);
    }
    public List<UserDto> getAllUsers(){
        return repo.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    public void deleteUser(Long id){
        repo.deleteById(id);
    }
    public UserDto updateUser(Long id, User updated) {
        return repo.findById(id).map(user -> {
            user.setName(updated.getName()); // consistent naming
            user.setEmail(updated.getEmail());
            user.setPassword(updated.getPassword());
            user.setRole(updated.getRole());
            return mapToDto(repo.save(user));
        }).orElse(null);
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }




}
