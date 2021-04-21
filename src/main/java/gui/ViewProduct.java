package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * View-ul pentru operatiile pe Product. Sunt aceleasi ca la client
 */
public class ViewProduct extends JFrame{
    private JLabel client=new JLabel("Alegeti operatia pe product:");

    private JLabel nume=new JLabel("Nume:");
    private JTextField fieldNume=new JTextField(25);
    private JLabel stock=new JLabel("Stock:");
    private JTextField fieldStock=new JTextField(5);
    private JButton butonAdd= new JButton("add Product");

    private JLabel id1=new JLabel("ID:");
    private JTextField fieldId1=new JTextField(5);
    private JLabel nume1=new JLabel("Nume:");
    private JTextField fieldNume1=new JTextField(25);
    private JLabel stock1=new JLabel("Stock:");
    private JTextField fieldStock1=new JTextField(5);
    private JButton butonEdit= new JButton("edit Product");

    private JLabel id2=new JLabel("ID:");
    private JTextField fieldId2=new JTextField(5);
    private JButton butonDelete= new JButton("delete Product");

    private JButton butonView= new JButton("view Product");

    private JButton butonInapoi=new JButton("Inapoi");

    public ViewProduct(){
        JPanel c=new JPanel();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.setPreferredSize(new Dimension(700,450));
        c.setBackground(Color.blue);

        client.setFont(new Font( "Times New Roman",Font.BOLD,20));

        butonAdd.setPreferredSize(new Dimension(130,30));
        butonDelete.setPreferredSize(new Dimension(130,30));
        butonEdit.setPreferredSize(new Dimension(130,30));
        butonView.setPreferredSize(new Dimension(130,30));
        butonInapoi.setPreferredSize(new Dimension(130,30));

        JPanel linia1=new JPanel();
        linia1.setBackground(Color.blue);
        linia1.setLayout(new FlowLayout());

        linia1.add(client);

        JPanel linia2=new JPanel();
        linia2.setBackground(Color.blue);
        linia2.setLayout(new FlowLayout());
        linia2.add(nume);
        linia2.add(fieldNume);
        linia2.add(stock);
        linia2.add(fieldStock);
        linia2.add(butonAdd);

        JPanel linia3=new JPanel();
        linia3.setBackground(Color.blue);
        linia3.setLayout(new FlowLayout());
        linia3.add(id1);
        linia3.add(fieldId1);
        linia3.add(nume1);
        linia3.add(fieldNume1);
        linia3.add(stock1);
        linia3.add(fieldStock1);
        linia3.add(butonEdit);

        JPanel linia4=new JPanel();
        linia4.setBackground(Color.blue);
        linia4.setLayout(new FlowLayout());
        linia4.add(id2);
        linia4.add(fieldId2);
        linia4.add(butonDelete);

        JPanel linia5=new JPanel();
        linia5.setBackground(Color.blue);
        linia5.setLayout(new FlowLayout());
        linia5.add(butonView);

        JPanel linia6=new JPanel();
        linia6.setBackground(Color.blue);
        linia6.setLayout(new FlowLayout());
        linia6.add(butonInapoi);

        c.add(linia1);
        c.add(linia2);
        c.add(linia3);
        c.add(linia4);
        c.add(linia5);
        c.add(linia6);


        this.setContentPane(c);
        this.pack();
        this.setTitle("Pagina Client");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public String getNumeAdd(){
        return fieldNume.getText();
    }
    public String getStockAdd(){
        return fieldStock.getText();
    }

    public String getIdEdit(){
        return fieldId1.getText();
    }
    public String getNumeEdit(){
        return fieldNume1.getText();
    }
    public String getStockEdit(){
        return fieldStock1.getText();
    }

    public String getIdDelete(){
        return fieldId2.getText();
    }

    public void butonInapoiListener(ActionListener bInapoi){
        butonInapoi.addActionListener(bInapoi);
    }
    public void butonAddListener(ActionListener bAdd){
        butonAdd.addActionListener(bAdd);
    }
    public void butonDeleteListener(ActionListener bDelete){
        butonDelete.addActionListener(bDelete);
    }
    public void butonEditListener(ActionListener bEdit){
        butonEdit.addActionListener(bEdit);
    }
    public void butonViewListener(ActionListener bView){
        butonView.addActionListener(bView);
    }
}
