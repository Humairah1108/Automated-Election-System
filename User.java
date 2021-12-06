import java.lang.Math;
import java.sql.*;
import java.util.ArrayList;


import javax.swing.JOptionPane;



public abstract class User {

    //attributes
    
    protected String NIC, password, name, region;
    protected int id, district;
    //protected String password;

    //constructures
    User(String NIC, String password){
        this.name = "";
        this.region = "";
        this.NIC = NIC;
        id = 0;
        this.password = password;
        this.district = 0;
    }
    User(){
        this("", User.RandomPassword());
    }
    User(String NIC){
        this(NIC, User.RandomPassword());
    }

    //-----Methods
    //getters
    public String getNIC() {
        return NIC;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return name;
    }
    public String getRegion() {
        return region;
    }
    public int getDistrict() {
        return district;
    }

    public void castVote(int Election_id, int[] candidate_id){
        Vote.submit_vote(Election_id, this.id, candidate_id);
    }
    public String[][] displayAllCandidatesInArea(){
        ArrayList<String[]> data = new ArrayList<String[]>();
        
        try {
            String sql = "SELECT Voters.id,  CONCAT(Firstname, ' ',Lastname)as Fullname, Ministry_Department.Name  FROM (Voters JOIN Candidates on Candidates.id = Voters.id) JOIN Ministry_Department on Ministry_Department.id = Candidates.department Where Region_id = ? AND Admit = 1; "; 
            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);
            stmt.setInt(1, this.district);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String[] row = { rs.getString(1), rs.getString(2), rs.getString(3)};
                data.add(row);
            }
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied, cannot get Candidates in area", "Error",JOptionPane.ERROR_MESSAGE);
            return  null;
        }
        String[][] result = new String[data.size()][3];
        for (int i = 0; i < data.size(); ++i){  
            result[i]= data.get(i);
           // System.out.println(Arrays.toString(result[i]));
        }
        return result;
    }

    public static String[] ViewCandidate(int candidate_id){
        String[] result = new String[5] ;
        try {
            String sql = "SELECT Voters.id,  CONCAT(Firstname, ' ',Lastname)as Fullname, actual_workplace, Descriptions, Ministry_Department.Name FROM (Voters JOIN Candidates on Voters.id = Candidates.id) JOIN Ministry_Department on Ministry_Department.id = Candidates.department Where Candidates.id =? ;";
            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);
            stmt.setInt(1, candidate_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                result[0] = rs.getString(1);
                result[1] =  rs.getString(2);
                result[2] =  rs.getString(3);
                result[3] = rs.getString(4);
                result[4] =  rs.getString(5);
            }
            
            
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied, cannot view candidate"+ e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return  null;
        }

        

        return result;
    }

    //private method
    public static String RandomPassword(){
        // this is to produce a random Pasword of length 8
        final String chars ="abcdefghijklmnopqrstuvwxyz#&@ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String pass ="";
        for (int i =0; i < 8; ++i){
            int j = (int)(Math.random()*chars.length());
            pass+= chars.charAt(j);
        }
        return pass;
    }

    public static boolean Register(String NIC, String password, int RegionId) {
        
        //need to check if user exists

        try {
            String sql = "SELECT id FROM Voters Where NIC = ? AND password IS NOT NULL;";

            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);
            stmt.setString(1, NIC);
            ResultSet rs = stmt.executeQuery();
            

            
            if (rs.next()){
                // cannot register
                JOptionPane.showMessageDialog(null, "Error: cannot register twice.", "Error",JOptionPane.WARNING_MESSAGE);
                return false;
            }
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied." +e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
       
        int row = 0;
       
        try {
            // Set auto commit as false.
            DBConnection.conn.setAutoCommit(false);

            //insert user
            String sql = "UPDATE Voters Set password = ?, Region_id = ? Where NIC = ?;";

            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);
            stmt.setString(3, NIC);
            stmt.setInt(2, RegionId);
            stmt.setString(1, password);
            row = stmt.executeUpdate();
            DBConnection.conn.commit();
            
            // Set auto commit as false.
            DBConnection.conn.setAutoCommit(true);
            stmt.close();

        } catch (Exception e) {
            try {
                DBConnection.conn.rollback();
                DBConnection.conn.setAutoCommit(true);
            } catch (Exception ex) {
                //TODO: handle exception
            }
            
            JOptionPane.showMessageDialog(null, "Error: Access Denied. " +e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (row == 0){
            JOptionPane.showMessageDialog(null, "Error: Cannot Register, please check your NIC.", "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }

        JOptionPane.showMessageDialog(null, "You have now been registered, login in with your NIC "+NIC+" and password: "+ password, "Success",JOptionPane.PLAIN_MESSAGE);
        
        return true;
    }


    private static int validate(User user, String NIC, String password){
        //validate user if sign in
        int id =0;
        String pass= "";
        try {
            String sql = "SELECT Voters.id, Region_id, CONCAT(Firstname, ' ',Lastname)as Fullname, region_name, Password  FROM Voters JOIN Region on Region.id = Voters.Region_id Where NIC = ?  AND Flag = 0;";
            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);
            stmt.setString(1, NIC);
            //stmt.setString(1, password);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()){
                pass = rs.getString(5);
                if (!pass.equals(password)){
                    JOptionPane.showMessageDialog(null, "Error, NIC or password is invalid","Login error", JOptionPane.WARNING_MESSAGE);
                    return id;
                }
                id = rs.getInt(1);
                user.district = rs.getInt(2);
                user.name = rs.getString(3);
                user.region = rs.getString(4);
                //System.out.println("Login");
            }else {
                //System.out.println("Error");
                JOptionPane.showMessageDialog(null, "Error, NIC or password is invalid","Login error", JOptionPane.WARNING_MESSAGE);
            }
            stmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied." +e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }

        //if id -1 then either user not exist or error in nic or password
        return id;
    }
    
    public boolean enter(){
        int rs = User.validate(this, this.NIC, this.password);
        this.id = rs;
        return (this.id != 0);

        //System.out.println("Region : "+district+ " id : "+ id);
        
    }

    
}

