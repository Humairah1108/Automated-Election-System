import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

public class Result {

    public static void generate_report(int Election_id) {
        
        try {
            String sql ="SELECT Voters.id, COUNT(id) as No_Votes FROM Votes JOIN Voters on Votes.Candidate_id = Voters.id Where Election_id = "+Election_id+" GROUP BY id Having COUNT(id) >= 0;";

            Statement stmt = DBConnection.conn.createStatement();
            ResultSet Candidates = stmt.executeQuery(sql);

            String sql1 = "INSERT INTO Result Values";

            while(Candidates.next()){
                sql1 += "( "+Candidates.getInt(1)+", "+ Election_id + ", "+ Candidates.getInt(2)+", current_timestamp()),";
            }
            char[] chars = sql1.toCharArray();
            chars[sql1.length() -1] = ';';
    
            sql1 = String.valueOf(chars);
            System.out.println(sql1);
            stmt.execute(sql1);
            
            stmt.close();


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Cannot produce result." +e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
    }
    public static String[][] get_report(int Election_id, int district_id) {
        ArrayList<String[]> data = new ArrayList<String[]>();
        try {
            String sql ="";
            if (district_id != 0){
                sql = "Select Candidate_id,  CONCAT(Firstname, ' ', Lastname) As fullname, No_of_Votes  From  (Voters Join Candidates on Candidates.id = Voters.id) Join  Result on Result.Candidate_id = Candidates.id Where Election_id = " + Election_id+ " AND Voters.Region_id = "+ district_id + " ORDER BY  No_of_Votes DESC;";
            }else{
                sql = "Select Candidate_id , Firstname, Region.region_name, Ministry_Department.Name, No_of_Votes From (((Result JOIN Voters on Voters.id = Result.Candidate_id) JOIN Region on Region.id = Voters.Region_id)JOIN Candidates on Candidates.Id = Voters.id) JOIn Ministry_Department on Ministry_Department.id = Candidates.department WHERE Election_id = " + Election_id+ " ORDER BY No_of_Votes, Region.region_name DESC ";

            }
            
            Statement stmt = DBConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String[] row ;
                if (district_id != 0)
                    row = new String[]{ Integer.toString(rs.getInt(1)), rs.getString(2), Integer.toString(rs.getInt(3))};
                else 
                    row = new String[]{ Integer.toString(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4), Integer.toString(rs.getInt(5))};

                data.add(row);
            }
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied.", "Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        int col = (district_id == 0)? 5 : 3;
        String[][] result = new String[data.size()][col];
        for (int i = 0; i < data.size(); ++i){  
            result[i]= data.get(i);
           // System.out.println(Arrays.toString(result[i]));
        }
        return result;
    }
}
