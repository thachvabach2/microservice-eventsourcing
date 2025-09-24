package vn.bachdao.userservice.dto.identity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.bachdao.userservice.dto.identity.Credential;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationParam {

    String username;
    boolean enabled;
    String email;
    boolean emailVerified;
    String firstName;
    String lastName;

    List<Credential> credentials;
}
