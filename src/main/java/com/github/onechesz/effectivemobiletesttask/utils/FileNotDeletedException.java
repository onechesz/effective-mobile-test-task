package com.github.onechesz.effectivemobiletesttask.utils;

public class FileNotDeletedException extends RuntimeException {
    public FileNotDeletedException(String message) {
        super(message);
    }
}
