package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * View-ul care contine tabelul in care afisam toate elementele unei tabele, fie ea Client sau Product
 */
public class ViewTable extends JFrame {
    private JTable table=new JTable();
    private JButton butonInapoi=new JButton("Inapoi");
    public ViewTable(){
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setBounds(0,0,200,500);
        JPanel c=new JPanel();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.setPreferredSize(new Dimension(700,450));
        c.setBackground(Color.blue);

        butonInapoi.setPreferredSize(new Dimension(130,30));

        JPanel linia1=new JPanel();
        linia1.setBackground(Color.blue);
        linia1.setLayout(new FlowLayout());

        linia1.add(table);

        JPanel linia2=new JPanel();
        linia2.setBackground(Color.blue);
        linia2.setLayout(new FlowLayout());
        linia2.add(butonInapoi);

        c.add(linia1);
        c.add(linia2);


        this.setContentPane(c);
        this.pack();
        this.setTitle("Pagina Tabel");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setTable(String[] columns, String[][] data){
          for(int v=0;v<columns.length;v++) {
              System.out.println(columns[v]);
          }
        for(int i=0;i<data.length;i++){
            for(int j=0;j<columns.length;j++) {
                System.out.println(data[i][j]);
            }
        }

        DefaultTableModel tabel=new DefaultTableModel(data,columns);
        table.setModel(tabel);
        //table.setBounds(0,0,200,500);
        table.setPreferredSize(new Dimension(600,200));
        table.setRowHeight(25);
    }

    public void butonInapoiListener(ActionListener bInapoi){
        butonInapoi.addActionListener(bInapoi);
    }
}
