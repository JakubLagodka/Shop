package bench.artshop.order.controller;

import bench.artshop.order.mapper.UserMapper;
import bench.artshop.order.service.UserService;
import bench.artshop.order.util.UserUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static bench.artshop.order.util.UserUtils.userDtos;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Transactional
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

//    @Test
//    void shouldSaveUser() throws Exception {
//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(UserDto.builder()
//                                .firstName("Jan")
//                                .lastName("Kowalski")
//                                .login("jan")
//                                .mail("jan@gmail.com")
//                                .password("password")
//                                .confirmPassword("password")
//                                .build())))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.password").doesNotExist())
//                .andExpect(jsonPath("$.confirmPassword").doesNotExist())
//                .andExpect(jsonPath("$.login").value("jan"))
//                .andExpect(jsonPath("$.mail").value("jan@gmail.com"))
//                .andExpect(jsonPath("$.firstName").value("Jan"))
//                .andExpect(jsonPath("$.lastName").value("Kowalski"))
//                .andExpect(jsonPath("$.createdDate").exists())
//                .andExpect(jsonPath("$.createdBy").value("anonymousUser"))
//                .andExpect(jsonPath("$.lastModifiedDate").exists())
//                .andExpect(jsonPath("$.lastModifiedBy").value("anonymousUser"))
//                .andExpect(jsonPath("$.revisionType").doesNotExist())
//                .andExpect(jsonPath("$.revisionNumber").doesNotExist());
//    }
//
//    @Test
//    void shouldNotSaveUserWhenAlreadyExists() throws Exception {
//        userRepository.save(User.builder()
//                .firstName("Jan")
//                .lastName("Kowalski")
//                .login("jan")
//                .mail("jan@gmail.com")
//                .password("password")
//                .createdBy("user")
//                .createdDate(LocalDateTime.of(2022, 1, 5, 12, 40, 50))
//                .lastModifiedBy("user")
//                .lastModifiedDate(LocalDateTime.of(2022, 1, 15, 12, 40, 50))
//                .build());
//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(UserDto.builder()
//                                .firstName("Jan")
//                                .lastName("Kowalski")
//                                .login("jan")
//                                .mail("jan@gmail.com")
//                                .password("password")
//                                .confirmPassword("password")
//                                .build())))
//                .andExpect(status().isConflict())
//                .andExpect(jsonPath("$").doesNotExist());
//    }
//
//    @Test
//    void shouldNotSaveUserWhenUncorrectedData() throws Exception {
//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(UserDto.builder()
//                                .password("password")
//                                .confirmPassword("password")
//                                .mail("")
//                                .firstName("")
//                                .lastName("")
//                                .login("")
//                                .build())))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$[*].fieldName", containsInAnyOrder("login", "lastName", "mail", "firstName")))
//                .andExpect(jsonPath("$[*].message", containsInAnyOrder("must not be blank", "must not be blank", "must not be blank", "must not be blank")));
//
//    }
//
//    @Test
//    void shouldNotSaveUserWhenNotValidPassword() throws Exception {
//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(UserDto.builder()
//                                .password("password")
//                                .confirmPassword("pass")
//                                .mail("jakub.lagodka.pl@gmail.com")
//                                .firstName("Jan")
//                                .lastName("Nowak")
//                                .login("jak")
//                                .build())))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$").doesNotExist());
//
//    }
//
//    @Test
//    void shouldNotGetUserWhenUserIsUnauthorized() throws Exception {
//        mockMvc.perform(get("/api/users/1"))
//                .andExpect(status().isForbidden())
//                .andExpect(jsonPath("$").doesNotExist());
//    }
//
//    @Test
//    void shouldNotGetUserWhenUserHasNotAccess() throws Exception {
//        mockMvc.perform(get("/api/users/1"))
//                .andExpect(status().isForbidden())
//                .andExpect(jsonPath("$").doesNotExist());
//    }
//
//
//    @Test
//    @WithMockUser(username = "john")
//    void shouldGetUserWhenUserHasAccess() throws Exception {
//        User save = userRepository.save(User.builder()
//                .firstName("John")
//                .lastName("John")
//                .login("john")
//                .mail("john@gmail.com")
//                .password("pass")
//                .build());
//        mockMvc.perform(get("/api/users/" + save.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.password").doesNotExist())
//                .andExpect(jsonPath("$.confirmPassword").doesNotExist())
//                .andExpect(jsonPath("$.login").value("john"))
//                .andExpect(jsonPath("$.mail").value("john@gmail.com"))
//                .andExpect(jsonPath("$.firstName").value("John"))
//                .andExpect(jsonPath("$.lastName").value("John"))
//                .andExpect(jsonPath("$.createdDate").exists())
//                .andExpect(jsonPath("$.createdBy").value("john"))
//                .andExpect(jsonPath("$.lastModifiedDate").exists())
//                .andExpect(jsonPath("$.lastModifiedBy").value("john"))
//                .andExpect(jsonPath("$.revisionType").doesNotExist())
//                .andExpect(jsonPath("$.revisionNumber").doesNotExist());
//    }
//
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void shouldGetUserWhenUserIsAdmin() throws Exception {
//        User save = userRepository.save(User.builder()
//                .firstName("John")
//                .lastName("John")
//                .login("john")
//                .mail("jakub.lagodka.pl@gmail.com")
//                .password("pass")
//                .build());
//        mockMvc.perform(get("/api/users/" + save.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.password").doesNotExist())
//                .andExpect(jsonPath("$.confirmPassword").doesNotExist())
//                .andExpect(jsonPath("$.login").value("john"))
//                .andExpect(jsonPath("$.mail").value("jakub.lagodka.pl@gmail.com"))
//                .andExpect(jsonPath("$.firstName").value("John"))
//                .andExpect(jsonPath("$.lastName").value("John"))
//                .andExpect(jsonPath("$.createdDate").exists())
//                .andExpect(jsonPath("$.createdBy").value("user"))
//                .andExpect(jsonPath("$.lastModifiedDate").exists())
//                .andExpect(jsonPath("$.lastModifiedBy").value("user"))
//                .andExpect(jsonPath("$.revisionType").doesNotExist())
//                .andExpect(jsonPath("$.revisionNumber").doesNotExist());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void shouldNotGetUserWhenUserDoesNotExist() throws Exception {
//        mockMvc.perform(get("/api/users/1"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$").doesNotExist());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void shouldGetUserPage() throws Exception {
//        userRepository.save(User.builder()
//                .firstName("John")
//                .lastName("John")
//                .login("john")
//                .mail("jakub.lagodka.pl@gmail.com")
//                .password("pass")
//                .build());
//        mockMvc.perform(get("/api/users/")
//                        .queryParam("page", "0")
//                        .queryParam("size", "10"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content.length()").value(1))
//                .andExpect(jsonPath("$.content[*].id").exists())
//                .andExpect(jsonPath("$.content[*].password").doesNotExist())
//                .andExpect(jsonPath("$.content[*].confirmPassword").doesNotExist())
//                .andExpect(jsonPath("$.content[*].login", containsInAnyOrder("john")))
//                .andExpect(jsonPath("$.content[*].mail", containsInAnyOrder("jakub.lagodka.pl@gmail.com")))
//                .andExpect(jsonPath("$.content[*].firstName", containsInAnyOrder("John")))
//                .andExpect(jsonPath("$.content[*].lastName", containsInAnyOrder("John")))
//                .andExpect(jsonPath("$.content[*].createdDate").exists())
//                .andExpect(jsonPath("$.content[*].createdBy", containsInAnyOrder("user")))
//                .andExpect(jsonPath("$.content[*].lastModifiedDate").exists())
//                .andExpect(jsonPath("$.content[*].lastModifiedBy", containsInAnyOrder("user")))
//                .andExpect(jsonPath("$.content[*].revisionType").doesNotExist())
//                .andExpect(jsonPath("$.content[*].revisionNumber").doesNotExist());
//
//    }
//
//    @Test
//    @WithMockUser
//    void shouldNotGetUserPageWhenUserIsNotAdmin() throws Exception {
//        userRepository.save(User.builder()
//                .firstName("John")
//                .lastName("John")
//                .login("john")
//                .mail("john@gmail.com")
//                .password("pass")
//                .build());
//        mockMvc.perform(get("/api/users/")
//                        .queryParam("page", "0")
//                        .queryParam("size", "10"))
//                .andExpect(status().isForbidden())
//                .andExpect(jsonPath("$").doesNotExist());
//    }
//
//    @Test
//    @WithMockUser(username = "john")
//    void shouldUpdateUserWhenUserHasAccess() throws Exception {
//        User save = userRepository.save(User.builder()
//                .firstName("John")
//                .lastName("John")
//                .login("john")
//                .mail("john@gmail.com")
//                .password("pass")
//                .build());
//        mockMvc.perform(put("/api/users/" + save.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(UserDto.builder()
//                                .firstName("John")
//                                .lastName("Kowalski")
//                                .login("john")
//                                .mail("john@gmail.com")
//                                .password("password")
//                                .confirmPassword("password")
//                                .build())))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.password").doesNotExist())
//                .andExpect(jsonPath("$.confirmPassword").doesNotExist())
//                .andExpect(jsonPath("$.login").value("john"))
//                .andExpect(jsonPath("$.mail").value("john@gmail.com"))
//                .andExpect(jsonPath("$.firstName").value("John"))
//                .andExpect(jsonPath("$.lastName").value("Kowalski"))
//                .andExpect(jsonPath("$.createdDate").exists())
//                .andExpect(jsonPath("$.createdBy").value("john"))
//                .andExpect(jsonPath("$.lastModifiedDate").exists())
//                .andExpect(jsonPath("$.lastModifiedBy").value("john"))
//                .andExpect(jsonPath("$.revisionType").doesNotExist())
//                .andExpect(jsonPath("$.revisionNumber").doesNotExist());
//    }
//
//    @Test
//    @WithMockUser(username = "john")
//    void shouldNotUpdateUserWhenUserHasNotAccess() throws Exception {
//        User save = userRepository.save(User.builder()
//                .firstName("John")
//                .lastName("John")
//                .login("jacek")
//                .mail("jacek@gmail.com")
//                .password("pass")
//                .build());
//        mockMvc.perform(put("/api/users/" + save.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(UserDto.builder()
//                                .firstName("John")
//                                .lastName("Kowalski")
//                                .login("jacek")
//                                .mail("jacek@gmail.com")
//                                .password("password")
//                                .confirmPassword("password")
//                                .build())))
//                .andExpect(status().isForbidden())
//                .andExpect(jsonPath("$").doesNotExist());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void shouldUpdateUserWhenUserIsAdmin() throws Exception {
//        User save = userRepository.save(User.builder()
//                .firstName("John")
//                .lastName("John")
//                .login("john")
//                .mail("john@gmail.com")
//                .password("pass")
//                .build());
//        mockMvc.perform(put("/api/users/" + save.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(UserDto.builder()
//                                .firstName("John")
//                                .lastName("Kowalski")
//                                .login("john")
//                                .mail("john@gmail.com")
//                                .password("password")
//                                .confirmPassword("password")
//                                .build())))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.password").doesNotExist())
//                .andExpect(jsonPath("$.confirmPassword").doesNotExist())
//                .andExpect(jsonPath("$.login").value("john"))
//                .andExpect(jsonPath("$.mail").value("john@gmail.com"))
//                .andExpect(jsonPath("$.firstName").value("John"))
//                .andExpect(jsonPath("$.lastName").value("Kowalski"))
//                .andExpect(jsonPath("$.createdDate").exists())
//                .andExpect(jsonPath("$.createdBy").value("user"))
//                .andExpect(jsonPath("$.lastModifiedDate").exists())
//                .andExpect(jsonPath("$.lastModifiedBy").value("user"))
//                .andExpect(jsonPath("$.revisionType").doesNotExist())
//                .andExpect(jsonPath("$.revisionNumber").doesNotExist());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void shouldNotUpdateUserWhenUserDoesNotExist() throws Exception {
//        mockMvc.perform(put("/api/users/1"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$").doesNotExist());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void shouldDeleteUser() throws Exception {
//        User save = userRepository.save(User.builder()
//                .firstName("John")
//                .lastName("John")
//                .login("john")
//                .mail("john@gmail.com")
//                .password("pass")
//                .build());
//        mockMvc.perform(delete("/api/users/" + save.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").doesNotExist());
//    }
//
//    @Test
//    @WithMockUser(username = "john")
//    void shouldNotDeleteUserWhenUserIsNotAdmin() throws Exception {
//        User save = userRepository.save(User.builder()
//                .firstName("John")
//                .lastName("John")
//                .login("john")
//                .mail("john@gmail.com")
//                .password("pass")
//                .build());
//        mockMvc.perform(delete("/api/users/" + save.getId()))
//                .andExpect(status().isForbidden())
//                .andExpect(jsonPath("$").doesNotExist());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void shouldNotDeleteUserWhenUserDoesNotExist() throws Exception {
//        mockMvc.perform(delete("/api/users/1"))
//                .andExpect(status().isNoContent())
//                .andExpect(jsonPath("$").doesNotExist());
//    }
    @BeforeAll
    public static void prepareData() {
        UserUtils.prepareData();
    }

    @Test
    public void shouldFindUsers() {
        var result = userController.findUsers();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(), result.getBody());
    }
    @Test
    public void shouldFindUser() {
        Mockito.when(userMapper.toDto(any())).thenReturn(userDtos.get(0));
        var result = userController.findUser(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userDtos.get(0), result.getBody());
    }
    @Test
    public void shouldNotFindUser() throws Exception {
        mockMvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void shouldMakeUser() {
        var result = userController.saveUser(userDtos.get(0));

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
