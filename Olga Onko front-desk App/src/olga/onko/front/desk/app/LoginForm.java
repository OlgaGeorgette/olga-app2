/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olga.onko.front.desk.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Olga Onuko
 */
public class LoginForm extends JFrame implements ActionListener {

    JLabel l1, l2, l3;
    JTextField tf1;
    JButton Lbtn1;
    JButton clear = new JButton("Clear");
    JPasswordField p1;
    JFrame frame = new JFrame("Login");
    JTable scrTbl;

    DbUtils DbUtils = new DbUtils();
    private final static ObservableList<PieChart.Data> details = FXCollections.observableArrayList();
    private static PieChart pieChart;
    private Object DBConnect;

    LoginForm() {

        l1 = new JLabel("Enter username and password");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));

        l2 = new JLabel("Username");
        l3 = new JLabel("Password");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        Lbtn1 = new JButton("Login");

        l1.setBounds(200, 30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        tf1.setBounds(300, 70, 200, 30);
        p1.setBounds(300, 110, 200, 30);
        Lbtn1.setBounds(390, 160, 100, 30);
        Lbtn1.addActionListener(this);

        clear.setBounds(250, 160, 100, 30);
        clear.addActionListener(this);

        frame.add(l1);
        frame.add(l2);
        frame.add(tf1);
        frame.add(l3);
        frame.add(p1);
        frame.add(Lbtn1);
        frame.add(clear);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setBounds(100, 100, 700, 500);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String uname = tf1.getText();
        String pass = p1.getText();
        if (ae.getSource() == Lbtn1) {  // login button on click  action

            // connect to mysql db
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/olgaapp?" + "user=root&password=");

                PreparedStatement pst = conn.prepareStatement("Select * from user where username=? and pass=?");
                pst.setString(1, uname);
                pst.setString(2, pass.toString());
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    //if user is found show dashboard
                    Welcome wel = new Welcome();

                    frame.dispose();  // hide login form
                    JLabel label = new JLabel("Welcome:" + uname);
                    wel.getContentPane().add(label);
                    wel.setBounds(100, 100, 980, 500);
                   
                    //showpiechart                   
                    //PieC pie = new PieC("Pie");
                    wel.getContentPane().add(createPieChartPanel());
                    // add bar chart
                    wel.getContentPane().add(createBarChartPanel());
                    // show table 
                    JTable table = new JTable();
                    JScrollPane scrollPane = new JScrollPane();
                    scrollPane.setBounds(250, 160, 100, 30);
                    wel.getContentPane().add(scrollPane);
                    scrollPane.setViewportView(table);

                    String query = "select name,Gender,Telephone,dob,Age from user";
                    Statement st = conn.createStatement();
                    ResultSet set = st.executeQuery(query);
                    table.setModel(DbUtils.resultSetToTableModel(set));
                    wel.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect login or password",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
// clears text on the form
        if (ae.getSource() == clear) {
            tf1.setText("");
            p1.setText("");
        }
    }

    private static PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection cnn = DriverManager.getConnection("jdbc:mysql://localhost:3306/olgaapp?" + "user=root&password=");

            //sql for pulling pie chart data
            String SQL = "SELECT COUNT(id), "
                    + "Gender FROM user"
                    + " GROUP BY Gender";

            ResultSet rs = cnn.createStatement().executeQuery(SQL);

            while (rs.next()) {
                //adding data on piechart data
                dataset.setValue(rs.getString(2), rs.getDouble(1));
            }

        } catch (Exception e) {
            System.out.println("Error on DB connection");
            //return;
        }

        return dataset;

    }

    private static JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Gender Distribution", // chart title 
                dataset, // data    
                true, // include legend   
                true,
                false);

        return chart;
    }

    public static JPanel createPieChartPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }
// bar chart instance

    private static CategoryDataset createBarDataset() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection cnn = DriverManager.getConnection("jdbc:mysql://localhost:3306/olgaapp?" + "user=root&password=");

            //sql for pulling bar chart data using case condtion
            String SQL = "SELECT agegroup, count(*) AS total \n"
                    + "					FROM (SELECT\n"
                    + "						  CASE WHEN age BETWEEN 0 AND 9 THEN \'0-9\'\n"
                    + "						  WHEN Age BETWEEN 10 and 19 THEN \'10-19\'\n"
                    + "						  WHEN Age BETWEEN 20 and 29 THEN \'20-29\'\n"
                    + "						  WHEN Age BETWEEN 30 and 39 THEN \'30-39\'\n"
                    + "						  WHEN Age BETWEEN 40 and 49 THEN \'40-49\'\n"
                    + "						  WHEN Age BETWEEN 50 and 59 THEN \'50-59\'\n"
                    + "						  WHEN Age >= 60 THEN \'60 +\' END AS agegroup\n"
                    + "						  FROM user ) user\n"
                    + "					GROUP BY agegroup";

            ResultSet rs = cnn.createStatement().executeQuery(SQL);
           // loop thru the result from database
            while (rs.next()) {
                
                dataset.addValue(rs.getInt(2), rs.getString(1), rs.getString(1));
              
            }

        } catch (Exception e) {
            System.out.println("Error on DB connection" + e);
            //return;
        }

        return dataset;

    }

    public static JPanel createBarChartPanel() {
        JFreeChart barChart;
        barChart = ChartFactory.createBarChart(
                "Age distribution",
                "Age",
                "Persons",
                createBarDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        return chartPanel;
    }

}
