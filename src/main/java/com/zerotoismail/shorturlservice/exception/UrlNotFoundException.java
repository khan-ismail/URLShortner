package com.zerotoismail.shorturlservice.exception;

/**
 * Custom runtime exception thrown when a requested short URL key cannot be found in persistence.
 * <p>
 * Handled by {@link GlobalException#handleUrlNotFoundException(UrlNotFoundException)}
 * which maps it to an HTTP 404 (Not Found) response containing an {@code ErrorDto}.
 * </p>
 *
 * @author ZeroToIsmail
 * @version 1.0.0
 */
public class UrlNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code UrlNotFoundException} with the specified detail message.
     *
     * @param msg Descriptive explanation containing the missing short URL key
     */
    public UrlNotFoundException(String msg) {
        super(msg);
    }
}
