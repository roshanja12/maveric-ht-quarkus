package org.maveric.quarkus.panache.enums;

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
