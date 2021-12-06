import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Vote {
    public static void submit_vote(int Election_id,int voter_id, int[] candidate_id){
        
        boolean open = true, found = false;
        //check if election is open
        try{
            String sql = "SELECT Open From Election Where id = ? ;";
            PreparedStatement stmt = DBConnection.conn.prepareStatement(sql);
            stmt.setInt(1, Election_id);
            ResultSet rs = stmt.executeQuery();
            found = rs.next();
            if (found){
                open = rs.getBoolean(1);
            }
            stmt.close();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied, cannot vote " +e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!found){// check if election id found
            JOptionPane.showMessageDialog(null, "Error: Election id not found", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        // check if open is close
        if (!open){
            JOptionPane.showMessageDialog(null, "Error: Election is closed.", "Error",JOptionPane.WARNING_MESSAGE);
            return;
        }

        Voting_server.Saves(Election_id, voter_id, candidate_id);

    }
}
