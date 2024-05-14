package bench.artshop.order.dto;

import bench.artshop.order.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String mail;
    private String role;
}
