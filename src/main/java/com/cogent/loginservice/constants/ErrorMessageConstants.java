package com.cogent.loginservice.constants;

/*THIS CLASS CONTAINS CUSTOM ERROR MESSAGES FOR VARIOUS EXCEPTIONS*/
public class ErrorMessageConstants {

    public interface ForgetPassword {
        String DEVELOPER_MESSAGE = "Password didn't match with the original one.";
        String MESSAGE = "Incorrect password.Forgot Password?";
    }

    public interface IncorrectPasswordAttempts {
        String DEVELOPER_MESSAGE = "User is blocked";
        String MESSAGE = "User is blocked. Please contact your system administrator.";
    }


    public interface InvalidUsername {
        String DEVELOPER_MESSAGE = "User entity returned null";
        String MESSAGE = "User with given username doesn't exits.";
    }
}
