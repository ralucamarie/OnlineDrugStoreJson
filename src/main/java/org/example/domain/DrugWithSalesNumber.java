package org.example.domain;

public class DrugWithSalesNumber extends  Entity {
    private String drugName;
    private int salesNumber;

    public DrugWithSalesNumber(int idEntity, String drugName, int salesNumber) {
        super(idEntity);
        this.drugName = drugName;
        this.salesNumber = salesNumber;
    }

    public String getDrugName() {
        return drugName;
    }

    public int getSalesNumber() {
        return salesNumber;
    }

    @Override
    public String toString() {
        return "DrugWithSalesNumber{" +
                "idEntity='" + super.getIdEntity() + '\'' +
                "drugName='" + drugName + '\'' +
                ", salesNumber=" + salesNumber +
                '}';
    }
}
