package com.cogent.loginservice.feignInterface;

import com.cogent.loginservice.constants.MicroServiceConstants;
import com.cogent.loginservice.constants.MicroServiceConstants.*;
import com.cogent.loginservice.responseDTO.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = DbMicroServiceConstants.BASE)
@Service
@RequestMapping(value = MicroServiceConstants.BASE_API)
public interface UserInterface {

    @RequestMapping(value = DbMicroServiceConstants.SEARCH_USER)
    UserResponseDTO searchUser(@PathVariable String username);

    @RequestMapping(value = DbMicroServiceConstants.UPDATE_USER_LOGIN_ATTEMPT)
    void updateUserLoginAttempt(@PathVariable String username, @PathVariable Integer loginAttempt);
}
