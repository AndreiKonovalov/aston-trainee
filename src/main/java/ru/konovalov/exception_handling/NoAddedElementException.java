package ru.konovalov.exception_handling;

public class NoAddedElementException extends RuntimeException{

    public NoAddedElementException(String message){
        super(message);
    }
}
