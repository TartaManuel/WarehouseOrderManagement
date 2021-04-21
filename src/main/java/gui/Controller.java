package gui;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Clasa Controller in care realizez legatura intre butoane si functionalitatile pe care trebuie sa le aiba
 */

public class Controller {

    private View view;
    private ViewClient viewClient;
    private ViewProduct viewProduct;
    private ViewOrder viewOrder;
    private ViewTable viewTable;

    int i=0;

    public Controller(View view, ViewClient view1,ViewProduct view2,ViewOrder view3,ViewTable view4){
        this.view=view;
        this.viewClient=view1;
        this.viewProduct=view2;
        this.viewOrder=view3;
        this.viewTable=view4;


        view.butonClientListener(new ClientListener());
        view.butonProductListener(new ProductListener());
        view.butonOrderListener(new OrderListener());
        viewClient.butonInapoiListener(new InapoiClient());
        viewProduct.butonInapoiListener(new InapoiProduct());
        viewOrder.butonInapoiListener(new InapoiOrder());

        viewClient.butonAddListener(new AddClientListener());
        viewClient.butonDeleteListener(new DeleteClientListener());
        viewClient.butonEditListener(new EditClientListener());
        viewClient.butonViewListener(new ViewClientListener());

        viewProduct.butonAddListener(new AddProductListener());
        viewProduct.butonDeleteListener(new DeleteProductListener());
        viewProduct.butonEditListener(new EditProductListener());
        viewProduct.butonViewListener(new ViewProductListener());

        viewOrder.butonCreateListener(new CreateOrderListener());

        viewTable.butonInapoiListener(new InapoiTable());

    }

    //primele 3 butoane pentru a alege pe cine vrem sa facem operatiile dintre cele 3 clase

    class ClientListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            viewClient.setVisible(true);
            view.setVisible(false);
        }
    }
    class ProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            viewProduct.setVisible(true);
            view.setVisible(false);
        }
    }
    class OrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            viewOrder.setVisible(true);
            view.setVisible(false);
        }
    }

    //butoanele de Inapoi
    class InapoiClient implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            viewClient.setVisible(false);
            view.setVisible(true);
        }
    }
    class InapoiProduct implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            viewProduct.setVisible(false);
            view.setVisible(true);
        }
    }
    class InapoiOrder implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            viewOrder.setVisible(false);
            view.setVisible(true);
        }
    }

    class InapoiTable implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            viewTable.setVisible(false);
            view.setVisible(true);
        }
    }

    //Operatiile pe CLient

    //Apelez insert, afisez niste mesaje de eroare daca parametrii nu sunt buni sau daca nu putem insera din diferite cauze
    class AddClientListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                ClientBLL c=new ClientBLL();
                String nume= viewClient.getNumeAdd();
                String adresa= viewClient.getAdresaAdd();
                String email= viewClient.getEmailAdd();
                int age= Integer.parseInt(viewClient.getAgeAdd());
                if(nume.equals("")==true||adresa.equals("")==true||email.equals("")==true){
                    JOptionPane.showMessageDialog(view, "Introduceti toti parametrii!","Error", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    Client client=new Client(nume,adresa,email,age);
                    try{
                        c.insertClientBLL(client);
                        JOptionPane.showMessageDialog(view, "Inserare cu succes","Error", JOptionPane.INFORMATION_MESSAGE);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(view, ex.getMessage(),"Error", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, "Introduceti parametrii corecti!","Error", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    class DeleteClientListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            try{
                int id= Integer.parseInt(viewClient.getIdDelete());
                try{
                    ClientBLL c=new ClientBLL();
                    c.findClientByIdBLL(id); //prima data caut daca clientul cu acel id exista
                    //daca nu exista o sa apara un mesaj de eroare
                    c.deleteClientBLL(id);//daca clientul exista, il sterg
                    JOptionPane.showMessageDialog(view, "Stergere cu succes","Error", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, ex.getMessage(),"Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, "Introduceti parametrii corecti!","Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    class EditClientListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                ClientBLL c=new ClientBLL();
                int id= Integer.parseInt(viewClient.getIdEdit());
                String nume= viewClient.getNumeEdit();
                String adresa= viewClient.getAdresaEdit();
                String email= viewClient.getEmailEdit();
                int age= Integer.parseInt(viewClient.getAgeEdit());
                if(nume.equals("")==true||adresa.equals("")==true||email.equals("")==true){
                    JOptionPane.showMessageDialog(view, "Introduceti toti parametrii!","Error", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    Client client=new Client(id,nume,adresa,email,age);//creez un obiect de tip Client cu datele luate de
                    //pe interfata
                    try{
                        c.updateClientBLL(client);//dau update cu acel obiect
                        JOptionPane.showMessageDialog(view, "Update cu succes","Error", JOptionPane.INFORMATION_MESSAGE);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(view, ex.getMessage(),"Error", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, "Introduceti parametrii corecti!","Error", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    class ViewClientListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ClientBLL cb=new ClientBLL();
            List<Client> clients=cb.findALlClientsBLL();//apelez metoda si stochez intr-o lista toti clientii

            String[] columns=new String[Client.class.getDeclaredFields().length];
            String[][] data=new String[clients.size()][Client.class.getDeclaredFields().length];
            //creez stringurile necesare pentru JTable
            //imi trebuie prima data o linie cu coloanele necesare
            //dupa cate un client pe fiecare linie

            int i=0;
            for(Field field:Client.class.getDeclaredFields()){
                columns[i]=field.getName();
                i++;
            }
            int k;
            for(int j=0;j<clients.size();j++){
                k=0;
                for(Field field:clients.get(j).getClass().getDeclaredFields()){//extrag valorile din campuri cu Reflection
                    field.setAccessible(true);
                    try {
                        data[j][k]=field.get(clients.get(j)).toString();
                        k++;
                    }
                    catch(Exception ex){

                    }
                }
            }
            for(int a=0;a<data.length;a++){
                for(int j=0;j<columns.length;j++) {
                }
            }
            viewTable.setTable(columns,data); //apelez metoda de set a tabelului
            viewTable.setVisible(true);
            viewClient.setVisible(false);

        }
    }

    //Metodele pentru Product, merg exact la fel doar ca lucram pe alta tabela
    class AddProductListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                ProductBLL pb=new ProductBLL();
                String nume= viewProduct.getNumeAdd();
                int stock= Integer.parseInt(viewProduct.getStockAdd());
                if(nume.equals("")==true){
                    JOptionPane.showMessageDialog(view, "Introduceti toti parametrii!","Error", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    Product p=new Product(nume,stock);
                    try{
                        pb.insertProductBLL(p);
                        JOptionPane.showMessageDialog(view, "Inserare cu succes","Error", JOptionPane.INFORMATION_MESSAGE);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(view, ex.getMessage(),"Error", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, "Introduceti parametrii corecti!","Error", JOptionPane.INFORMATION_MESSAGE);
            }


        }
    }

    class DeleteProductListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                int id= Integer.parseInt(viewProduct.getIdDelete());
                // System.out.println("Id "+id);
                try{
                    ProductBLL pb=new ProductBLL();
                    pb.findProductByIdBLL(id);
                    pb.deleteProductBLL(id);
                    JOptionPane.showMessageDialog(view, "Stergere cu succes","Error", JOptionPane.INFORMATION_MESSAGE);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(view, ex.getMessage(),"Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, "Introduceti parametrii corecti!","Error", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    class EditProductListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                ProductBLL pb=new ProductBLL();
                int id= Integer.parseInt(viewProduct.getIdEdit());
                String nume= viewProduct.getNumeEdit();
                int stock= Integer.parseInt(viewProduct.getStockEdit());
                if(nume.equals("")==true){
                    JOptionPane.showMessageDialog(view, "Introduceti toti parametrii!","Error", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    Product p=new Product(id,nume,stock);
                    try{
                        pb.updateProductBLL(p);
                        JOptionPane.showMessageDialog(view, "Update cu succes","Error", JOptionPane.INFORMATION_MESSAGE);
                    }catch(Exception ex){
                        // ex.printStackTrace();
                        JOptionPane.showMessageDialog(view, ex.getMessage(),"Error", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, "Introduceti parametrii corecti!","Error", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    class ViewProductListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ProductBLL cb=new ProductBLL();
            List<Product> products=cb.findAllProductsBLL();

            String[] columns=new String[Product.class.getDeclaredFields().length];
            String[][] data=new String[products.size()][Client.class.getDeclaredFields().length];

            int i=0;
            for(Field field:Product.class.getDeclaredFields()){
                columns[i]=field.getName();
                i++;
            }
            int k;
            for(int j=0;j<products.size();j++){
                k=0;
                for(Field field:products.get(j).getClass().getDeclaredFields()){
                    field.setAccessible(true);
                    try {
                        data[j][k]=field.get(products.get(j)).toString();
                        k++;
                    }
                    catch(Exception ex){

                    }
                }
            }
            for(int a=0;a<data.length;a++){
                for(int j=0;j<columns.length;j++) {
                }
            }
            viewTable.setTable(columns,data);
            viewTable.setVisible(true);
            viewProduct.setVisible(false);
        }
    }

    //metoda de inserare a Order-urilor
    class CreateOrderListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                OrderBLL ob=new OrderBLL();
                ClientBLL c=new ClientBLL();
                ProductBLL p=new ProductBLL();

                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter("log"+i+".txt");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                PrintWriter printWriter = new PrintWriter(fileWriter);

                int idClient= Integer.parseInt(viewOrder.getIdClient());
                int idProdus= Integer.parseInt(viewOrder.getIdProdus());
                int quantity= Integer.parseInt(viewOrder.getDesiredQuantity());
                try{
                    Client cl= c.findClientByIdBLL(idClient);
                    Product pr= p.findProductByIdBLL(idProdus); //verific daca exista clientul si produsul
                    //daca nu exista o sa dea o exceptie
                    pr.setStock(pr.getStock()-quantity);//actualizez stocul pentru produs
                    p.updateProductBLL(pr); //updated produsul
                    Orders o=new Orders(idClient,idProdus,quantity); //creez un obiect de tipul Orders pe care
                    //o sa il inserez
                    try{
                        ob.insertOrderBLL(o);
                        printWriter.println("Chitanta numarul "+i); //creez chitanta in care scriu date despre
                        //client, produs si cantitatea ceruta
                        printWriter.println(cl.toString());
                        printWriter.println(pr.toString());
                        printWriter.println("Cantitatea dorita: "+quantity);
                        i++;
                        JOptionPane.showMessageDialog(view, "Inserare cu succes","Error", JOptionPane.INFORMATION_MESSAGE);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(view, ex.getMessage(),"Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(view, ex.getMessage(),"Error", JOptionPane.INFORMATION_MESSAGE);
                }
                printWriter.close();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(view, "Introduceti parametrii corecti!","Error", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }
}
