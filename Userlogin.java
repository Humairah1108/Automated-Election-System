import java.sql.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
public class Userlogin extends JFrame implements ActionListener {
    private JButton login;
    private Placeholder id , password;
    Userlogin(){

        
        super("Voting System");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DBConnection.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Election_system","root", "");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        
        JLabel Voter_text = new JLabel("Cabdidate login");
		Voter_text.setFont(new Font("times new roman", Font.BOLD,30));
		//setLayout(null);
		Voter_text.setBounds(450,10,500,50);
		add(Voter_text);
        JPanel panel = new JPanel();

        panel.setBounds(0,50,this.getWidth()-50, this.getHeight());
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());
        JLabel id_text = new JLabel("NIC: ",16), pass_text = new JLabel("Password", 15);
         id = new Placeholder("Enter your NIC", 15); password = new Placeholder("Enter Your password", 15);
        
         login = new JButton("Login");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
         
        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        panel.add(id_text.getObject(), constraints);
 
        constraints.gridx = 1;
        panel.add(id.getObject(), constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        panel.add(pass_text.getObject(), constraints);
         
        constraints.gridx = 1;
        panel.add(password.getObject(), constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(login, constraints);

        login.addActionListener(this);
        this.add(panel);
        this.setSize(1150,750);
		setResizable(false);
		setVisible(true);
    }

    public static void main(String[] args) {
        new Userlogin();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login){
            String NIC = id.getObject().getText();
            String pass = password.getObject().getText();

            Candidate voter = new Candidate(NIC, pass);
            
            voter.enter();
        }
        
    }

}
