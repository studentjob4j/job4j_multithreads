package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 08.06.2021
 */

public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}
