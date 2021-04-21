package bll;

import bll.validators.QuantityValidator;
import bll.validators.Validator;
import dao.OrderDAO;
import model.Orders;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa care implementeaza operatiile pe Orders, si anume findOrderById si inserarea unui order
 * Si aici facem validari pentru desiredQuantity
 */
public class OrderBLL {
    private List<Validator<Orders>> validators;

    public OrderBLL() {
        validators = new ArrayList<Validator<Orders>>();
        validators.add(new QuantityValidator());
    }

    //metodele functioneaza la fel ca pentru celelalte clase, CLient si Product pentru ca metodele de lucru cu bazele de
    //date sunt implementate cu Reflection

    public Orders findOrderByIdBLL(int id) {
        OrderDAO order= new OrderDAO();
        Orders st = order.findOrderById(id);
        if (st == null) {
            throw new NoSuchElementException("The Order with id =" + id + " was not found!");
        }
        return st;
    }

    public int insertOrderBLL(Orders order) { //primim un student pe care vrem sa il inseram
        for (Validator<Orders> v : validators) {
            v.validate(order);//prima data facem validarile, validate da throw la o exceptie daca nu e bine
        }
        OrderDAO c=new OrderDAO();
        return c.insertOrder(order);//apelam insert din StudentDAO, care returneaza un int
    }
}
