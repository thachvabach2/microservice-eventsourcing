package vn.bachdao.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.bachdao.userservice.dto.LoginReqeustDTO;
import vn.bachdao.userservice.dto.identity.response.TokenExchangeResponse;
import vn.bachdao.userservice.service.IAuthService;
import vn.bachdao.userservice.service.impl.AuthServiceImpl;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    ResponseEntity<TokenExchangeResponse> login(@RequestBody LoginReqeustDTO dto) {
        return ResponseEntity.ok(this.authService.login(dto));
    }
}
