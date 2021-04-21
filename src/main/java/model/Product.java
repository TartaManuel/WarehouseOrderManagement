package model;

/**
 * Clasa Product, una dintre cele 3 tabele din baza de date
 */
public class Product {
    private int id;
    private String name;
    private int stock;

    //constructor cu toate datele
    public Product(int id, String name, int stock) {
        super();
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    //alt constructor, de data asta fara id
    public Product(String name, int stock) {
        super();
        this.name = name;
        this.stock = stock;
    }
    public Product(){

    }

    //getteri si setteri la toate campurile clasei
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    //override la toString; returneaza ca string numele clasei si campurile
    //metoda e din Object
    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name  + ", stock=" + stock + "]";
    }
}
