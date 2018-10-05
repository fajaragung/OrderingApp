package com.robotsoftware.orderingapp.activities.controllers;

import android.widget.EditText;

public class Validate {

    /**
     * this for validate user input name
     */
    public boolean validateName(EditText fieldName, String resultName) {

        boolean validate = false;

        if (resultName.isEmpty()) {
            validate = false;
            fieldName.setError("Name should not be empty!");
        } else {
            if (resultName.length() < 5) {
                validate = false;
                fieldName.setError("Name is too short!");
            } else {
                validate = true;
                fieldName.setError(null);
            }
        }

        return validate;
    }

    /**
     * this for validate quantity
     */
    public boolean validateQuantity(int resultQuantity) {

        boolean validate = false;

        validate = resultQuantity != 0;

        return validate;
    }

    /**
     * this for validate price
     */
    public boolean validatePrice(int resultPrice) {

        boolean validate = false;

        validate = resultPrice != 0;

        return validate;
    }
}
