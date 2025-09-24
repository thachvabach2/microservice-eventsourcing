package vn.bachdao.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginReqeustDTO {
    String username;
    String password;
}
