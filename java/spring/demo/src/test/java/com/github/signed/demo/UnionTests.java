package com.github.signed.demo;

import org.junit.jupiter.api.Test;

public class UnionTests {
    sealed interface Animal permits Dog, Cat {
    }

    static final class Dog implements Animal {
        public void bark() {
            System.out.println("Woof!");
        }
    }

    static final class Cat implements Animal {
        public void meow() {
            System.out.println("Meow!");
        }
    }

    @Test
    void name() {
        Animal animal = animal();

        switch (animal) {
            case Dog dog -> {
                dog.bark();
            }
            case Cat cat -> {
                cat.meow();
            }
        }
    }

    private Animal animal() {
        return new Cat();
    }
}
