package vn.bachdao.userservice.service.impl;

import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.bachdao.userservice.dto.LoginReqeustDTO;
import vn.bachdao.userservice.dto.identity.request.TokenExchangeParam;
import vn.bachdao.userservice.dto.identity.request.UserTokenExchangeParam;
import vn.bachdao.userservice.dto.identity.response.TokenExchangeResponse;
import vn.bachdao.userservice.repository.IdentityClient;
import vn.bachdao.userservice.repository.UserRepository;
import vn.bachdao.userservice.service.IAuthService;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdentityClient identityClient;

    @Value("${idp.client-id}")
    @NonFinal
    String clientId;

    @Value("${idp.client-secret}")
    @NonFinal
    String clientSecret;

    @Override
    public TokenExchangeResponse login(LoginReqeustDTO dto) {
        return this.identityClient.exchangeUserToken(UserTokenExchangeParam.builder()
                .grant_type("password")
                .client_secret(clientSecret)
                .client_id(clientId)
                .scope("openid")
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build());
    }
}
