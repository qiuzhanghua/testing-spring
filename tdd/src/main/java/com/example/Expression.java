package com.example;

public interface Expression {
    Money reduce(Bank bank, String currency);
    Expression plus(Expression addend);
    Expression times(int multiplier);
}
