package com.kabanov.scheduler.utils;

import java.util.Optional;
import java.util.function.Supplier;

public class Utils {
    
    private Utils() {
    }
    
    public static <T> T getOrThrow(Optional<T> optional, Supplier<? extends RuntimeException> exceptionSupplier){
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw exceptionSupplier.get();
        }
    }
}
