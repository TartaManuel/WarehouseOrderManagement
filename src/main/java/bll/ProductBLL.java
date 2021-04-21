package bll;

import bll.validators.ClientAgeValidator;
import bll.validators.EmailValidator;
import bll.validators.StockValidator;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa care implementeaza operatiile cu baza de date pentru Product
 * Inainte, se fac valdarile unde este cazul
 */

public class ProductBLL {
    private List<Validator<Product>> validators;

    public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        validators.add(new StockValidator());

    }

    //din nou, metodele functioneaza la fel

    public Product findProductByIdBLL(int id) {
        ProductDAO product= new ProductDAO();
        Product st = product.findProductById(id);
        if (st == null) {
            throw new NoSuchElementException("The Product with id =" + id + " was not found!");
        }
        return st;
    }

    public List<Product> findAllProductsBLL() {
        ProductDAO product= new ProductDAO();
        List<Product> st = product.findAllProducts();
        return st;
    }

    public int insertProductBLL(Product product) { //primim un student pe care vrem sa il inseram
        for (Validator<Product> v : validators) {
            v.validate(product);//prima data facem validarile, validate da throw la o exceptie daca nu e bine
        }
        ProductDAO c=new ProductDAO();
        return c.insertProduct(product);//apelam insert din StudentDAO, care returneaza un int
    }

    public void updateProductBLL(Product product) { //primim un student pe care vrem sa il inseram
        for (Validator<Product> v : validators) {
            v.validate(product);//prima data facem validarile, validate da throw la o exceptie daca nu e bine
        }
        ProductDAO p=new ProductDAO();
        p.updateProduct(product);//apelam insert din StudentDAO, care returneaza un int
    }

    public void deleteProductBLL(int id) {
        ProductDAO p=new ProductDAO();
        p.deleteProduct(id);
    }
}
