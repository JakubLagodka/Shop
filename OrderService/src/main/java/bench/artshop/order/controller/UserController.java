package bench.artshop.order.controller;

import bench.artshop.order.dto.UserDto;
import bench.artshop.order.mapper.UserMapper;
import bench.artshop.order.service.UserService;
import bench.artshop.order.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.ThrowableProblem;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody UserDto user) {
        try {
            return ResponseEntity.ok().body(userMapper.toDto(userService.create(userMapper.toDao(user))));
        } catch (ThrowableProblem throwableProblem) {
            return new ResponseEntity<>(throwableProblem.getMessage(), HttpStatusCode.valueOf(409));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    public ResponseEntity<Object> findUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(userMapper.toDto(userService.getById(id)));
        } catch (ThrowableProblem throwableProblem) {
            return new ResponseEntity<>(throwableProblem.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @GetMapping("/logged")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> findCurrentUser() {
        try {
            return ResponseEntity.ok().body(userMapper.toDto(userService.getCurrentUser()));
        } catch (ThrowableProblem throwableProblem) {
            return new ResponseEntity<>(throwableProblem.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @PutMapping("/logged")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> updateCurrentUser(@RequestBody UserDto user) {
        return ResponseEntity.ok().body(userMapper.toDto(userService.update(userMapper.toDao(user), userService.getByLogin(SecurityUtils.getCurrentUserLogin()).getId())));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    public ResponseEntity<Object> findUsers() {
        return ResponseEntity.ok().body(userService.getUsers().stream().map(userMapper::toDto).collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateUser(@RequestBody UserDto user, @PathVariable Long id) {
        return ResponseEntity.ok().body(userMapper.toDto(userService.update(userMapper.toDao(user), id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}