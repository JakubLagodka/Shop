package bench.artshop.order.controller;

import bench.artshop.order.dao.Role;
import bench.artshop.order.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.ThrowableProblem;

import static bench.artshop.order.util.SecurityUtils.getCurrentUserLogin;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService RoleService;

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    public ResponseEntity<Object> findRole(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(RoleService.getById(id));
        } catch (ThrowableProblem throwableProblem) {
            return new ResponseEntity<>(throwableProblem.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @GetMapping("/logged")
    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    public ResponseEntity<Object> findCurrentRoles() {
        try {
            return ResponseEntity.ok().body(RoleService.getCurrentRoles(getCurrentUserLogin()));
        } catch (ThrowableProblem throwableProblem) {
            return new ResponseEntity<>(throwableProblem.getMessage(), HttpStatusCode.valueOf(404));
        }
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    public ResponseEntity<Object> findRoles() {
        return ResponseEntity.ok().body(RoleService.getRoles());
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateRole(@RequestBody Role Role, @PathVariable Long id) {
        return ResponseEntity.ok().body(RoleService.update(Role, id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    public void deleteRole(@PathVariable Long id) {
        RoleService.delete(id);
    }
}