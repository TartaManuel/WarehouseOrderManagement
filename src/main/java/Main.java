import bll.ClientBLL;
import bll.ProductBLL;
import bll.OrderBLL;
import gui.*;
import model.Client;
import model.Orders;
import model.Product;

import java.util.List;

/**
 * Clasa main in care am View-urile doar
 * Dau setVisible la primul view si de acolo o sa merg secvential in functie de butonul apasat
 */
public class Main {
    public static void main(String[] argv) {
        View view = new View();
        ViewClient view1=new ViewClient();
        ViewProduct view2=new ViewProduct();
        ViewOrder view3=new ViewOrder();
        ViewTable view4=new ViewTable();
        Controller controller=new Controller(view,view1,view2,view3,view4);
        view.setVisible(true);

    }
}
