import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class Admin {
    private String admin_username, admin_password;
    private int admin_id ;
    private boolean login;
    Admin(String username, String password){
        admin_id = 0;
        admin_password = password;
        admin_username = username;
        login = false;
    }

    void log(){
        if (login) return;
        try {
            String sql = "SELECT id, Password From Admin WHERE Username = ?  ;";

            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);

            stmt.setString(1, admin_username);
            //stmt.setString(2, admin_password);
            
            ResultSet rs=stmt.executeQuery();

            

            if (rs.next()){
                if (!admin_password.equals(rs.getString(2))) return;
                login = true;
                admin_id = rs.getInt(1);
                
                //System.out.println("Login");
            }else {
                //System.out.println("Error in credentials");
            }
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied, Cannot log in" + e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getAdmin_username() {
        return admin_username;
    }
    public boolean isLogin(){
        return login;
    }
    public static String[][] getAllCandidate(){
        ArrayList<String[]> array = new ArrayList<String[]>();
        try {
            String sql = " SELECT Voters.id , CONCAT(Firstname,' ', Lastname) as Fullname, Region.region_name From (Voters Join Region on Voters.Region_id = Region.id)Join Candidates on Candidates.id = Voters.id Where Admit = 1 ";
            Statement stmt = DBConnection.conn.createStatement();
            ResultSet row = stmt.executeQuery(sql);
            while(row.next()){
                String[] data = { Integer.toString(row.getInt(1)),row.getString(2), row.getString(3)};
                array.add(data);
            }
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied" +e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String[][] result = new String[array.size()][3];

        for (int i = 0; i < array.size(); ++i){
            result[i] = array.get(i);
        }

        return result;
    }
    public static String[][] getAllRegisteredCandidate(){
        ArrayList<String[]> array = new ArrayList<String[]>();
        try {
            String sql = " SELECT Voters.id , CONCAT(Firstname,' ', Lastname) as Fullname, Ministry_Department.Name, Region.region_name From ((Voters Join Region on Voters.Region_id = Region.id)Join Candidates on Candidates.id = Voters.id) JOIN Ministry_Department on Ministry_Department.id = Candidates.department Where Admit = 0 ";
            Statement stmt = DBConnection.conn.createStatement();
            ResultSet row = stmt.executeQuery(sql);
            while(row.next()){
                String[] data = { Integer.toString(row.getInt(1)),row.getString(2), row.getString(3), row.getString(4)};
                array.add(data);
            }
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied" +e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String[][] result = new String[array.size()][4];

        for (int i = 0; i < array.size(); ++i){
            result[i] = array.get(i);
            //System.out.println(Arrays.toString(result[i]));
        }


        return result;
    }

    public static Object[] getCandidateInfo(int Candidate_id){
        Object[] result = null;
        try {
            String sql = "SELECT Voters.id, Firstname, Lastname, Ministry_Department.Name, gender, Candidates.address, post_held, actual_workplace,office_no, home_no, mobile_no, email, own_car, Descriptions FROM ((Voters Join Region on Voters.Region_id = Region.id)Join Candidates on Candidates.id = Voters.id) JOIN Ministry_Department on Ministry_Department.id = Candidates.department Where Voters.id = ? ";
            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);
            stmt.setInt(1, Candidate_id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next())
                return null;
            Object [] data = {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),rs.getInt(13),rs.getString(14)};
            result = data;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied" +e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return result;
    }
    public void CreateElection(){
        int row =0;
        try {
            String sql = "INSERT INTO Election(Admin_id) Values (" + this.admin_id+");";
            Statement stmt = DBConnection.conn.createStatement();
            row = stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (row >0 ){
            JOptionPane.showMessageDialog(null, "Election has been created.", "Created",JOptionPane.PLAIN_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(null, "Error: Cannot create Election", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    void registerCandidate(int candidate_id){
        int row =0 ;
        try {
            String sql = "UPDATE Candidates SET Admit = 1 WHERE id = " + candidate_id;
            Statement stmt = DBConnection.conn.createStatement();
            row = stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied "+e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (row >0 ){
            JOptionPane.showMessageDialog(null, "Candidate has been registered", "Registration",JOptionPane.PLAIN_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(null, "Error: Cannot register candidate", "Error",JOptionPane.ERROR_MESSAGE);
        }

    }

    /*void deleteVoter(int Voter_id){
        int row =0 ;
        try {
            String sql = "UPDATE Voters SET Flag = 1, password = NULL WHERE id =  " + Voter_id;
            Statement stmt = DBConnection.conn.createStatement();
            row = stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (row >0 ){
            JOptionPane.showMessageDialog(null, "Voter has been deleted", "Delete Voter",JOptionPane.PLAIN_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(null, "Error: Cannot de-register Voter", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }*/

    void deleteCandidate(int Candidate_id[]){
        int row =0 ;
        try {
            String sql = "DELETE FROM Candidates WHERE id IN (";
            for (int i = 0; i < Candidate_id.length; ++i){
                
                sql += "?";
                if (i != Candidate_id.length -1)
                    sql+=", ";
            }
            sql+=");";
            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);
            for (int i = 0; i < Candidate_id.length; ++i){
                stmt.setInt((i+1), Candidate_id[i]);
            }
            row = stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (row >0 ){
            JOptionPane.showMessageDialog(null, Candidate_id.length+" Candidate(s)  has been deleted", "Delete Candidate",JOptionPane.PLAIN_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(null, "Error: Cannot de-register Candidate", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    void AdmitCandidate(int[] candidate_id){
        int row =0;
        try {
            String sql = "UPDATE Candidates set Admit = 1 WHERE id in (" ;
            for (int i = 0; i < candidate_id.length; ++i){
    
                sql += "?";
                if (i != candidate_id.length -1)
                    sql+=",";
            }
            sql+=");";

            //  System.out.println(sql);
            //  System.out.println(Arrays.toString(candidate_id));
            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);

            for (int i = 0; i < candidate_id.length; ++i){
                stmt.setInt((i+1), candidate_id[i]);
                // System.out.println(candidate_id[i]);
            }
            row = stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied"+e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (row >0 ){
            JOptionPane.showMessageDialog(null, candidate_id.length +" Candidate(s) has been Admited", "Update Candidate",JOptionPane.PLAIN_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(null, "Cannot Regsiter Candidate", "Error",JOptionPane.ERROR_MESSAGE);
        }

    }

    void displayResult(int Election_id){

        //check if election is open
        int open = 0;
        try {
            String sql = "UPDATE Election SET Open = 0, RecentlyClosed = 1 WHERE id = ? AND Open = 1;";
            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);
            stmt.setInt(1, Election_id);
            int rs = stmt.executeUpdate();
            if (rs> 0){
                open = 1;
            }

            stmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Cannot produce result."+e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (open == 0) {
            JOptionPane.showMessageDialog(null, "Error: There is no result", "Error",JOptionPane.WARNING_MESSAGE);
            return;
        }

        Result.generate_report(Election_id);
        JOptionPane.showMessageDialog(null, "Result for this Election has been generated.", "Result",JOptionPane.PLAIN_MESSAGE);

    }
}
