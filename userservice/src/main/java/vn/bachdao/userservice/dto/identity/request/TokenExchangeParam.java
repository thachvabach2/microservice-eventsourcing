package vn.bachdao.userservice.dto.identity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenExchangeParam {

    String grant_type;
    String client_id;
    String client_secret;
    String scope;
}
