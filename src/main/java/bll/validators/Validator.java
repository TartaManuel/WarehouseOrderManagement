package bll.validators;

/**
 * Interfata care o sa fie implementata de toate clasele care fac validari
 * @param <T>
 */
public interface Validator<T> {

    public void validate(T t);
}