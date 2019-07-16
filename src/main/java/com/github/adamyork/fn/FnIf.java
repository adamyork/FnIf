/*
 * Copyright (C) 2018 Adam York
 *
 * Licensed under the MIT (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/MIT
 *
 */
package com.github.adamyork.fn;

import java.util.Optional;
import java.util.function.Function;


/**
 *
 */
@SuppressWarnings({"WeakerAccess", "OptionalUsedAsFieldOrParameterType"})
public class FnIf {

    private final Boolean condition;
    private final Optional result;

    private FnIf(final Boolean condition, final Optional result) {
        this.condition = condition;
        this.result = result;
    }

    /**
     * @param function a functional interface evaluated when the binary condition is false
     * @param <T>      the return type of the evaluation
     * @return FnIF an instance of the the class
     */
    public <T> FnIf then(final Function<T, T> function) {
        final Optional<T> maybeResult = Optional.of(condition)
                .filter(bool -> bool)
                .map(bool -> function.apply(null));
        return maybeResult.map(val -> new FnIf(true, Optional.of(val)))
                .orElseGet(() -> new FnIf(condition, result));
    }

    /**
     * @param condition an additive value to further qualify the true condition
     * @return FnIF an instance of the the class
     */
    public FnIf or(final Boolean condition) {
        return Optional.of(condition)
                .filter(bool -> bool)
                .map(bool -> new FnIf(true, result))
                .orElseGet(() -> new FnIf(false, result));
    }

    /**
     * @param function a functional interface evaluated when the binary condition is false
     * @param <T>      the return type of the evaluation
     * @return FnIF an instance of the the class
     */
    @SuppressWarnings("unchecked")
    public <T> T orFail(final Function<T, T> function) {
        return (T) result.orElseGet(() -> function.apply(null));
    }

    /**
     * @param condition binary value for branching logic.
     * @return FnIF an instance of the the class
     */
    public static FnIf of(final Boolean condition) {
        return new FnIf(condition, Optional.empty());
    }

}
