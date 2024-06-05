package ru.skypro.homework.exceptions;

public class NotEditUserPasswordException extends RuntimeException {

    public NotEditUserPasswordException(String message) {
        super(message);
    }
}
