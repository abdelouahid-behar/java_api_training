package fr.lernejo;

import java.util.function.BiFunction;

public class Sample {

    public int op(Operation op, int a, int b) {
        return op.func.apply(a, b);
    }

    public int fact(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N should be positive");
        }
        return n == 0 ? 1 : n * fact(n - 1);
    }

    enum Operation {
        ADD((a, b) -> a + b),
        MULT((a, b) -> a * b),
        ;

        final BiFunction<Integer, Integer, Integer> func;

        Operation(BiFunction<Integer, Integer, Integer> func) {
            this.func = func;
        }
    }
}
