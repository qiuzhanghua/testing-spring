package com.example;

public interface Expression {
    public Money reduce(Bank bank, String currency);
}
