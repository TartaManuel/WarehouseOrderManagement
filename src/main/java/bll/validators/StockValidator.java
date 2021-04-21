package bll.validators;

import model.Product;

/**
 * Clasa care valideaza stock-ul pentru un produs introdus
 * Stock-ul trebuie sa fie pozitiv
 */
public class StockValidator implements Validator<Product> {
    private static final int MIN_STOCK = 0;

    public void validate(Product t) {

        if (t.getStock() < MIN_STOCK) {
            throw new IllegalArgumentException("The limit for stock product is not respected!");
        }

    }
}
