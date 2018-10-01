/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olga.onko.front.desk.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class AddUser extends JFrame implements ActionListener {

    JFrame frame = new JFrame("Add user");
    JButton save = new JButton("save");
    Panel p = new Panel();
    JTextField f = new JTextField(20);
    JTextField f1 = new JTextField(20);
    JLabel l = new JLabel("Enter the name");
    JLabel l1 = new JLabel("Enter the Last name");
    String s1, s2;
    String ppl;
    int people;
    JTextField tf1 = new JTextField(20);
    ;
    JLabel l1s, l2, l3, l22, l33, age, dob, gender;
    JTextField tf1s, name, tel, ages, dobs;
    JButton Lbtn1;
    JButton clear = new JButton("Clear");
    JPasswordField p1;
  String genders="M";
    AddUser() {
        l1 = new JLabel("Add User");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));

        l2 = new JLabel("Username");
        l3 = new JLabel("Password");
        l22 = new JLabel("Name");
        l33 = new JLabel("Telephone");
        age = new JLabel("Age");
        dob = new JLabel("Date of birth");
         gender = new JLabel("Gender");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        name = new JTextField();
        tel = new JTextField();
        ages = new JTextField();
        dobs = new JTextField();
        Lbtn1 = new JButton("Save");

        JRadioButton Male = new JRadioButton("Male", true);

        JRadioButton female = new JRadioButton("Female");

        l1.setBounds(200, 30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        l22.setBounds(80, 160, 200, 30);
        l.setBounds(80, 70, 200, 30);
        l33.setBounds(80, 210, 200, 30);
        age.setBounds(80, 260, 200, 30);
        dob.setBounds(80, 310, 200, 30);
        gender.setBounds(80, 360, 200, 30);
        tf1.setBounds(300, 70, 200, 30);
        name.setBounds(300, 150, 200, 30);
        tel.setBounds(300, 200, 200, 30);
        ages.setBounds(300, 260, 200, 30);
        dobs.setBounds(300, 310, 200, 30);
        Male.setBounds(300, 350, 200, 30);
        female.setBounds(300, 350, 200, 30);
        p1.setBounds(300, 110, 200, 30);
        Lbtn1.setBounds(390, 410, 100, 30);
        Lbtn1.addActionListener(this);

        clear.setBounds(250, 410, 100, 30);
        clear.addActionListener(this);

        frame.add(l1);
        frame.add(l2);
        frame.add(tf1);
        frame.add(l3);
        frame.add(p1);
        frame.add(l22);
        frame.add(name);
        frame.add(l33);
        frame.add(tel);
        frame.add(age);
          frame.add(ages);
        frame.add(dob);
        frame.add(dobs);
        //Group the radio buttons.

        ButtonGroup group = new ButtonGroup();

        group.add(Male);

        group.add(female);
        frame.add(gender);
        frame.add(Male);

        frame.add(female);
        frame.add(Lbtn1);
        frame.add(clear);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setBounds(100, 100, 700, 500);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Lbtn1) {
            try {
                // connection string
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/olgaapp?" + "user=root&password=");

                Statement st = con.createStatement();

                st.executeUpdate("insert into user VALUES(null,'"
                     + tf1.getText() + "','" + p1.getText() + "','" + name.getText() + "','"+ genders + "','"+ tel.getText() +"','"
                        + dobs.getText() + "','"
                        + ages.getText() + "')");

                JOptionPane.showConfirmDialog(null, "Your Data Has been Inserted",
                        "Result", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                st.close();
                con.close();
               Welcome wels =new Welcome();
               wels.setVisible(true);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException | HeadlessException e1) {
                System.out.println("Exception:" + e1);
            }

        }
    }

}
