package ru.javawebinar.basejava.util;

import java.util.function.Consumer;

public class DataStreamUtil {

    public static final String DUMMY_VALUE_FOR_NULL = "%DUMMY_VALUE_FOR_NULL%";

    private DataStreamUtil() {
    }

    public static <T, E extends Exception> Consumer<T> handlingConsumerWrapper(
            ThrowingConsumer<T, E> throwingConsumer
            , Class<E> exceptionClass) {

        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    System.err.println(
                            "Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    public static String getOptionalValue(String value) {
        if (value == null) {
            return DUMMY_VALUE_FOR_NULL;
        }
        return value.equals(DUMMY_VALUE_FOR_NULL) ? null : value;
    }


}
