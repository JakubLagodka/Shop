package bench.artshop.order.controller;

import bench.artshop.order.dto.UserDto;
import bench.artshop.order.mapper.UserMapper;
import bench.artshop.order.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    public  ResponseEntity<Object> saveUser(@RequestBody UserDto user) {
        return ResponseEntity.ok().body(userMapper.toDto(userService.create(userMapper.toDao(user))));
    }
    @GetMapping("/{id}")
//    @PreAuthorize("isAuthenticated() && (@securityService.hasAccessToUser(#id) || hasRole('ADMIN'))")
    public  ResponseEntity<Object> findUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(userMapper.toDto(userService.getById(id)));
    }
    @GetMapping("/")
    public ResponseEntity<Object> findUsers() {
        return ResponseEntity.ok().body(userService.getUsers().stream().map(userMapper::toDto).collect(Collectors.toList()));
    }
    @PutMapping("/{id}")
//    @PreAuthorize("isAuthenticated() && (@securityService.hasAccessToUser(#id) || hasRole('ADMIN'))")
    public  ResponseEntity<Object> updateUser(@RequestBody UserDto user, @PathVariable Long id){
        return ResponseEntity.ok().body(userMapper.toDto(userService.update(userMapper.toDao(user), id)));
    }
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id){
        userService.delete(id);
    }
}