package org.example.domain;

public class CardClientWithSalesNumber {
    private int cardNumber;
    private int salesNumber;

    public CardClientWithSalesNumber(int cardNumber, int salesNumber) {
        this.cardNumber = cardNumber;
        this.salesNumber = salesNumber;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getSalesNumber() {
        return salesNumber;
    }

    public void setSalesNumber(int salesNumber) {
        this.salesNumber = salesNumber;
    }

    @Override
    public String toString() {
        return "CardClientWithSalesNumber{" +
                "cardNumber=" + cardNumber +
                ", salesNumber=" + salesNumber +
                '}';
    }
}
