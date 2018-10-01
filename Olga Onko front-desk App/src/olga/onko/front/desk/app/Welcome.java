/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olga.onko.front.desk.app;

/**
 *
 * @author Olga Onuko
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Welcome extends JFrame implements ActionListener {
 JButton logout = new JButton("Logout");
 JButton AddUsers = new JButton("AddUser");
    Welcome() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DashBoard");
        setSize(400, 200);
        setLayout(new GridLayout(2, 3, 10, 10)); // set window layout
       
       // logout.setSize(3, 3);
        //logout.setBounds(100, 100, 700, 500);
        logout.setPreferredSize(new Dimension(40, 40));
        logout.addActionListener(this);
        this.getContentPane().add(logout);

        
        AddUsers.setPreferredSize(new Dimension(40, 40));
                    //logout.setBounds(100, 100, 700, 500);
        //AddUsers.setBounds(250, 160, 100, 30);
        AddUsers.addActionListener(this);
        this.getContentPane().add(AddUsers);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource()==logout){
       this.dispose();
       LoginForm loginform =new LoginForm();
       loginform.setVisible(true);
       }
       // start adduser class on butoon user add press
       if(e.getSource()==AddUsers){
         this.dispose();
         AddUser adduser = new AddUser();
         
         adduser.setVisible(true);
       }
    }
}
