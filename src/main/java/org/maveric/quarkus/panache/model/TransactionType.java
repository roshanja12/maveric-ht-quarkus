package org.maveric.quarkus.panache.model;

public enum TransactionType {
    WITHDRAW(1),DEPOSIT(2);
    int value;

    TransactionType(int value){
        this.value=value;
    }

    int getValue(){
        return value;
    }
}
