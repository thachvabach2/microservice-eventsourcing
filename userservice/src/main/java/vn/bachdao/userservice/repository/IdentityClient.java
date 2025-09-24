package vn.bachdao.userservice.repository;

import feign.Body;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import vn.bachdao.userservice.dto.identity.request.TokenExchangeParam;
import vn.bachdao.userservice.dto.identity.request.UserCreationParam;
import vn.bachdao.userservice.dto.identity.response.TokenExchangeResponse;

@FeignClient(name = "identity-client", url = "${idp.url}")
public interface IdentityClient {

    @PostMapping (
            value = "/realms/devtaycode/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    TokenExchangeResponse exchangeClientToken(@QueryMap() TokenExchangeParam param);

    @PostMapping (
            value = "admin/realms/devtaycode/users",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> createUser(@RequestBody() UserCreationParam body, @RequestHeader("authorization") String token);
}
