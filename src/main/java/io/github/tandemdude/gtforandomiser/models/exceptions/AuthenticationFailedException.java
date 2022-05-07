package io.github.tandemdude.gtforandomiser.models.exceptions;

public class AuthenticationFailedException extends Exception {
    public AuthenticationFailedException() {}

    public AuthenticationFailedException(String message)
    {
        super(message);
    }
}
