import java.sql.*;
import javax.swing.*;
public class Voting_server {
    public static void Saves(int Election_id,int voter_id, int[] candidate_id){
        int row =0 ;
        try {
            DBConnection.conn.setAutoCommit(false);
            String sql = "INSERT INTO Votes(Voter_id,Candidate_id, Election_Id) VALUES ";
            for (int i = 0; i < candidate_id.length ; ++i){
                sql+= "(?, ? ,?)";
                if (i != candidate_id.length -1)
                    sql +=",";
                else 
                    sql +=";";
            }

            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);
            int index  =1;
            for (int i = 0; i < candidate_id.length ; ++i){
                stmt.setInt(index, voter_id);
                stmt.setInt(index+1, candidate_id[i]);
                stmt.setInt(index+2, Election_id);
                index +=3;
            }
           
            //System.out.println(stmt);
            row = stmt.executeUpdate();
            

            DBConnection.conn.commit();
            DBConnection.conn.setAutoCommit(true);
            stmt.close();
           

        } catch (Exception e) {
            try {
                DBConnection.conn.rollback();
                DBConnection.conn.setAutoCommit(true);
            } catch (Exception ex) {
                //TODO: handle exception
            }
            JOptionPane.showMessageDialog(null, "Error: Access Denied, cannot vote "+ e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (row == 0){
            JOptionPane.showMessageDialog(null, "Error: Votes was not cast please try again later", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null, "Vote has been casted", "Cast Vost",JOptionPane.PLAIN_MESSAGE);

    }
}
