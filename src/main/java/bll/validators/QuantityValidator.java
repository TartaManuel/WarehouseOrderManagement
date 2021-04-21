package bll.validators;

import model.Orders;

/**
 * Clasa care valideaza cantitatea introdusa intr-un order
 */
public class QuantityValidator implements Validator<Orders>{
    private static final int MIN_QUANTITY = 0;

    public void validate(Orders t) {

        if (t.getDesiredQuantity() < MIN_QUANTITY) {
            throw new IllegalArgumentException("The limit for order quantity is not respected!");
        }

    }
}
