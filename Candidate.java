import java.sql.*;

import javax.swing.*;
public class Candidate extends User{
    private String description= "";
    Candidate( ){
        super();
    }
    Candidate(String NIC){
        super(NIC);
    }
    Candidate(String NIC, String password){
        super(NIC, password);
        
    }

    

    public static void uploadDetails(int id ,String details, String add,String post_held, String actual_workplace, String Office_no, String home_no, String mobile_no, String email, int own_car, int department ){
        //post_held, paysite_code, ministry_department, actual_workplace, office_no, home_no, mobile_no, email, own_car
        int row= 0;
        try {
            String sql ="INSERT INTO Candidates VALUES(?,?,?,?,?,?,?,?,?,?,?,0)";
            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);
            stmt.setInt(1,id);
            stmt.setString(2,details);
            stmt.setString(3,add);
            stmt.setString(4,post_held);
            stmt.setString(5,actual_workplace);
            stmt.setString(6,Office_no);
            stmt.setString(7,home_no);
            stmt.setString(8,mobile_no);
            stmt.setString(9,email);
            stmt.setInt(10,own_car);
            stmt.setInt(11,department);
            //"UPDATE Candidates SET Description = " + details + " WHERE Admit = 0 AND id = "+ this.id
            row = stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied, please try again later "+ e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (row == 0){
            JOptionPane.showMessageDialog(null, "Error: Cannot upload details. Please try later.", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getDetails(){
        return description;
    }

    @Override
    public boolean enter() {
        //check if user sign in as candidate
        
        try {
            PreparedStatement stmt = DBConnection.conn.prepareStatement("Select Voters.id, Admit, Flag FROM Candidates JOIN Voters on  Voters.id = Candidates.id WHERE NIC = ?");
            stmt.setString(1,this.NIC);

            ResultSet row = stmt.executeQuery();
            
            if (!row.next()){
                JOptionPane.showMessageDialog(null, "Error: You are not a candidate.", "Error",JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (!row.getBoolean(2)){
                JOptionPane.showMessageDialog(null, "Error: You are not a yet been admited by the admin", "Error",JOptionPane.ERROR_MESSAGE);
                return false;
            }

            stmt.close();
        } catch (Exception e) {
            //TODO: handle exception
            JOptionPane.showMessageDialog(null, "Error: Access Denied.", "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }

        
        return super.enter();
    }

    public Object[] getProfile(){
        Object[] result = null;
        try {
            String sql ="SELECT Descriptions,post_held,actual_workplace,office_no,home_no,mobile_no,email,own_car,department, Region_id, address FROM Candidates Join Voters on Voters.id = Candidates.id WHERE Candidates.id = "+ this.id;
            Statement stmt = DBConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) return null;
            Object[] res = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7),rs.getString(8),rs.getInt(9), rs.getInt(10), rs.getString(11)};
            result = res;
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied. "+e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return result;
    }

    public boolean updateProfile(String description, String add, String post_held, String workplace, String office, String home, String mobile, String email, int own_car, int department){

        try {
            String sql ="Update Candidates SET  Descriptions = ? ,post_held  =?,actual_workplace= ?,office_no= ?,home_no= ?,mobile_no= ?,email= ?,own_car= ?,department= ?, address = ? WHERE id = ?;";
            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);
            
            stmt.setString(1, description);
            stmt.setString(2, post_held);
            stmt.setString(3, workplace);
            stmt.setString(4, office);
            stmt.setString(5, home);
            stmt.setString(6, mobile);
            stmt.setString(7, email);
            stmt.setInt(8, own_car);
            stmt.setInt(9, department);
            stmt.setString(10, add);
            stmt.setInt(11, this.id);

            int rs = stmt.executeUpdate();
            if (rs == 0){
                JOptionPane.showMessageDialog(null, "Error: Please try again later.", "Cannot Update",JOptionPane.ERROR_MESSAGE);
                return false;
            }
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied. "+e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
