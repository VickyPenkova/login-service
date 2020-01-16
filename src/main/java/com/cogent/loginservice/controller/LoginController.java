package com.cogent.loginservice.controller;

import com.cogent.loginservice.constants.WebResourceKeyConstants;
import com.cogent.loginservice.requestDTO.LoginRequestDTO;
import com.cogent.loginservice.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = WebResourceKeyConstants.BASE_API)
@Api(value = "This is login controller")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = WebResourceKeyConstants.LOGIN)
    @ApiOperation(value = "This is login api", notes = "Request contains username and password")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = loginService.login(loginRequestDTO);
        updateUserToken(loginRequestDTO, token);
        return ok().body(token);
    }

    private void updateUserToken(LoginRequestDTO user, String token){
        String uri = "http://localhost:8082/api/user/update/" + user.getUsername() + "/" + token;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(uri, LoginRequestDTO.class);
    }


}
