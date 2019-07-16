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

import org.junit.Test;

import static org.junit.Assert.*;

public class FnIfPrimitiveTest {

    @Test
    public void testMeetsConditionWhenReturnValueIsZero() {
        final Integer result = FnIf.of(true)
                .then(s -> 0)
                .orFail(s -> 1);
        assertEquals(0, (int) result);
    }

    @Test
    public void testMeetsConditionWhenReturnValueIsOne() {
        final Integer result = FnIf.of(true)
                .then(s -> 1)
                .orFail(s -> 0);
        assertEquals(1, (int) result);
    }

    @Test
    public void testMeetsConditionWhenReturnValueIsFalse() {
        final Boolean result = FnIf.of(true)
                .then(s -> false)
                .orFail(s -> true);
        assertFalse(result);
    }

    @Test
    public void testMeetsConditionWhenReturnValueIsTrue() {
        final Boolean result = FnIf.of(true)
                .then(s -> true)
                .orFail(s -> false);
        assertTrue(result);
    }

}
