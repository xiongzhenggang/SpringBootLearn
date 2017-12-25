package com.xzg.cn.java9Test.javadesage.data_mapper;

public final class DataMapperException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new runtime exception with the specified detail message. The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the
     *        {@link #getMessage()} method.
     */
    public DataMapperException(final String message) {
        super(message);
    }
}