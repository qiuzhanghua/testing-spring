package com.example;

public class Sum implements Expression {
    public final Expression augmend;
    public final Expression addmend;
    public Sum(Expression augmend, Expression addmend) {
        this.augmend = augmend;
        this.addmend = addmend;
    }

    public Money reduce(Bank bank, String currency) {
        int amount = augmend.reduce(bank, currency).amount + addmend.reduce(bank, currency).amount;
        return new Money(amount, currency);
    }


    @Override
    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    @Override
    public Expression times(int multiplier) {
        return new Sum(augmend.times(multiplier), addmend.times(multiplier));
    }
}
