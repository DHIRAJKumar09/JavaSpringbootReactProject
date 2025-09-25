package com.tcs.ecom.userservice.controller;

import com.tcs.ecom.userservice.dto.UserDto;
import com.tcs.ecom.userservice.model.User;
import com.tcs.ecom.userservice.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController (UserService service){
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody User user){
        UserDto  dto  = service.createUser(user);
        return ResponseEntity.created(URI.create("/api/users/"+dto.getId())).body(dto);

    }
    @GetMapping
    public List<UserDto> getAll(){
        return service.getAllUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable  Long id){
        UserDto dto = service.getUserById(id);
        return dto == null ? ResponseEntity.notFound().build() :ResponseEntity.ok(dto);


    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto>  update(@PathVariable Long id, @RequestBody User user){
        UserDto  dto = service.updateUser(id,user);
        return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



}
