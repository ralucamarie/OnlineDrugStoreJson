package org.example.domain;

public class Drug extends Entity{
    private String name;
    private String producer;
    private float price;
    private boolean needsPrescription;
    private int piecesNumber;

    public Drug(int idEntity, String name, String producer, float price, boolean needsPrescription, int piecesNumber) {
        super(idEntity);
        this.name = name;
        this.producer = producer;
        this.price = price;
        this.needsPrescription = needsPrescription;
        this.piecesNumber = piecesNumber;
    }

    public String getName() {
        return name;
    }

    public String getProducer() {
        return producer;
    }

    public float getPrice() {
        return price;
    }

    public boolean isNeedsPrescription() {
        return needsPrescription;
    }

    public int getPiecesNumber() {
        return piecesNumber;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "idEntity=" + getIdEntity() +
                ", name='" + name + '\'' +
                ", producer='" + producer + '\'' +
                ", price=" + price +
                ", needsPrescription=" + needsPrescription +
                ", piecesNumber=" + piecesNumber +
                '}';
    }
}
