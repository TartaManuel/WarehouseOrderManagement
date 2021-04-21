package bll.validators;

import model.Client;

/**
 * Clasa care valideaza varsta inserata pentru client
 * Daca varsta nu este in invervalul bun, se arunca exceptie
 */
public class ClientAgeValidator implements Validator<Client> {
    private static final int MIN_AGE = 14;
    private static final int MAX_AGE = 99;

    public void validate(Client t) {

        if (t.getAge() < MIN_AGE || t.getAge() > MAX_AGE) {
            throw new IllegalArgumentException("The Client Age limit is not respected!");
        }

    }

}
