package dao;

import model.Client;
import model.Orders;

import java.util.NoSuchElementException;

public class OrderDAO extends AbstractDAO<Orders>{
    public Orders findOrderById(int id) {
        return findById(id);
    }
    public int insertOrder(Orders or){// throws Exception {
        return insert(or);
    }
}
