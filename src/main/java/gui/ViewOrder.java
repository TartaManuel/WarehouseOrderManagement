package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * View-ul pentru operatia de inserare a unui Order
 */
public class ViewOrder extends JFrame{

    private JLabel idClient=new JLabel("idClient:");
    private JTextField fieldIdClient=new JTextField(5);
    private JLabel idProdus=new JLabel("idProdus:");
    private JTextField fieldIdProdus=new JTextField(5);
    private JLabel stock=new JLabel("Quantity:");
    private JTextField fieldQuantity=new JTextField(25);

    private JButton butonCreate= new JButton("create Order");


    private JButton butonInapoi= new JButton("Inapoi");


    public ViewOrder(){
        JPanel c=new JPanel();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.setPreferredSize(new Dimension(600,150));
        c.setBackground(Color.blue);

        butonCreate.setPreferredSize(new Dimension(130,30));
        butonInapoi.setPreferredSize(new Dimension(130,30));

        JPanel linia1=new JPanel();
        linia1.setBackground(Color.blue);
        linia1.setLayout(new FlowLayout());
        linia1.add(idClient);
        linia1.add(fieldIdClient);
        linia1.add(idProdus);
        linia1.add(fieldIdProdus);
        linia1.add(stock);
        linia1.add(fieldQuantity);

        JPanel linia2=new JPanel();
        linia2.setBackground(Color.blue);
        linia2.setLayout(new FlowLayout());
        linia2.add(butonCreate);
        linia2.add(butonInapoi);

        c.add(linia1);
        c.add(linia2);

        this.setContentPane(c);
        this.pack();
        this.setTitle("Pagina Order");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String getIdClient(){
        return fieldIdClient.getText();
    }
    public String getIdProdus(){
        return fieldIdProdus.getText();
    }
    public String getDesiredQuantity(){
        return fieldQuantity.getText();
    }

    public void butonInapoiListener(ActionListener bInapoi){
        butonInapoi.addActionListener(bInapoi);
    }
    public void butonCreateListener(ActionListener bCreate) {butonCreate.addActionListener(bCreate);}
}
