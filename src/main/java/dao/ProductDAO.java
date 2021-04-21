package dao;

import model.Client;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductDAO extends AbstractDAO<Product>{

    public Product findProductById(int id) {
        return findById(id);
    }

    public List<Product> findAllProducts() {
        return findAll();
    }

    public int insertProduct(Product p) {//throws Exception {
        return insert(p);
    }

    public void deleteProduct(int id){
       delete(id);
    }

    public void updateProduct(Product p){
       update(p);
    }

}
