package vn.bachdao.userservice.dto.identity.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE) // generate access-modifier is Private for all attributes.
public class UserTokenExchangeParam{
    String grant_type;
    String client_id;
    String client_secret;
    String scope;
    String username;
    String password;
}
