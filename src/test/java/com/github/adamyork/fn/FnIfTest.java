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

import static org.junit.Assert.assertEquals;

public class FnIfTest {

    @Test
    public void testMeetsSingleCondition() {
        final String result = FnIf.of(true)
                .then(s -> "foo")
                .orFail(s -> "failed");
        assertEquals(result, "foo");
    }

    @Test
    public void testDoesNotMeetSingleCondition() {
        final String result = FnIf.of(false)
                .then(s -> "foo")
                .orFail(s -> "failed");
        assertEquals(result, "failed");
    }

    @Test
    public void testMeetsSecondCondition() {
        assertEquals(getResult(1), "bar");
    }

    @Test
    public void testMeetsThirdCondition() {
        assertEquals(getResult(2), "baz");
    }

    @Test
    public void testNestedConditions() {
        final String imperativeResultShouldBeFoo = getComplexResultImperative(0, 0);
        final String functionalResultShouldBeFoo = getComplexResultFunctional(0, 0);
        assertEquals(imperativeResultShouldBeFoo, functionalResultShouldBeFoo);

        final String imperativeResultShouldBeBar = getComplexResultImperative(0, 1);
        final String functionalResultShouldBeBar = getComplexResultFunctional(0, 1);
        assertEquals(imperativeResultShouldBeBar, functionalResultShouldBeBar);

        final String imperativeResultShouldBeNonZeroBar = getComplexResultImperative(0, 2);
        final String functionalResultShouldBeNonZeroBar = getComplexResultFunctional(0, 2);
        assertEquals(imperativeResultShouldBeNonZeroBar, functionalResultShouldBeNonZeroBar);
    }

    private String getResult(final int val) {
        return FnIf.of(val == 0)
                .then(s -> "foo")
                .or(val == 1)
                .then(s -> "bar")
                .or(val == 2)
                .then(s -> "baz")
                .orFail(s -> "failed");
    }

    private String getComplexResultFunctional(final int val1, final int val2) {
        return FnIf.of(val1 == 0)
                .then(s -> "foo")
                .or(val1 == 1)
                .then(s -> FnIf.of(val2 == 0)
                        .then(f -> "bar")
                        .orFail(f -> "nonZeroBar"))
                .or(val1 == 2)
                .then(s -> FnIf.of(val2 == 0)
                        .then(f -> "baz")
                        .or(val2 == 1)
                        .then(f -> "oneBaz")
                        .orFail(f -> "failedBaz"))
                .orFail(s -> "failed");
    }

    private String getComplexResultImperative(final int val1, final int val2) {
        if (val1 == 0) {
            return "foo";
        }
        if (val1 == 1) {
            if (val2 == 0) {
                return "bar";
            }
            return "nonZeroBar";
        } else if (val1 == 2) {
            if (val2 == 0) {
                return "baz";
            } else if (val2 == 1) {
                return "oneBaz";
            } else {
                return "failedBaz";
            }
        } else {
            return "failed";
        }
    }
}
