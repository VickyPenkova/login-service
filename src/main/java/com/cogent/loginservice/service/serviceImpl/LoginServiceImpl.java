package com.cogent.loginservice.service.serviceImpl;

import com.cogent.loginservice.constants.ErrorMessageConstants.ForgetPassword;
import com.cogent.loginservice.constants.ErrorMessageConstants.IncorrectPasswordAttempts;
import com.cogent.loginservice.constants.ErrorMessageConstants.InvalidUsername;
import com.cogent.loginservice.exceptions.UnauthorisedException;
import com.cogent.loginservice.feignInterface.UserInterface;
import com.cogent.loginservice.jwt.JwtTokenProvider;
import com.cogent.loginservice.requestDTO.LoginRequestDTO;
import com.cogent.loginservice.responseDTO.UserResponseDTO;
import com.cogent.loginservice.service.LoginService;
import com.cogent.loginservice.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@Transactional("transactionManager")
public class LoginServiceImpl implements LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserInterface userInterface;

    @Override
    public String login(LoginRequestDTO requestDTO){
          //, HttpServletRequest request) {

        LOGGER.info("LOGIN PROCESS STARTED ::::");

        long startTime = DateUtils.getTimeInMillisecondsFromLocalDate();

        UserResponseDTO user = userDetails.apply(requestDTO);

        validateUserUsername.accept(user);

        validatePassword.accept(requestDTO, user);

        String jwtToken = jwtTokenProvider.createToken(requestDTO.getUsername());
              //, request);

        LOGGER.info("LOGIN PROCESS COMPLETED IN ::: " + (DateUtils.getTimeInMillisecondsFromLocalDate() - startTime)
                + " ms");

        return jwtToken;
    }

    private Function<LoginRequestDTO, UserResponseDTO> userDetails = (loginRequestDTO) -> {
        return userInterface.searchUser(loginRequestDTO.getUsername());
    };

    private Consumer<UserResponseDTO> validateUserUsername = (admin) -> {
        if (Objects.isNull(admin))
            throw new UnauthorisedException(InvalidUsername.MESSAGE, InvalidUsername.DEVELOPER_MESSAGE);
        LOGGER.info(":::: ADMIN USERNAME VALIDATED ::::");
    };

    private BiConsumer<LoginRequestDTO, UserResponseDTO> validatePassword = (requestDTO, user) -> {

        LOGGER.info(":::: USER PASSWORD VALIDATION ::::");

        if (BCrypt.checkpw(requestDTO.getPassword(), user.getPassword())) {
            user.setLoginAttempt(0);
            userInterface.updateUserLoginAttempt(user.getUsername(), user.getLoginAttempt());
        } else {
            user.setLoginAttempt(user.getLoginAttempt() + 1);

            if (user.getLoginAttempt() >= 3) {
                userInterface.updateUserLoginAttempt(user.getUsername(), user.getLoginAttempt());

                LOGGER.debug("USER IS BLOCKED DUE TO MULTIPLE WRONG ATTEMPTS...");
                throw new UnauthorisedException(IncorrectPasswordAttempts.MESSAGE,
                        IncorrectPasswordAttempts.DEVELOPER_MESSAGE);
            }

            LOGGER.debug("INCORRECT PASSWORD...");
            throw new UnauthorisedException(ForgetPassword.MESSAGE, ForgetPassword.DEVELOPER_MESSAGE);
        }

        LOGGER.info(":::: ADMIN PASSWORD VALIDATED ::::");
    };

}

