package org.example.domain;

import java.util.List;

public class DrugValidator {
    StringBuilder stringBuilder = new StringBuilder();
    public void validate(Drug drug) throws Exception {
        if (drug.getPrice() <= 0) {
            stringBuilder.append("The price is lower than 0");
        }

        if (drug.getPiecesNumber() <= 0) {
            stringBuilder.append("The number of pieces is lower than 0");
        }

        if (stringBuilder.length() > 0) {
            throw new Exception(stringBuilder.toString());
        }
    }
}



