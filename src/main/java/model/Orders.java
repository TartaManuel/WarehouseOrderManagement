package model;

/**
 * Clasa Orders care face maparea pe tabela Orders din baza de date. Si ea contine constructori, setteri si getteri
 */
public class Orders {
    private int id;
    private int idClient;
    private int idProduct;
    private int desiredQuantity;

    public Orders(){

    }
    public Orders(int id, int idCLient, int idProduct, int desiredQuantity){
        this.id=id;
        this.idClient=idCLient;
        this.idProduct=idProduct;
        this.desiredQuantity=desiredQuantity;
    }

    public Orders(int idCLient, int idProduct, int desiredQuantity){
        this.idClient=idCLient;
        this.idProduct=idProduct;
        this.desiredQuantity=desiredQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }


    public int getDesiredQuantity(){
        return desiredQuantity;
    }

    public void setDesiredQuantity(int desiredQuantity){
        this.desiredQuantity=desiredQuantity;
    }

    public String toString() {
        return "Order [id=" + id + ", Client=" + idClient  + ", Product=" + idProduct + ", desiredQuantity=" + desiredQuantity +"]";
    }

}
