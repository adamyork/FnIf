# FnIf
functional-esque control flow for java

## 
replaces traditional control flow in java, with a more stream friendly option.

### Example

````code
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
````

becomes...

````code
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
````

[inspired by an earlier project](https://github.com/adamyork/logic-tree)





