package vn.bachdao.userservice.service;

import vn.bachdao.userservice.dto.LoginReqeustDTO;
import vn.bachdao.userservice.dto.identity.response.TokenExchangeResponse;

public interface IAuthService {
    TokenExchangeResponse login(LoginReqeustDTO dto);
}
