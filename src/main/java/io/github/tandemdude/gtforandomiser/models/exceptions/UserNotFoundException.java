package io.github.tandemdude.gtforandomiser.models.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {}

    public UserNotFoundException(String message)
    {
        super(message);
    }
}
