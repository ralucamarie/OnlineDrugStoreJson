package org.example.domain;

//1.2. CRUD tranzacție: id, id_medicament (trebuie să existe), nr. card client (întreg), nr_bucăți, data
//și ora. Tranzacția se poate face doar dacă nr. bucăți din tranzacție < nr. bucăți din medicament.
//Nu este necesar să actualizați numărul de bucăți pentru medicament.
public class Transaction extends Entity {
    private int idDrug;
    private int cardNumberClient;
    private int soldPiecesNumber;
    String dateAndHour;

    public Transaction(int idEntity, int idDrug, int cardNumberClient, int soldPiecesNumber, String dateAndHour) {
        super(idEntity);
        this.idDrug = idDrug;
        this.cardNumberClient = cardNumberClient;
        this.soldPiecesNumber = soldPiecesNumber;
        this.dateAndHour = dateAndHour;
    }

    public int getIdDrug() {
        return idDrug;
    }

    public int getCardNumberClient() {
        return cardNumberClient;
    }

    public int getSoldPiecesNumber() {
        return soldPiecesNumber;
    }

    public String getDateAndHour() {
        return dateAndHour;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "idEntity=" + getIdEntity() +
                ", idDrug=" + idDrug +
                ", cardNumberClient=" + cardNumberClient +
                ", pieces_number=" + soldPiecesNumber +
                ", dateAndHour='" + dateAndHour + '\'' +
                '}';
    }

}
