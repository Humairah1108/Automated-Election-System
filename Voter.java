import java.sql.*;

import javax.swing.*;
public class Voter extends User{
    
    Voter(String NIC){
        super(NIC);
    }
    Voter(String NIC, String password){
        super(NIC, password);
    }
    Voter(){
        super();
    }
    @Override
    public boolean enter() {
        
        
        
        try {
            PreparedStatement stmt = DBConnection.conn.prepareStatement("Select Voters.id FROM Candidates JOIN Voters on  Voters.id = Candidates.id WHERE NIC = ?;");
            stmt.setString(1,this.NIC);

            ResultSet row = stmt.executeQuery();
            
            if (row.next()){
                JOptionPane.showMessageDialog(null, "Error: You are a candidate, must login in the candidate panel.", "Error",JOptionPane.ERROR_MESSAGE);
                return false;
            }
            stmt.close();
        } catch (Exception e) {
            //TODO: handle exception
            JOptionPane.showMessageDialog(null, "Error: Access Denied."+ e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }

        
        return super.enter();
    }
    
    
    
}