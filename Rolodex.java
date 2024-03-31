import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.*;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.PrintWriter;
import java.lang.*;
import java.lang.reflect.Array;
import java.util.*;
import static java.lang.System.out;
public class Rolodex {
    static class Entry implements Comparable<Entry>{
        String s1,s2;
        String phone;
        String address;
        public Entry(String s1, String s2, String phone, String address){
            this.s1 = s1;
            this.s2 = s2;
            this.phone = phone;
            this.address = address;
        }
        public String toString(){
            return s2 + ", " + s1 + "\n";
        }
        public String allData(){
            return toString().substring(0,toString().length()-1) + ", " + phone + ", " + address;
        }
        @Override
        public int compareTo(Entry o){
            return this.toString().compareTo(o.toString());
        }
    }
    public static boolean check(String s){
        for(char c:s.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) throws IOException{
        File f = new File("testVaishnav.txt");
        ArrayList<Entry> here = new ArrayList<Entry>();
        Entry t; String s;
        if(f.exists()){
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()){
                s = sc.nextLine();
                String[] a = s.split(", ");
                String[] A = new String[4];
                for(int i = 0; i < a.length; ++i) A[i] = a[i];
                for(int i = a.length; i < 4; ++i) A[i] = "";
                a = A;
                t = new Entry(a[1],a[0],a[2],a[3]);
                here.add(t);
            }
        }
        Entry[] oldpeople = new Entry[here.size()];
        for(int i = 0; i < oldpeople.length; ++i){
            oldpeople[i] = here.get(i);
        }
        JFrame frame = new JFrame("Rolodex");
        JList<Entry> rolodexer = new JList<Entry>();
        JScrollPane scrollPane = new JScrollPane(rolodexer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        rolodexer.setListData(oldpeople);
        ArrayList<JTextField> field = new ArrayList<JTextField>();
        ArrayList<JLabel> Field = new ArrayList<JLabel>();
        Field.add(new JLabel("First Name:   "));
        Field.add(new JLabel("Last Name:    "));
        Field.add(new JLabel("Phone number: "));
        Field.add(new JLabel("Address:      "));
        field.add(new JTextField(""));
        field.add(new JTextField(""));
        field.add(new JTextField(""));
        field.add(new JTextField(""));
        Color color = frame.getBackground();
        for(int i = 0; i < 4; ++i){
            Field.get(i).setBounds(240,20 + 30*i, 120,20);
        }
        for(int i = 0; i < 4; ++i){
            field.get(i).setBounds(370,20 + 30*i, 120,20);
        }
        for(int i = 0; i < 4; ++i){
            field.get(i).setEditable(true);
            field.get(i).setBackground(Color.WHITE);
            field.get(i).setBorder(new LineBorder(Color.BLACK,1));
            field.get(i).setVisible(true);
            frame.add(field.get(i));
        }
        for(JLabel l:Field){
            frame.add(l);
        }
        ArrayList<Entry> people = new ArrayList<Entry>();
        for(Entry human:here){
            people.add(human);
        }
        scrollPane.setBounds(20,20,200,400);
        scrollPane.setBorder(new LineBorder(Color.BLACK,1));
        scrollPane.setVisible(true);
        JButton save = new JButton("Save");
        save.setBounds(260,135,100,30);
        JButton New = new JButton("New");
        New.setBounds(370,135,100,30);
        New.setVisible(true);
        JButton clear = new JButton("Delete Contact");
        clear.setBounds(240,260,250,50);
        JButton changes = new JButton("Save Changes");
        changes.setBounds(240,200,250,50);
        clear.setVisible(false);
        changes.setVisible(false);
        frame.add(New);
        save.setVisible(true);
        save.setVisible(true);
        frame.add(save);
        frame.setSize(515,500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.add(clear);
        frame.add(changes);
        frame.add(scrollPane);
        New.addActionListener(e->{
            for(int i = 0; i < 4; ++i){
                field.get(i).setText("");
            }
        });
        save.addActionListener(e->{
            if(!(field.get(0).getText().equals("") || field.get(1).getText().equals(""))){
                people.add(new Entry(field.get(0).getText(),field.get(1).getText(),field.get(2).getText(),field.get(3).getText()));
                Collections.sort(people);
                Entry[] Populus = new Entry[people.size()];
                for(int i = 0; i < Populus.length; ++i){
                    Populus[i] = people.get(i);
                }
                rolodexer.setListData(Populus);
                for(int i = 0; i < 4; ++i){
                    field.get(i).setText("");
                }
            }
            else{
                String[] options = { "OK" };
                JOptionPane.showOptionDialog(null, "Your contact must have a first and last name.", "Error Message",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
            }
        });
        changes.addActionListener(e->{
            Entry current = new Entry("","","","");
            current.s1 = field.get(0).getText();
            current.s2 = field.get(1).getText();
            current.phone = field.get(2).getText();
            current.address = field.get(3).getText();
            people.set(rolodexer.getSelectedIndex(),current);
            Collections.sort(people);
            Entry[] Populus = new Entry[people.size()];
            for(int i = 0; i < Populus.length; ++i) {
                Populus[i] = people.get(i);
            }
            rolodexer.setListData(Populus);
            save.setVisible(true);
            clear.setVisible(false);
            changes.setVisible(false);
            New.setVisible(true);
            rolodexer.clearSelection();
        });
        clear.addActionListener(e->{
            for(int i = 0; i < 4; ++i){
                field.get(i).setText("");
            }
            people.remove(rolodexer.getSelectedIndex());
            Entry[] Populus = new Entry[people.size()];
            for(int i = 0; i < Populus.length; ++i) {
                Populus[i] = people.get(i);
            }
            rolodexer.setListData(Populus);
            save.setVisible(true);
            New.setVisible(true);
            changes.setVisible(false);
            clear.setVisible(false);
        });
        rolodexer.addListSelectionListener(e->{
            Entry current = rolodexer.getSelectedValue();
            if(current != null){
                field.get(0).setText(current.s1);
                field.get(1).setText(current.s2);
                field.get(2).setText(current.phone);
                field.get(3).setText(current.address);
            }
            New.setVisible(false);
            save.setVisible(false);
            clear.setVisible(true);
            changes.setVisible(true);
        });
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                try
                {
                    f.createNewFile();
                    PrintWriter pw = new PrintWriter(f);
                    for(Entry human:people){
                        pw.println(human.allData());
                    }
                    pw.close();
                }
                catch(Exception exc)
                {
                    exc.printStackTrace();
                    // code to handle errors
                    // or shut down the program with System.exit(0);
                }
                finally{
                    System.exit(0);
                }
            }
        });
    }
}