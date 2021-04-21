package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * View-ul principal in care aleg tabela pe care vreau sa fac operatii
 * Alegerea se face prin intermediul a 3 butoane
 */
public class View extends JFrame{
    private JLabel alegere=new JLabel("Alegeti pe cine sa faceti operatia");
    private JButton butonClient= new JButton("Client");
    private JButton butonProduct= new JButton("Product");
    private JButton butonOrder= new JButton("Order");

    public View(){
        JPanel c=new JPanel();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.setPreferredSize(new Dimension(400,250));
        c.setBackground(Color.YELLOW);

        alegere.setFont(new Font( "Times New Roman",Font.BOLD,20));

        butonClient.setPreferredSize(new Dimension(100,30));
        butonProduct.setPreferredSize(new Dimension(100,30));
        butonOrder.setPreferredSize(new Dimension(100,30));

        JPanel linia1=new JPanel();
        linia1.setBackground(Color.YELLOW);
        linia1.setLayout(new FlowLayout());

        linia1.add(alegere);

        JPanel linia2=new JPanel();
        linia2.setBackground(Color.YELLOW);
        linia2.setLayout(new FlowLayout());
        linia2.add(butonClient);
        linia2.add(butonProduct);
        linia2.add(butonOrder);

        c.add(linia1);
        c.add(linia2);


        this.setContentPane(c);
        this.pack();
        this.setTitle("Pagina Principala");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //Action Listeneri pentru toate butoanele
    public void butonClientListener(ActionListener bClient){
        butonClient.addActionListener(bClient);
    }
    public void butonProductListener(ActionListener bProduct){
        butonProduct.addActionListener(bProduct);
    }
    public void butonOrderListener(ActionListener bOrder){
        butonOrder.addActionListener(bOrder);
    }
}
