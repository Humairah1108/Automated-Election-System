
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.Border;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;

public class User_gui extends JFrame implements ActionListener{
    private JButton login, candidate_button, voter_button, voterLogin, voterRegister, backToVoter_panel, candidateLogin_btn, candidateRegister_btn;
    private JButton candidate_to_Home_btn, voter_to_Home_btn;
    private JPanel Voter_panel, Candidate_panel, Home_panel, Voter_loginPanel, voter_registerPanel, candidate_registerPanel, candidate_loginPanel;
    
    private Placeholder Nic, password;
    //voter Register
    private JTextField jt_nic, jt_password, jt_Cpassword, jt_fname, jt_lname;
    private JButton jb_register, jb_back;
	private JComboBox jbox_region;


    //voter Homepage
    JLabel Voter_name, Voter_region;

    //candidate register 
    private JTextField nictxt, addrtxt, maidennametxt;
	private JTextField postHeldtxt, paysiteCodetxt, actualworktxt, officetxt, hometxt, mobiletxt, emailtxt, pass_TextField, cpass_TextField;
	private ButtonGroup buttonGroup,type;
	private JComboBox box, mindeptxt;
    private JRadioButton yes, no;
    private JCheckBox agree_chBox;
    private JButton submitForm_btn, back_btn;
    private JTextArea candidate_Description;

    //candidate login
    private Placeholder Nic_field, passowrd_field;
    private JButton bck_btn, login_btn;
    private JLabel candidate_region, candidate_name;

    private String[] region = {"--- Choose Region ---", "Savanne", "Pamplemousse", "Port-Louis"};
    private String[] department = {"--- Choose Department ---", "MMM", "MSM"};

    //voter profile panel 
    private JPanel voter_main_Panel;
    private JButton voter_logoutButton, voter_votingButton, voter_viewCandidateButton, voter_viewResultButton;

    //Candidate profile panel 
    private JPanel candidate_main_Panel;
    private JButton candidate_logoutButton, candidate_votingButton, candidate_viewCandidateButton, candidate_viewResultButton, viewDescription;
         
    //cast Vote Panel
    private JPanel castVotePanel, previousPanel;
    private JButton castVoteButton, castBackButton;
    private JTable votingTable;
    private DefaultTableModel model;
    static final int Max_votes = 2, Min_votes = 1;


    //result 
    private JPanel resultPanel;
    private JButton result_back;
    private JTable resultTable;
    private DefaultTableModel resultModel;
    final String[] column_result = {"Candidate Id", "Candidate Name", "Number of votes"};


    //view candidates in area
    private JPanel viewCandidatesInAreaPanel;
    private JButton viewCandidatesArea_backbtn;
    private JTable viewCandidatesAreaTable;
    private DefaultTableModel model_viewCandidate;
    private final String[] Column_viewcandidates  = {"id","Name", "Department"};
    private JLabel viewId_label, viewName_label, viewDepartment_label , viewWorkplace_label , viewDetails_label;

    //candidate profile
    private JPanel profile_main_panel;
	private JTextField nictxt_profile, addrtxt_profile;
	private JTextField postHeldtxt_profile, paysiteCodetxt_profile, actualworktxt_profile, officetxt_profile, hometxt_profile, mobiletxt_profile, emailtxt_profile;
    private JComboBox mindeptxt_profile, regtxt_profile;
    private JButton back_viewProfile, edit_profile, save_profile;
    private JTextArea profile_descrption;
    private JRadioButton profile_yes, profile_no;
    //voter 
    private Voter voter_person;

    //candidate 
    private Candidate candidate_person;


    //election_id
    private int election_id;
    
    User_gui(){
        this.setTitle("Election system");
        voter_person = null;
        candidate_person = null;
        election_id =0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DBConnection.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Election_system","root", "");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.setSize(1150,750);
        
        this.setLocationRelativeTo(null);
        //set Home Panel

        Home_panel = new JPanel(null);
        Home_panel.setBounds(0,0,this.getWidth(), this.getHeight());

        JLabel PanelFont = new JLabel("Home Panel");
		PanelFont.setFont(new Font("times new roman", Font.BOLD,30));
        PanelFont.setBounds(500,10,500,50);

        candidate_button = new JButton("Candidate Panel");
        voter_button = new JButton("Voter Panel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,1,10,150));
        buttonPanel.setBounds(431,200,300,300);
        buttonPanel.setBorder(BorderFactory.createTitledBorder( null, "Choose an option", TitledBorder.CENTER, TitledBorder.TOP, new Font("calibri",Font.BOLD,20), Color.BLACK));
        

        buttonPanel.add(candidate_button);
        buttonPanel.add(voter_button);
       
        Home_panel.add(PanelFont);
        Home_panel.add(buttonPanel);
        
        
        add(Home_panel);

        setResultPanel();
        setVoterHomePanel();
        setVoterLoginPanel();
        setVoterRegisterPanel();
        setVoterPanel();

        setCandidateHomePanel();
        setCandidateLogin();
        setCandidateRegister();
        setCandidatePanel();

        setCandidateProfile();
        setCastVotePanel();
        setViewCandidatesInAreaPanel();

        this.setVisible(true);

        //add action listener
        login.addActionListener(this);
        candidate_button.addActionListener(this);
        voter_button.addActionListener(this);
        voterLogin.addActionListener(this);
        voterRegister.addActionListener(this);
        backToVoter_panel.addActionListener(this);
        jb_back.addActionListener(this);
		jb_register.addActionListener(this);
		candidateRegister_btn.addActionListener(this);
		candidateLogin_btn.addActionListener(this);
        candidate_to_Home_btn.addActionListener(this);
        voter_to_Home_btn.addActionListener(this);
    }
    public static void main(String[] args) {
        new User_gui();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == login){// Voter login
            String nic_str = Nic.getObject().getText().trim();
            String pass_str = password.getObject().getText().trim();

            if (nic_str.equals("")|| pass_str.equals("")){
                JOptionPane.showMessageDialog(this, "Fields cannot be empty","Fields error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            voter_person = new Voter(nic_str, pass_str);

            if (!voter_person.enter()){
                return;
            }
            password.getObject().setText("");//clear password field

            //set name and region of user
            Voter_name.setText("Your name:- "+ voter_person.getName());
            Voter_region.setText("Your region:- " + voter_person.getRegion());

            //show panel
            Voter_loginPanel.hide();
            voter_main_Panel.show();
        }else if (e.getSource() == jb_register){ // voter register
            String nic_str= jt_nic.getText().trim();
            String region_str = (String)jbox_region.getSelectedItem();
            String pass_str = jt_password.getText().trim(), repeat_pass_str = jt_Cpassword.getText().trim();
            int region_index = jbox_region.getSelectedIndex();

            //System.out.println("Nic :" + nic_str + " region: "+ region_str+ " index:"+region_index);
            if (region_index == 0){
                JOptionPane.showMessageDialog(this, "Please choose your region","Region error", JOptionPane.WARNING_MESSAGE);
                return;
            }else if (nic_str.equals("")){
                JOptionPane.showMessageDialog(this, "NIC field should not be empty","NIC error", JOptionPane.WARNING_MESSAGE);
                return;
            }else if(!pass_str.equals(repeat_pass_str)){
                JOptionPane.showMessageDialog(this, "Both Password field should be the same","Password error", JOptionPane.WARNING_MESSAGE);
                return;
            }else if (pass_str.length() != 0 && (pass_str.length() <8|| pass_str.length() >20)){
                JOptionPane.showMessageDialog(this, "Password Length be between 8 - 20","Password error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (pass_str.length() == 0){
                pass_str = Voter.RandomPassword();
            }
            
            if (Voter.Register(nic_str, pass_str,region_index)){//register voter
                //reset Fields
                jt_nic.setText("");
                jbox_region.setSelectedIndex(0);
                jt_password.setText("");
                jt_Cpassword.setText("");

                //go Back
                jb_back.doClick();
                
                // Voter_panel.show();
                // voter_registerPanel.hide(); 
            }
        }
        else if (e.getSource() == voter_logoutButton){// voter logs out
            voter_person = null;
            Voter_loginPanel.show();
            voter_main_Panel.hide();
        }else if (e.getSource() == voter_button){
            Home_panel.hide();
            Voter_panel.show();
        }else if (e.getSource() == voterLogin){
            Voter_panel.hide();
            Voter_loginPanel.show();
        }
        else if (e.getSource() == voterRegister){
            Voter_panel.hide();
            voter_registerPanel.show();
        }
        else if (e.getSource() == backToVoter_panel){
            Voter_panel.show();
            Voter_loginPanel.hide();
        }
        else if (e.getSource() == jb_back){
            Voter_panel.show();
            voter_registerPanel.hide();    
        }else if (e.getSource() == candidate_button){
            Home_panel.hide();
            Candidate_panel.show();
        }else if (e.getSource() == candidate_to_Home_btn){
            Home_panel.show();
            Candidate_panel.hide();
        }else if (e.getSource() == voter_to_Home_btn){
            Home_panel.show();
            Voter_panel.hide();
        }else if (e.getSource() == back_btn){// candidate exits register panel
            Candidate_panel.show();
            candidate_registerPanel.hide();
        }else if (e.getSource() == candidateRegister_btn){
            Candidate_panel.hide();
            candidate_registerPanel.show();
        }
        else if (e.getSource() == candidateLogin_btn){// candidate enter login panel
            Candidate_panel.hide();
            candidate_loginPanel.show();
        }
        else if (e.getSource() == bck_btn){// candidate exits login panel
            Candidate_panel.show();
            candidate_loginPanel.hide();
        }
        else if (e.getSource() == login_btn){// candidate clicks login
            String nic_str = Nic_field.getObject().getText().trim(), pass_str = passowrd_field.getObject().getText().trim();

            
            if (nic_str.equals("") || pass_str.equals("")){
                JOptionPane.showMessageDialog(this, "Fields cannot be empty","Fields error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //log in candidates
            candidate_person = new Candidate(nic_str, pass_str);

            if (!candidate_person.enter()){
                return;
            }

            candidate_name.setText("Your name:- "+ candidate_person.getName());
            candidate_region.setText("Your region:- "+ candidate_person.getRegion());

            passowrd_field.getObject().setText("");// reset password fields

            candidate_loginPanel.hide();
            candidate_main_Panel.show();
        }
        else if (e.getSource() == candidate_logoutButton){// candidate log out
            candidate_person = null;
            candidate_loginPanel.show();
            candidate_main_Panel.hide();
        }
        else if (e.getSource() == candidate_votingButton){// candidate enters voting panel
            if (!checkElection()) return;

            //check if voter has already voted;
            if (!checkVote(candidate_person.getID())) return;

            //get candidate
            String [][] data = candidate_person.displayAllCandidatesInArea();

            if (data.length == 0){ 
                JOptionPane.showMessageDialog(this, "There is no candidates in your area", "Error",JOptionPane.WARNING_MESSAGE);
                return;
            }
            model.setRowCount(0);
            model = (DefaultTableModel)votingTable.getModel();

            for (int i = 0; i <data.length; ++i){
                Object[] obj = {data[i][0], data[i][1], data[i][2], false};
                model.addRow(obj);
            }
            
            previousPanel = candidate_main_Panel;
            castVotePanel.show();
            candidate_main_Panel.hide();
        }
        else if (e.getSource() == voter_votingButton){ // voter enters voting panel
            if (!checkElection()) return;

            //check if voter has already voted;
            if (!checkVote(voter_person.getID())) return;

            String [][] data = voter_person.displayAllCandidatesInArea();
            if (data.length == 0){  
                JOptionPane.showMessageDialog(this, "There is no candidates in your area", "Error",JOptionPane.WARNING_MESSAGE);
                return;
            }
            model.setRowCount(0);
            model = (DefaultTableModel)votingTable.getModel();

            for (int i = 0; i <data.length; ++i){
                Object[] obj = {data[i][0], data[i][1], data[i][2], false};
                model.addRow(obj);
            }
            previousPanel = voter_main_Panel;
            castVotePanel.show();
            voter_main_Panel.hide();
        }
        else if (e.getSource() == castBackButton){ //user exits voting panel
            castVotePanel.hide();
            previousPanel_show();
        }else if(e.getSource() == result_back){// user exits result panel
            resultPanel.hide();
            previousPanel_show();
        }
        else if(e.getSource() == voter_viewResultButton){//** voter view result
            if (voter_person == null) return;
                previousPanel = voter_main_Panel;

            if (!getRecentlyClosedElection()) return;

            String[][] data = Result.get_report(election_id, voter_person.getDistrict());

            if (data.length ==0)
            {
                JOptionPane.showMessageDialog(this, "Result has not yet benn publised.", "Result Error",JOptionPane.WARNING_MESSAGE);
                return;
            }
            resultTable = new JTable(data, column_result);

            voter_main_Panel.hide();
            resultPanel.show();
        }
        else if (e.getSource() == candidate_viewResultButton){//** candidate view result
            if (candidate_person == null) return;

            previousPanel = candidate_main_Panel;
            if (!getRecentlyClosedElection()) return;

            String[][] data = Result.get_report(election_id, candidate_person.getDistrict());

            if (data.length ==0)
            {
                JOptionPane.showMessageDialog(this, "Result has not yet benn publised.", "Result Error",JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            resultModel =  (DefaultTableModel)resultTable.getModel();

            int rowCount = resultModel.getRowCount();
            for (int  i = 0; i < data.length; ++i)
                resultModel.addRow(data[i]);

            //remove selection
            resultTable.setRowSelectionInterval(rowCount, rowCount);
            for (int i = rowCount -1; i >= 0; i--)
                resultModel.removeRow(0);

            candidate_main_Panel.hide();
            resultPanel.show();
        }
        else if(e.getSource() == voter_viewCandidateButton){// voters view candidate in area

            if (voter_person == null)  return;
            String [][] data = voter_person.displayAllCandidatesInArea();

            if (data.length == 0){  
                JOptionPane.showMessageDialog(null, "There is no candidates in your area", "Error",JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            model_viewCandidate =  (DefaultTableModel)viewCandidatesAreaTable.getModel();
            //viewCandidatesAreaTable = new JTable(data, Column_viewcandidates);
            int rowCount = model_viewCandidate.getRowCount();

            for (int  i = 0; i < data.length; ++i)
                model_viewCandidate.addRow(data[i]);

            //remove selection
            viewCandidatesAreaTable.setRowSelectionInterval(rowCount, rowCount);
            for (int i = rowCount -1; i >= 0; i--)
                model_viewCandidate.removeRow(0);

            previousPanel = voter_main_Panel;
            voter_main_Panel.hide();
            viewCandidatesInAreaPanel.show();
        }else if(e.getSource() == candidate_viewCandidateButton){// candidates view candidates in area
            if (candidate_person == null)  return;
            String [][] data = candidate_person.displayAllCandidatesInArea();

            if (data.length == 0){  
                JOptionPane.showMessageDialog(this, "There is no candidates in your area", "Error",JOptionPane.WARNING_MESSAGE);
                return;
            }

            
            model_viewCandidate =  (DefaultTableModel)viewCandidatesAreaTable.getModel();

            int rowCount = model_viewCandidate.getRowCount();
            for (int  i = 0; i < data.length; ++i)
                model_viewCandidate.addRow(data[i]);

            //remove selection
            viewCandidatesAreaTable.setRowSelectionInterval(rowCount, rowCount);
            for (int i = rowCount -1; i >= 0; i--)
            model_viewCandidate.removeRow(0);

            previousPanel = candidate_main_Panel;
            candidate_main_Panel.hide();
            viewCandidatesInAreaPanel.show();
        }else if (e.getSource() == viewCandidatesArea_backbtn){// user exits view candidates in area
            resetViewCandidateFields();
           
            viewCandidatesInAreaPanel.hide();
            previousPanel_show();
        }
        else if(e.getSource() == castVoteButton){// cast voting

            if (voter_person == null && candidate_person == null) return;

            
            User person = (voter_person != null)? voter_person : candidate_person;

            ArrayList<Integer> id = new ArrayList<Integer>();
            for (int i = 0; i< votingTable.getRowCount(); ++i){
                Boolean check = Boolean.valueOf(votingTable.getValueAt(i, 3).toString());
                if (check){
                    id.add(Integer.parseInt(votingTable.getValueAt(i, 0).toString()));
                }
            }

            if (id.size()> Max_votes){
                JOptionPane.showMessageDialog(null, "Error: Cannot vote for more than "+ Max_votes +" candidates.", "Error",JOptionPane.ERROR_MESSAGE);
                return;
            }else if (id.size()< Min_votes){
                JOptionPane.showMessageDialog(null, "Error: Please at least choose " + Min_votes+ " candidate(s) to votes.", "Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

            int[] candidate_id= new int[id.size()];
            for (int i = 0; i < id.size(); ++i)
                candidate_id[i] = id.get(i);
            
            
            person.castVote(election_id, candidate_id);

            castBackButton.doClick();

        } 
        else if (e.getSource() == viewDescription){// candidate view profile

           
            setProfile_fields();

            profile_main_panel.show();
            candidate_main_Panel.hide();

        }else if (e.getSource()== back_viewProfile){// candidate exits profile
            if (save_profile.isEnabled()){
                int option =JOptionPane.showConfirmDialog(null, "You are currently editing your profile, are you sure to exit this panel? ", "Error",JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.NO_OPTION)
                    return;
                
                setProfile_fields();
                enable_profileField(false);
                save_profile.setEnabled(false);
                edit_profile.setText("Edit");
            }
            profile_main_panel.hide();
            candidate_main_Panel.show();
        }
        else if (e.getSource() == submitForm_btn ){// candidate register
            String nic_str = nictxt.getText().trim(), postheld_str = postHeldtxt.getText().trim();
            String actualWork_str = actualworktxt.getText().trim(), office_str= officetxt.getText().trim(), home_str = hometxt.getText().trim(), mobile_str = mobiletxt.getText().trim();
            String email_str = emailtxt.getText().trim(), pass_str = pass_TextField.getText().trim(), cpass_str = cpass_TextField.getText().trim(), details_str = candidate_Description.getText().trim();
            String department_str = (String) mindeptxt.getSelectedItem();
            int department_index = mindeptxt.getSelectedIndex();
            boolean ownCar_bool = yes.isSelected(), agreement = agree_chBox.isSelected();
            int region_id =box.getSelectedIndex();
            String address_str = addrtxt.getText().trim();
            if (nic_str.equals("")|| postheld_str.equals("") || address_str.equals("") || actualWork_str.equals("") || office_str.equals("") || home_str.equals("")|| mobile_str.equals("") ||email_str.equals("") || department_index == 0 || region_id == 0){
                JOptionPane.showMessageDialog(this, "Fields cannot be empty","Fields error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!agreement){
                JOptionPane.showMessageDialog(this, "Please read the Declaration of Aplication section(Agreement not checked)","Agreement", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!pass_str.equals("") && !pass_str.equals(cpass_str)){
                JOptionPane.showMessageDialog(this, "Password fields should be the same","Password error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!pass_str.equals("")   && (pass_str.length() <8|| pass_str.length() >20)){
                JOptionPane.showMessageDialog(this, "Password length should be between 8 - 20","Password error", JOptionPane.WARNING_MESSAGE);
                return;
            }

           
            if (pass_str.equals(""))
                pass_str = Candidate.RandomPassword();
            

            if (Candidate.Register(nic_str, pass_str, region_id)){
                //Candidate new_candidate = new Candidate(nic_str, pass_str);
                User person = new Voter(nic_str, pass_str);
                if (!person.enter()) return;
                int id = person.getID();

                int own_car = (ownCar_bool)?1:0;
                Candidate.uploadDetails(id, details_str, address_str ,postheld_str, actualWork_str, office_str, home_str, mobile_str, email_str, own_car, department_index);

                //clear all text box
                nictxt.setText("");
                addrtxt.setText("");
                postHeldtxt.setText("");
                actualworktxt.setText("");
                officetxt.setText("");
                hometxt.setText("");
                mobiletxt.setText("");
                emailtxt.setText("");
                pass_TextField.setText("");
                cpass_TextField.setText("");
                mindeptxt.setSelectedIndex(0);
                yes.setSelected(false);
                no.setSelected(true);
                agree_chBox.setSelected(false);
                candidate_Description.setText("");
                box.setSelectedIndex(0);
                //goback
                back_btn.doClick();
            }

            //System.out.printf("Candidate\n nic: %s\n pass: %s\n details: %s\n address: %s\n postheld: %s\n paysite: %s\n actual workplace: %s\n office no: %s\n home no: %s\n mobile no: %s\n email: %s\n department: %s\n",nic_str, pass_str, details_str, address_str, postheld_str, paysite_str, actualWork_str, office_str, home_str, mobile_str, email_str, department_str);

                 
            
        }
        else if (e.getSource() == edit_profile){// candidate edit profile
            if (save_profile.isEnabled()){
                // reset
                setProfile_fields();
                enable_profileField(false);
                save_profile.setEnabled(false);
                edit_profile.setText("Edit");
                return;
            }
            edit_profile.setText("cancel");
            save_profile.setEnabled(true);
            enable_profileField(true);



        }else if (e.getSource() == save_profile){//candidate saves new profile

            String description_str =profile_descrption.getText().trim(), postheld_str =postHeldtxt_profile.getText().trim(), email_str = emailtxt_profile.getText().trim();
            int department_int = mindeptxt_profile.getSelectedIndex();
            String actualWorkplace_str = actualworktxt_profile.getText().trim(), office_str = officetxt_profile.getText().trim(), home_str = hometxt_profile.getText().trim(), mobile_str = mobiletxt_profile.getText().trim();
            int yes = (profile_yes.isSelected())? 1: 0;
            String adress_str = addrtxt_profile.getText().trim();
            if (description_str.equals("") || postheld_str.equals("") || email_str.equals("") || adress_str.equals("") ||  department_int == 0 || actualWorkplace_str.equals("") || office_str.equals("") || home_str.equals("") || mobile_str.equals("")  ){
                JOptionPane.showMessageDialog(this, "All fields should be selected", "Empty Fields",JOptionPane.WARNING_MESSAGE);
                return;
            }

            Boolean update = candidate_person.updateProfile(description_str,adress_str, postheld_str,actualWorkplace_str, office_str, home_str, mobile_str, email_str, yes, department_int);
            if (!update){
                setProfile_fields();
                JOptionPane.showMessageDialog(null, "Error: Please try again later.", "Cannot Update",JOptionPane.ERROR_MESSAGE);
            }

            
            enable_profileField(false);
            save_profile.setEnabled(false);
            edit_profile.setText("Edit");
        }
    }


    void setVoterPanel(){
        Voter_panel = new JPanel(null);
        Voter_panel.setBounds(0, 0, this.getWidth(), this.getHeight());
        JPanel mini = new JPanel(new GridLayout(3,1,50,50));
        mini.setBorder(BorderFactory.createTitledBorder( null, "Choose an option", TitledBorder.CENTER, TitledBorder.TOP, new Font("calibri",Font.BOLD,20), Color.BLACK));
        JLabel PanelFont = new JLabel("Voter Panel");
		PanelFont.setFont(new Font("times new roman", Font.BOLD,30));
        PanelFont.setBounds(500,10,500,50);
        
        
        voterLogin= new JButton("Login");
        voterRegister= new JButton("Register");
        voter_to_Home_btn = new JButton("Back to Home");

        //mini.add(Title);
        mini.add(voterLogin);
        mini.add(voterRegister);
        mini.add(voter_to_Home_btn);
        mini.setBounds(431,200,300,300);

        Voter_panel.add(PanelFont);
        Voter_panel.add(mini);
        Voter_panel.hide();
        add(Voter_panel);
    }

    void setVoterLoginPanel(){

        Voter_loginPanel= new JPanel(null);
        
        Voter_loginPanel.setBounds(0,0,this.getWidth(), this.getHeight());
        //Voter_loginPanel.setBackground(Color.WHITE);
        JPanel main_panel = new JPanel(new GridBagLayout());
        main_panel.setBounds(0,50,this.getWidth()-50, this.getHeight());

        JLabel PanelFont = new JLabel("Voter Login Panel");
		PanelFont.setFont(new Font("times new roman", Font.BOLD,30));
        PanelFont.setBounds(450,10,500,50);


        //declare and initialise components
        JLabel id_text = new JLabel("NIC: "), pass_text = new JLabel("Password");
        Nic = new Placeholder("Enter Your NIC", 15); password = new Placeholder("Enter Your password", 15);
        
        login = new JButton("Login"); // button
        backToVoter_panel = new JButton("Back");

        // add component to Voter_panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
         
        // add components to the Voter_panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        main_panel.add(id_text, constraints);
 
        constraints.gridx = 1;
        main_panel.add(Nic.getObject(), constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        main_panel.add(pass_text, constraints);
         
        constraints.gridx = 1;
        main_panel.add(password.getObject(), constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        main_panel.add(backToVoter_panel, constraints);

        
        constraints.gridx = 1 ;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        main_panel.add(login, constraints);

        Voter_loginPanel.add(PanelFont);
        Voter_loginPanel.add(main_panel);

        Voter_loginPanel.hide();
        add(Voter_loginPanel);
    }
    
    void setVoterRegisterPanel(){
        voter_registerPanel = new JPanel(null);
        voter_registerPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        JPanel jp_field, jp_btn;
	    JLabel jl_nic, jl_password, jl_Cpassword,  jl_region;
        JLabel PanelFont = new JLabel("Voter Register Panel");

		PanelFont.setFont(new Font("times new roman", Font.BOLD,30));
        PanelFont.setBounds(450,10,500,50);

        jl_nic = new JLabel("National Identity Card Number :");
		jt_nic = new JTextField(15);
		
		jl_password = new JLabel("Password :");
		jt_password = new JTextField(15);
		
		jl_Cpassword = new JLabel("Confirm Password :");
		jt_Cpassword = new JTextField(15);
		
		jl_region = new JLabel("Region :");
		jbox_region = new JComboBox(region);
		
		//adding into voter login panel
		jp_field = new JPanel(new GridLayout(4,2,10,30));
        jp_field.add(jl_nic);
		jp_field.add(jt_nic);
		jp_field.add(jl_region);
		jp_field.add(jbox_region);
		jp_field.add(jl_password);
		jp_field.add(jt_password);
		jp_field.add(jl_Cpassword);
		jp_field.add(jt_Cpassword);
		
		
		//panel for button
		jp_btn = new JPanel(new GridLayout(1,2,50,50));
		
		jb_register = new JButton("Register");
		jb_back = new JButton("Back");
		
		
		jp_btn.add(jb_back);
		jp_btn.add(jb_register);

        JPanel newPanel= new JPanel();//, notePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50,50));
        JLabel noteLabel = new JLabel("Note: if cannot figure out a password, keep BOTH pasword fields empty .(the program will auto generate a password for you)");
        noteLabel.setFont(new Font("times new roman", Font.ITALIC,15));
        
        //notePanel.add(noteLabel);
        
        newPanel.setBounds(0,50,this.getWidth()-50, this.getHeight());
        //Voter_loginPanel.setBackground(Color.WHITE);
        newPanel.setLayout(new GridBagLayout());
        
        
        // add component to Voter_panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(10, 10, 10, 10);
        
        // add components to the Voter_panel
        voter_registerPanel.add(PanelFont);

        constraints.gridx = 0;
        constraints.gridy = 0;     
        newPanel.add(jp_field, constraints);

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 1;
        newPanel.add(noteLabel, constraints);
         
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 2;
        newPanel.add(jp_btn, constraints);

        voter_registerPanel.add(newPanel);
        voter_registerPanel.hide();
        add(voter_registerPanel);
    }

    void setVoterHomePanel(){
        voter_main_Panel = new JPanel(null);
        voter_main_Panel.setBounds(0, 0, this.getWidth(), this.getHeight());
        JPanel mainPanel = new JPanel(new GridLayout(4,1,10,10));
        mainPanel.setBorder(BorderFactory.createTitledBorder( null, "Choose an option", TitledBorder.CENTER, TitledBorder.TOP, new Font("calibri",Font.BOLD,20), Color.BLACK));
        mainPanel.setBounds(431,200,300,300);
        
        JLabel PanelFont = new JLabel("Voter");
		PanelFont.setFont(new Font("times new roman", Font.BOLD,30));
        PanelFont.setBounds(500,10,500,50);

        Voter_name = new JLabel("Your name:- name");
		Voter_name.setFont(new Font("times new roman", Font.ITALIC,15));
        Voter_name.setBounds(getWidth()-200, 20, 500, 100);

        Voter_region = new JLabel("Your region:- region");
		Voter_region.setFont(new Font("times new roman", Font.ITALIC + Font.BOLD,15));
        Voter_region.setBounds(getWidth()-200, 50, 500, 100);

        voter_logoutButton= new JButton("Log out");
        voter_votingButton= new JButton("Cast vote");
        voter_viewCandidateButton= new JButton("View Candidate in area");
        voter_viewResultButton= new JButton("View Result");

        voter_logoutButton.addActionListener(this);
        voter_votingButton.addActionListener(this);
        voter_viewCandidateButton.addActionListener(this);
        voter_viewResultButton.addActionListener(this);

        mainPanel.add(voter_votingButton);
        mainPanel.add(voter_viewCandidateButton);
        mainPanel.add(voter_viewResultButton);
        mainPanel.add(voter_logoutButton);

        voter_main_Panel.add(PanelFont);
        voter_main_Panel.add(Voter_name);
        voter_main_Panel.add(Voter_region);
        voter_main_Panel.add(mainPanel);

        voter_main_Panel.hide();

        add(voter_main_Panel);
        
    }

    void setCandidateHomePanel(){
        candidate_main_Panel = new JPanel(null);
        candidate_main_Panel.setBounds(0, 0, this.getWidth(), this.getHeight());
        JPanel mainPanel = new JPanel(new GridLayout(5,1,10,10));
        mainPanel.setBounds(431,200,300,300);
        mainPanel.setBorder(BorderFactory.createTitledBorder( null, "Choose an option", TitledBorder.CENTER, TitledBorder.TOP, new Font("calibri",Font.BOLD,20), Color.BLACK));
        
        JLabel PanelFont = new JLabel("Candidate");
		PanelFont.setFont(new Font("times new roman", Font.BOLD,30));
        PanelFont.setBounds(500,10,500,50);

        candidate_name = new JLabel("Your name:- name");
		candidate_name.setFont(new Font("times new roman", Font.ITALIC,15));
        candidate_name.setBounds(getWidth()-200, 20, 500, 100);

        candidate_region = new JLabel("Your region:- region");
		candidate_region.setFont(new Font("times new roman", Font.ITALIC + Font.BOLD, 15));
        candidate_region.setBounds(getWidth()-200, 50, 500, 100);

        

        candidate_logoutButton= new JButton("Log out");
        candidate_votingButton= new JButton("Cast vote");
        candidate_viewCandidateButton= new JButton("View Candidate in area");
        candidate_viewResultButton= new JButton("View my Result");
        viewDescription = new JButton("View Profile");

        candidate_logoutButton.addActionListener(this);
        candidate_votingButton.addActionListener(this);
        candidate_viewCandidateButton.addActionListener(this);
        candidate_viewResultButton.addActionListener(this);
        viewDescription.addActionListener(this);

        mainPanel.add(viewDescription);
        mainPanel.add(candidate_votingButton);
        mainPanel.add(candidate_viewCandidateButton);
        mainPanel.add(candidate_viewResultButton);
        mainPanel.add(candidate_logoutButton);

        candidate_main_Panel.add(PanelFont);
        candidate_main_Panel.add(candidate_name);
        candidate_main_Panel.add(candidate_region);
        candidate_main_Panel.add(mainPanel);

        candidate_main_Panel.hide();

        add(candidate_main_Panel);
    }

    void setCandidatePanel(){
        Candidate_panel = new JPanel(null);
        Candidate_panel.setBounds(0, 0, this.getWidth(), this.getHeight());
        JPanel mini = new JPanel(new GridLayout(3,1,50,50));
        mini.setBorder(BorderFactory.createTitledBorder( null, "Choose an option", TitledBorder.CENTER, TitledBorder.TOP, new Font("calibri",Font.BOLD,20), Color.BLACK));
        JLabel PanelFont = new JLabel("Candidate Panel");
		PanelFont.setFont(new Font("times new roman", Font.BOLD,30));
        PanelFont.setBounds(500,10,500,50);
        
 
        candidateLogin_btn= new JButton("Login");
        candidateRegister_btn= new JButton("Register");
        candidate_to_Home_btn = new JButton("Back to Home");
      
        mini.add(candidateLogin_btn);
        mini.add(candidateRegister_btn);
        mini.add(candidate_to_Home_btn);
        mini.setBounds(431,200,300,300);

        Candidate_panel.add(PanelFont);
        Candidate_panel.add(mini);
        Candidate_panel.hide();
        add(Candidate_panel);
    }

    void setCandidateRegister(){
        JPanel bigpanel, jmain, candidate, persoDet, details, contactDet, otherDetails, decl, general, notePanel, passwordPanel, miniPass_panel, buttons;
        JLabel candidateProf, nic, title, surname, onames, maidenname, reg,addr;
        JLabel postHeld, mindep, actualwork;
        JLabel office, home, mobile, email, owncar;
        JLabel Pass_field, signature;
        
        candidate_registerPanel = new JPanel(null);
        candidate_registerPanel.setBounds(0, 0, this.getWidth(), this.getHeight());

        candidateProf = new JLabel("Candidate Profile");
		candidateProf.setFont(new Font("times new roman", Font.BOLD,30));
		candidateProf.setBounds(450,10,500,50);
		candidate_registerPanel.add(candidateProf);
		
		bigpanel = new JPanel();
		bigpanel.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		bigpanel.setBounds(10,70,1100,620);
		bigpanel.setLayout(new BoxLayout(bigpanel, BoxLayout.Y_AXIS));
		candidate_registerPanel.add(bigpanel);
		
		//main jpanel to include other jpanels
		jmain = new JPanel();
		jmain.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		bigpanel.add(jmain);
		
		jmain.setLayout(new BoxLayout(jmain, BoxLayout.Y_AXIS));
		jmain.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		candidate = new JPanel();
		candidate.setLayout(new GridLayout(1,2,5,10));
		jmain.add(candidate);
		
		//Personal Details
		//Creating a big Panel to include other small panels
		persoDet = new JPanel();
		persoDet.setLayout(new BoxLayout(persoDet, BoxLayout.Y_AXIS));
		candidate.add(persoDet);
		
		//Creating a jpanel inside persoDet
		details = new JPanel();
		details.setBorder(BorderFactory.createTitledBorder(null, "Particulars of Applicant", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		persoDet.add(details);
		
		//this panel contains 5 rows and 2 columms
		details.setLayout(new GridLayout(4,2,5,10));
		
		nic = new JLabel("1.1 National Identity Card No. :");
		nictxt = new JTextField(5);
		nictxt.setSize(10,1);
		details.add(nic);
		details.add(nictxt);
		
		/*title = new JLabel("1.2 Title :");
		JPanel mrmrs = new JPanel();
		mrmrs.setLayout(new GridLayout(1,3));
		mr = new JRadioButton("Mr",false);
		mrs = new JRadioButton("Mrs",false);
		ms = new JRadioButton("Miss",false);
		mrmrs.add(mr);
		mrmrs.add(mrs);
		mrmrs.add(ms);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(mr);
		buttonGroup.add(mrs);
		buttonGroup.add(ms);
		
		details.add(title);
		details.add(mrmrs);
		
		surname = new JLabel("1.3 Surname:");
		surnametxt = new JTextField(5);
		surnametxt.setSize(10,1);
		details.add(surname);
		details.add(surnametxt);
		
		onames = new JLabel("1.4 Other Name(s) in full:");
		onamestxt = new JTextField(5);
		onamestxt.setSize(10,1);
		details.add(onames);
		details.add(onamestxt);
		
		maidenname = new JLabel("1.5 Maiden Name (Where Applicable):");
		maidennametxt = new JTextField(5);
		maidennametxt.setSize(10,1);
		details.add(maidenname);
		details.add(maidennametxt);
		*/
		reg = new JLabel("1.2 District :");
		details.add(reg);
    	box = new JComboBox(region);
		details.add(box);

        JLabel description = new JLabel("1.3 Description: ");
        candidate_Description = new JTextArea();
        JScrollPane scroll = new JScrollPane(candidate_Description);
        details.add(description);
        details.add(scroll);
		
		addr = new JLabel("1.4 Permanent Residential Address:");
		addrtxt = new JTextField(5);
		addrtxt.setSize(10,1);
		details.add(addr);
		details.add(addrtxt);
		
		//---------------------------------------------------------------------------
		//creating small JPanel for contact details
		contactDet = new JPanel();
		contactDet.setBorder(BorderFactory.createTitledBorder(null, "Particulars of Present Occupation", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		persoDet.add(contactDet);
		contactDet.setLayout(new GridLayout(3,2,5,10));  
		
		postHeld = new JLabel("2.1 Post Held :");
		postHeldtxt = new JTextField(5);
		postHeldtxt.setSize(10,1);
		contactDet.add(postHeld);
		contactDet.add(postHeldtxt);
		
		mindep = new JLabel("2.2 Ministry/Department :");
		mindeptxt = new JComboBox(department);
		contactDet.add(mindep);
		contactDet.add(mindeptxt);
		
		actualwork = new JLabel("2.3 Actual workplace :");
		actualworktxt = new JTextField(5);
		actualworktxt.setSize(10,1);
		contactDet.add(actualwork);
		contactDet.add(actualworktxt);
		
        //password fields
        passwordPanel = new JPanel();
		passwordPanel.setLayout(new GridLayout(2,1,5,20));
		//jmain.add(passwordPanel);
		
		//passwordPanel.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		
		notePanel = new JPanel();
		passwordPanel.add(notePanel);
		notePanel.setLayout(new GridLayout(2,1,5,10));  
		JLabel noteLabel1 = new JLabel("Note: if cannot figure out a password, keep BOTH pasword fields empty ."), noteLabel2 = new JLabel("(the program will auto generate a password for you)");
        noteLabel1.setFont(new Font("times new roman", Font.ITALIC,15));
        noteLabel2.setFont(new Font("times new roman", Font.ITALIC,15));
        //JLabel l2 = new JLabel("4.2	 I also undertake to inform the Office of the Electoral Commissioner immediately in case I no longer satisfy any  change in my particulars or status.");
		notePanel.add(noteLabel1);
		notePanel.add(noteLabel2);
		
		miniPass_panel = new JPanel();
		miniPass_panel.setLayout(new GridLayout(2,2,50,10)); 
		passwordPanel.add(miniPass_panel);
		
		Pass_field = new JLabel("Password: ");
		pass_TextField = new JTextField();
		
		miniPass_panel.add(Pass_field);
		miniPass_panel.add(pass_TextField);
		
		signature = new JLabel("Repeat Password: ");
		cpass_TextField = new JTextField();
		
		miniPass_panel.add(signature);
		miniPass_panel.add(cpass_TextField);

        persoDet.add(passwordPanel);

		//----------------------------------------------------
		//second big panel
		otherDetails = new JPanel();
		candidate.add(otherDetails);
		otherDetails.setLayout(new BoxLayout(otherDetails, BoxLayout.Y_AXIS));
    
		general = new JPanel();
		general.setBorder(BorderFactory.createTitledBorder(null, "General Information", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		general.setLayout(new GridLayout(5,2,5,10));  
		otherDetails.add(general);
		
		office = new JLabel("3.1 Office No. :");
		officetxt = new JTextField(5);
		officetxt.setSize(10,1);
		general.add(office);
		general.add(officetxt);
		
		home = new JLabel("3.2 Home No. :");
		hometxt = new JTextField(5);
		hometxt.setSize(10,1);
		general.add(home);
		general.add(hometxt);
	
		mobile = new JLabel("3.3 Mobile No. :");
		mobiletxt = new JTextField(5);
		mobiletxt.setSize(10,1);
		general.add(mobile);
		general.add(mobiletxt);
		
		email = new JLabel("3.4 Email Address :");
		emailtxt = new JTextField(5);
		emailtxt.setSize(10,1);
		general.add(email);
		general.add(emailtxt);
		
		owncar = new JLabel("3.5 Do you own a car? :");
		JPanel radioOwnCar = new JPanel();
		radioOwnCar.setLayout(new GridLayout(1,2));  
		
		yes = new JRadioButton("Yes",false);
		no = new JRadioButton("No",true);
		radioOwnCar.add(yes);
		radioOwnCar.add(no);
		
		//create logical relationship between radio buttons
		type = new ButtonGroup();
		type.add(yes);
		type.add(no);
		
		//owncartxt.setSize(10,1);
		general.add(owncar);
		general.add(radioOwnCar);
		
		//-------------------------------------------------------------
		decl = new JPanel();
		decl.setBorder(BorderFactory.createTitledBorder(null, "Declaration of Applicant", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		decl.setLayout(new GridLayout(10,1,10,10));  
		otherDetails.add(decl);
		
		JLabel l1 = new JLabel("4.1 I, the undersigned, declare that the information given above is true and ");
		decl.add(l1);
		l1 = new JLabel("correct; and that I am eligible to work for elections and/or house-to-house enquiries  in that:-");
		decl.add(l1);
		l1 = new JLabel("enquiries  in that:-");
		decl.add(l1);
		l1 = new JLabel("(i) I am a civil servant;");
		decl.add(l1);
		l1 = new JLabel("(ii) I have not resigned or retired from the service;");
		decl.add(l1);
		l1= new JLabel("(iii) I have not taken up employment in a parastatal body, statutory body, local authority or the private sector;");
		decl.add(l1);
		l1 = new JLabel("(iv) I am not on leave without pay (except for study purposes);");
		decl.add(l1);
		l1 = new JLabel("(v) I am not employed in the Civil Service on a contract basis; and");
		decl.add(l1);
        l1 = new JLabel("(vi) I am not under interdiction.");
		decl.add(l1);
        agree_chBox = new JCheckBox("I agreee all the terms and condition above.");
		decl.add(agree_chBox);
		
		//------------------------------------------------------------------------------------------------
		/*passwordPanel = new JPanel();
		passwordPanel.setLayout(new GridLayout(2,1,5,20));
		jmain.add(passwordPanel);
		
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		
		sentence = new JPanel();
		passwordPanel.add(sentence);
		sentence.setLayout(new GridLayout(1,1,5,10));  
		JLabel noteLabel = new JLabel("Note: if cannot figure out a password, keep BOTH pasword fields empty .(the program will auto generate a password for you)");
        noteLabel.setFont(new Font("times new roman", Font.ITALIC,15));
        //JLabel l2 = new JLabel("4.2	 I also undertake to inform the Office of the Electoral Commissioner immediately in case I no longer satisfy any  change in my particulars or status.");
		sentence.add(noteLabel);
		
		miniPass_panel = new JPanel();
		miniPass_panel.setLayout(new GridLayout(1,4,100,100)); 
		passwordPanel.add(miniPass_panel);
		
		Pass_field = new JLabel("Password: ");
		pass_TextField = new JTextField();
		
		miniPass_panel.add(Pass_field);
		miniPass_panel.add(pass_TextField);
		
		signature = new JLabel("Repeat Password: ");
		cpass_TextField = new JTextField();
		
		miniPass_panel.add(signature);
		miniPass_panel.add(pass_TextField);
        */
		//-----------------------------------------------------------------
		
		buttons = new JPanel();
		buttons.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		bigpanel.add(buttons);
		back_btn = new JButton("Back");
		buttons.add(back_btn, BorderLayout.WEST);
		
		submitForm_btn = new JButton("Submit");
		buttons.add(submitForm_btn, BorderLayout.EAST);
		
		back_btn.addActionListener(this);
		submitForm_btn.addActionListener(this);
        candidate_registerPanel.hide();
        add(candidate_registerPanel);
    }

    void setCandidateLogin(){
        candidate_loginPanel = new JPanel(null);
        candidate_loginPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        JPanel main_panel = new JPanel();
        
        main_panel.setBounds(0,50,this.getWidth()-50, this.getHeight());
        //Voter_loginPanel.setBackground(Color.WHITE);
        main_panel.setLayout(new GridBagLayout());

        JLabel PanelFont = new JLabel("Candidate Login Panel");
        PanelFont.setFont(new Font("times new roman", Font.BOLD,30));
        PanelFont.setBounds(450,10,500,50);
        //declare and initialise components
        JLabel id_text = new JLabel("NIC: "), pass_text = new JLabel("Password");
        Nic_field = new Placeholder("Enter Your NIC", 15); passowrd_field = new Placeholder("Enter Your password", 15);
        
        login_btn = new JButton("Login"); // button
        bck_btn = new JButton("Back");

        // add component to Voter_panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
         
        // add components to the Voter_panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        main_panel.add(id_text, constraints);
 
        constraints.gridx = 1;
        main_panel.add(Nic_field.getObject(), constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        main_panel.add(pass_text, constraints);
         
        constraints.gridx = 1;
        main_panel.add(passowrd_field.getObject(), constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        main_panel.add(bck_btn, constraints);

        
        constraints.gridx = 1 ;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        main_panel.add(login_btn, constraints);

        candidate_loginPanel.add(PanelFont);
        candidate_loginPanel.add(main_panel);

        candidate_loginPanel.hide();

        login_btn.addActionListener(this);
        bck_btn.addActionListener(this);

        add(candidate_loginPanel);

    }

    void setCastVotePanel(){
        previousPanel = new JPanel();
        castVotePanel = new JPanel(null);
        castVotePanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        
        JLabel Title = new JLabel("Choose candidate to cast vote", JLabel.CENTER);
		Title.setFont(new Font("times new roman", Font.BOLD,15));
        
        JPanel main_panel = new JPanel(new GridLayout(3,1,50,50));
        main_panel.setBounds(300, 50, 500,600);

        JLabel PanelFont = new JLabel("Cast Vote");
		PanelFont.setFont(new Font("times new roman", Font.BOLD,30));
        PanelFont.setBounds(500,10,500,50);

        votingTable= new JTable();

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(votingTable);

        model = new DefaultTableModel()
        {
            @Override public Class<?> getColumnClass(int column){
                switch (column) {
                    
                    case 3:
                       return Boolean.class;
                
                    default:
                        return String.class;
                }
            }
            @Override public boolean isCellEditable(int row, int col){
                return (col != 1 && col != 0 && col != 2);
            }
        };

        votingTable.setModel(model);

        model.addColumn("id");
        model.addColumn("Name");
        model.addColumn("Department");
        model.addColumn("Vote");

        //fake data
        for (int i = 0; i < 7; ++i){
            model.addRow(new Object[0]);
            model.setValueAt(Integer.toString(i+1),i , 0);
            model.setValueAt("Name " + (i+1),i , 1);
            model.setValueAt("Department"+ (i+1), i, 2);
            model.setValueAt(false, i, 3);
        }
        /*
        votingTable.getColumnModel().setSelectionModel(new DefaultListSelectionModel(){
            @Override public boolean isSelectedIndex(int index){
                return votingTable.convertColumnIndexToModel(index) ==0;
            }
        });
        */
        castVoteButton = new JButton("Cast vote");
        castBackButton = new JButton("Back");

        castVoteButton.addActionListener(this);
        castBackButton.addActionListener(this);

        JPanel button_panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50,50));
        button_panel.add(castVoteButton);
        button_panel.add(castBackButton);

        main_panel.add(Title);
        main_panel.add(scroll);
        main_panel.add(button_panel);

        castVotePanel.add(PanelFont);
        castVotePanel.add(main_panel);

        castVotePanel.hide();

        add(castVotePanel);        
    }

    void previousPanel_show(){
        if (previousPanel == voter_main_Panel){
            voter_main_Panel.show();
        }else {
            candidate_main_Panel.show();
        }
    }

    void setResultPanel(){
        resultPanel = new JPanel(null);
        resultPanel.setBounds(0, 0, this.getWidth(), this.getHeight());

        JPanel main_panel = new JPanel(new GridLayout(2,1,50,50));
        main_panel.setBounds(325, 100, 500,600);
        //main_panel.setBounds(500, this.getWidth()/2 -400, 200,200);

        //display data
        final String[][] data = {{"1","Varren", "100"}, {"2", "Joe", "50"}, {"3", "James", "49"},{"4", "Jordan", "50"}};

        JLabel PanelFont = new JLabel("Result Panel");
		PanelFont.setFont(new Font("times new roman", Font.BOLD,30));
        PanelFont.setBounds(500,10,500,50);

        resultModel= new DefaultTableModel(data, column_result)
        {
          public boolean isCellEditable(int row, int column)
          {
            return false;
          }
        };
        
        resultTable = new JTable(resultModel);
        
        resultTable.setEnabled(false);
        result_back = new JButton("Back");
        result_back.addActionListener(this);
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(resultTable);
        JPanel btn_panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50,50));

        btn_panel.add(result_back);

        main_panel.add(scroll);
        main_panel.add(btn_panel);

        resultPanel.add(PanelFont);
        resultPanel.add(main_panel);

        resultPanel.hide();

        add(resultPanel);

    }

    void setViewCandidatesInAreaPanel(){
        viewCandidatesInAreaPanel = new JPanel(null);
        viewCandidatesInAreaPanel.setBounds(0, 0, this.getWidth(), this.getHeight());

        JPanel main_panel = new JPanel(new GridLayout(3,1,50,50)), candidate_detailPanel = new JPanel(new GridLayout(4,2,20,0));
        main_panel.setBounds(25, 100, 600,600);
        candidate_detailPanel.setBounds(725, 100, 300, 200);

        JLabel id_txt = new JLabel("ID:"), Name_txt = new JLabel("Name:"), Department_txt = new JLabel("Department:"), workplace_txt = new JLabel("Actual Workplace:") , details_txt= new JLabel("Description");
		//resetViewCandidateFields();
        viewId_label= new JLabel();
        viewName_label = new JLabel();
        viewDepartment_label = new JLabel();
        viewWorkplace_label = new JLabel("Candidate Workplace");
        viewDetails_label = new JLabel("Candidate Description");

        JScrollPane dScrollPane = new JScrollPane(viewDetails_label);
        //dScrollPane.setSize(200, 200);
        candidate_detailPanel.add(id_txt); candidate_detailPanel.add(viewId_label);
        candidate_detailPanel.add(Name_txt); candidate_detailPanel.add(viewName_label);
        candidate_detailPanel.add(Department_txt); candidate_detailPanel.add(viewDepartment_label);
        candidate_detailPanel.add(workplace_txt); candidate_detailPanel.add(viewWorkplace_label);
        JPanel detailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 50)), details_panel = new JPanel(new FlowLayout(FlowLayout.CENTER,10, 10));
        detailPanel.add(details_txt); detailPanel.add(dScrollPane);

        details_panel.add(candidate_detailPanel);
        details_panel.add(detailPanel);
        detailPanel.setSize(200, 200);
        JLabel PanelFont = new JLabel("View Candidates in Area");
		PanelFont.setFont(new Font("times new roman", Font.BOLD,30));
        PanelFont.setBounds(500,10,500,50);

        viewCandidatesArea_backbtn = new JButton("Back");
        viewCandidatesArea_backbtn.addActionListener(this);

        Object [][] data = {
            {"1", "John", "MMM"},
            {"2", "Noa", "MMM"},
            {"3", "Hansan", "MSM"},
            {"4", "Vode", "MMM"},
        };

        

        model_viewCandidate = new DefaultTableModel(data, Column_viewcandidates)
        {
          public boolean isCellEditable(int row, int column)
          {
            return false;
          }
        };
        
        viewCandidatesAreaTable = new JTable(model_viewCandidate);
        
        ListSelectionModel select= viewCandidatesAreaTable.getSelectionModel();  
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        select.addListSelectionListener(new ListSelectionListener() {  
            public void valueChanged(ListSelectionEvent e) {  
                String id_str = "", name_str= "", department_str ="";  
                int row = viewCandidatesAreaTable.getSelectedRow();    
                // rowSelected = row;
                // columnSelected =viewCandidatesAreaTable.getSelectedColumn();
                id_str = (String) viewCandidatesAreaTable.getValueAt(row, 0);  
                name_str = (String)viewCandidatesAreaTable.getValueAt(row, 1);  
                department_str = (String)viewCandidatesAreaTable.getValueAt(row, 2);  
                String[] data = User.ViewCandidate(Integer.parseInt(id_str));

                if (data == null) return;
                viewId_label.setText(data[0]);
                viewName_label.setText(data[1]);
                viewWorkplace_label.setText(data[2]);
                viewDetails_label.setText(data[3]);
                viewDepartment_label.setText(data[4]);
                // viewId_label.setText(id_str);
                // viewName_label.setText(name_str);
                // viewWorkplace_label.setText(department_str);

                //viewCandidatesAreaTable.getSelectionModel().removeSelectionInterval(row, row);
                
            }       
        });  
        
        
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(viewCandidatesAreaTable);

        JLabel Title = new JLabel("Candidates in your Area", JLabel.CENTER);
		Title.setFont(new Font("times new roman", Font.BOLD,15));

        JPanel btn_panel =new JPanel(new FlowLayout(FlowLayout.CENTER, 50,50));
        btn_panel.add(viewCandidatesArea_backbtn);
        main_panel.add(Title);
        main_panel.add(scroll);
        main_panel.add(btn_panel);
        details_panel.setBounds(main_panel.getWidth()+100, 100, 500, 500);
        viewCandidatesInAreaPanel.add(PanelFont);
        viewCandidatesInAreaPanel.add(main_panel);
        viewCandidatesInAreaPanel.add(details_panel);

        viewCandidatesInAreaPanel.setVisible(false);

        add(viewCandidatesInAreaPanel);

    }

    boolean checkElection(){
        try {
            String sql ="Select COUNT(id) FROM Election WHERE Open = 1;";

            Statement stmt = DBConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()){
                election_id =0;
                return false;
            }

            int count = rs.getInt(1);
            if (  count == 0 ){
                JOptionPane.showMessageDialog(this, "Error: Election is closed", "Election CLose",JOptionPane.WARNING_MESSAGE);
                election_id =0;
                return false;
            }else if ( count > 1 ){
                JOptionPane.showMessageDialog(this, "Error: An error has occured, please try later", "Error",JOptionPane.ERROR_MESSAGE);
                election_id =0;
                return false;
            }
            

            sql = "Select id FROM Election WHERE Open = 1;";
            rs = stmt.executeQuery(sql);
            if (!rs.next()){ 
                election_id =0; 
                return false;
            }

            election_id = rs.getInt(1);
            stmt.close();
        } catch (Exception e) {
            election_id =0;
            JOptionPane.showMessageDialog(null, "Error: Access Denied." +e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    boolean getRecentlyClosedElection(){
        try {
            String sql ="Select COUNT(id) FROM Election WHERE Open = 0 AND RecentlyClosed = 1;";

            Statement stmt = DBConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()){
                election_id =-1;
                return false;
            }

            int count = rs.getInt(1);
            if (  count == 0 ){
                JOptionPane.showMessageDialog(this, "Error: Result has been published", "Result not published",JOptionPane.WARNING_MESSAGE);
                election_id =-1;
                return false;
            }else if ( count > 1 ){
                JOptionPane.showMessageDialog(this, "Error: An error has occured, please try later", "Error",JOptionPane.ERROR_MESSAGE);
                election_id =-1;
                return false;
            }
            

            sql = "Select id FROM Election WHERE Open = 0 and RecentlyClosed = 1;";
            rs = stmt.executeQuery(sql);
            if (!rs.next()){ 
                election_id =-1; 
                return false;
            }

            election_id = rs.getInt(1);
            stmt.close();
        } catch (Exception e) {
            election_id =-1;
            JOptionPane.showMessageDialog(null, "Error: Access Denied." +e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
   
    boolean checkVote(int VoterId){
        try {
            String sql = "SELECT COUNT(Voter_id) FROM Votes Where Voter_id = "+VoterId + " AND Election_id = " + election_id;
            Statement stmt = DBConnection.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()){ 
                JOptionPane.showMessageDialog(null, "Error: Please try again later", "Error",JOptionPane.ERROR_MESSAGE);
                return false;
            }

            int vote = rs.getInt(1);
            if (vote >0){
                JOptionPane.showMessageDialog(null, "Error: Cannot vote twice.", "Error",JOptionPane.ERROR_MESSAGE);
                return false;
            }
            stmt.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Access Denied." +e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    void resetViewCandidateFields(){

        viewId_label.setText("");
        viewName_label.setText("");
        viewDepartment_label.setText("");
        viewWorkplace_label.setText("");
        viewDetails_label.setText("");

    }

    void setCandidateProfile(){
        JPanel profile_bigpanel, profile_jmain, profile_candidate, profile_persoDet, profile_details, profile_contactDet, profile_otherDetails, profile_decl, profile_general, profile_sentence, profile_acknowledgement, profile_datesig, profile_buttons;
	    JLabel candidateProf, nic_profile, reg_profile, addr_profile,desciptor_profile;
	    JLabel postHeld_profile, mindep_profile, actualwork__profile;
	    JLabel office__profile, home_profile, mobile_profile, email_profile, owncar_profile;
        //Letting user know which window they are in by displaying a page title
        profile_main_panel = new JPanel(null);
        profile_main_panel.setBounds(0,0, this.getWidth(), this.getHeight());

		JLabel candidateViewProf = new JLabel("Candidate Profile");
		candidateViewProf.setFont(new Font("times new roman", Font.BOLD,30));
		
		candidateViewProf.setBounds(450,10,500,50);
		profile_main_panel.add(candidateViewProf);
		
		profile_bigpanel = new JPanel();
		profile_bigpanel.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		profile_bigpanel.setBounds(10,70,1100,620);
		profile_bigpanel.setLayout(new BoxLayout(profile_bigpanel, BoxLayout.Y_AXIS));
		profile_main_panel.add(profile_bigpanel);
		
		//main jpanel to include other jpanels
		profile_jmain = new JPanel();
		profile_jmain.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		profile_bigpanel.add(profile_jmain);
		
		profile_jmain.setLayout(new BoxLayout(profile_jmain, BoxLayout.Y_AXIS));
		profile_jmain.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		profile_candidate = new JPanel();
		profile_candidate.setLayout(new GridLayout(1,2,5,10));
		profile_jmain.add(profile_candidate);
		
		//Personal Details
		//Creating a big Panel to include other small panels
		profile_persoDet = new JPanel();
		profile_persoDet.setLayout(new BoxLayout(profile_persoDet, BoxLayout.Y_AXIS));
		profile_candidate.add(profile_persoDet);
		
		//Creating a jpanel inside persoDet
		profile_details = new JPanel();
		profile_details.setBorder(BorderFactory.createTitledBorder(null, "Particulars of Applicant", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		profile_persoDet.add(profile_details);
		
		//this panel contains 5 rows and 2 columms
		profile_details.setLayout(new GridLayout(4,2,5,10));
		
		nic_profile = new JLabel("1.1 National Identity Card No. :");
		nictxt_profile = new JTextField(5);
		nictxt_profile.setEditable(false);
		//nictxt.setText(rs.getString("nic"));
		profile_details.add(nic_profile);
		profile_details.add(nictxt_profile);
		
		/*title_profile = new JLabel("1.2 Title :");
		titletxt_profile = new JTextField(5);
		//titletxt.setText(rs.getString("title"));
		titletxt_profile.setEditable(false);
		profile_details.add(title_profile);
		profile_details.add(titletxt_profile);
		
		surname_profile = new JLabel("1.3 Surname:");
		surnametxt_profile = new JTextField(5);
		//surnametxt.setText(rs.getString("surname"));
		surnametxt_profile.setEditable(false);
		profile_details.add(surname_profile);
		profile_details.add(surnametxt_profile);
		
		onames__profile = new JLabel("1.4 Other Name(s) in full:");
		onamestxt_profile = new JTextField(5);
		//onamestxt.setText(rs.getString("other_names"));
		onamestxt_profile.setEditable(false);
		profile_details.add(onames__profile);
		profile_details.add(onamestxt_profile);
		
		maidenname__profile = new JLabel("1.5 Maiden Name (Where Applicable):");
		maidennametxt_profile = new JTextField(5);
		//maidennametxt.setText(rs.getString("maiden_name"));
		maidennametxt_profile.setEditable(false);
		profile_details.add(maidenname__profile);
		profile_details.add(maidennametxt_profile);
		*/
		reg_profile = new JLabel("1.2 District :");
		regtxt_profile = new JComboBox(region);
		//regtxt.setText(rs.getString("district"));
		regtxt_profile.setEnabled(false);
		profile_details.add(reg_profile);
		profile_details.add(regtxt_profile);
		
        desciptor_profile = new JLabel("1.3 Description:");
        profile_descrption = new JTextArea();
        profile_descrption.setEditable(false);
        profile_details.add(desciptor_profile);
        profile_details.add(profile_descrption);

		addr_profile = new JLabel("1.4 Permanent Residential Address:");
		addrtxt_profile = new JTextField(5);
		//addrtxt.setText(rs.getString("address"));
		addrtxt_profile.setEditable(false);
		profile_details.add(addr_profile);
		profile_details.add(addrtxt_profile);
		
		//---------------------------------------------------------------------------
		//creating small JPanel for contact details
		profile_contactDet = new JPanel();
		profile_contactDet.setBorder(BorderFactory.createTitledBorder(null, "Particulars of Present Occupation", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		profile_persoDet.add(profile_contactDet);
		profile_contactDet.setLayout(new GridLayout(3,2,5,10));  
		
		postHeld_profile = new JLabel("2.1 Post Held :");
		postHeldtxt_profile = new JTextField(5);
		//postHeldtxt.setText(rs.getString("post_held"));
		postHeldtxt_profile.setEditable(false);
		profile_contactDet.add(postHeld_profile);
		profile_contactDet.add(postHeldtxt_profile);
		
	
		
		mindep_profile = new JLabel("2.2 Ministry/Department :");
		mindeptxt_profile = new JComboBox(department);
		//mindeptxt.setText(rs.getString("ministry_department"));
		mindeptxt_profile.setEnabled(false);
		profile_contactDet.add(mindep_profile);
		profile_contactDet.add(mindeptxt_profile);
		
		actualwork__profile = new JLabel("2.3 Actual workplace :");
		actualworktxt_profile = new JTextField(5);
		//actualworktxt.setText(rs.getString("actual_workplace"));
		actualworktxt_profile.setEditable(false);
		profile_contactDet.add(actualwork__profile);
		profile_contactDet.add(actualworktxt_profile);
		
		//----------------------------------------------------
		//second big panel
		profile_otherDetails = new JPanel();
		profile_candidate.add(profile_otherDetails);
		profile_otherDetails.setLayout(new BoxLayout(profile_otherDetails, BoxLayout.Y_AXIS));
		
		profile_general = new JPanel();
		profile_general.setBorder(BorderFactory.createTitledBorder(null, "General Information", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		profile_general.setLayout(new GridLayout(5,2,5,10));  
		profile_otherDetails.add(profile_general);
		
		office__profile = new JLabel("3.1 Office No. :");
		officetxt_profile = new JTextField(5);
		//officetxt.setText(Integer.toString(rs.getInt("office_no")));
		officetxt_profile.setEditable(false);
		profile_general.add(office__profile);
		profile_general.add(officetxt_profile);
		
		home_profile = new JLabel("3.2 Home No. :");
		hometxt_profile = new JTextField(5);
		//hometxt.setText(Integer.toString(rs.getInt("home_no")));
		hometxt_profile.setEditable(false);
		profile_general.add(home_profile);
		profile_general.add(hometxt_profile);
	
		mobile_profile = new JLabel("3.3 Mobile No. :");
		mobiletxt_profile = new JTextField(5);
		//mobiletxt.setText(Integer.toString(rs.getInt("mobile_no")));
		mobiletxt_profile.setEditable(false);
		profile_general.add(mobile_profile);
		profile_general.add(mobiletxt_profile);
		
		email_profile = new JLabel("3.4 Email Address :");
		emailtxt_profile = new JTextField(5);
		//emailtxt.setText(rs.getString("email"));
		emailtxt_profile.setEditable(false);
		profile_general.add(email_profile);
		profile_general.add(emailtxt_profile);
		
		owncar_profile = new JLabel("3.5 Do you own a car? :");
		JPanel radioPanel = new JPanel(new GridLayout(1,2, 50, 50));
        profile_yes = new JRadioButton("yes", false);
        profile_no = new JRadioButton("no", true);
        profile_no.setEnabled(false);
        profile_yes.setEnabled(false);
        radioPanel.add(profile_yes);
        radioPanel.add(profile_no);
        ButtonGroup gp= new ButtonGroup();
        gp.add(profile_yes);
        gp.add(profile_no);
		profile_general.add(owncar_profile);
		profile_general.add(radioPanel);
		
		
		//-------------------------------------------------------------
		profile_decl = new JPanel();
		profile_decl.setBorder(BorderFactory.createTitledBorder(null, "Declaration of Applicant", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		profile_decl.setLayout(new GridLayout(9,1,10,10));  
		profile_otherDetails.add(profile_decl);
		
		JLabel l1 = new JLabel("4.1 I, the undersigned, declare that the information given above is true and ");
		profile_decl.add(l1);
		l1 = new JLabel("correct; and that I am eligible to work for elections and/or house-to-house enquiries  in that:-");
		profile_decl.add(l1);
		l1 = new JLabel("enquiries  in that:-");
		profile_decl.add(l1);
		l1 = new JLabel("(i) I am a civil servant;");
		profile_decl.add(l1);
		l1 = new JLabel("(ii) I have not resigned or retired from the service;");
		profile_decl.add(l1);
		l1= new JLabel("(iii) I have not taken up employment in a parastatal body, statutory body, local authority or the private sector;");
		profile_decl.add(l1);
		l1 = new JLabel("(iv) I am not on leave without pay (except for study purposes);");
		profile_decl.add(l1);
		l1 = new JLabel("(v) I am not employed in the Civil Service on a contract basis; and");
		profile_decl.add(l1);
		l1 = new JLabel("(vi) I am not under interdiction.");
		profile_decl.add(l1);
		/*
		//------------------------------------------------------------------------------------------------
		profile_acknowledgement = new JPanel();
		profile_acknowledgement.setLayout(new GridLayout(2,1,5,20));
		profile_jmain.add(profile_acknowledgement);
		
		profile_acknowledgement.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		
		profile_sentence = new JPanel();
		profile_acknowledgement.add(profile_sentence);
		profile_sentence.setLayout(new GridLayout(1,1,5,10));  
		JLabel l2 = new JLabel("4.2	 I also undertake to inform the Office of the Electoral Commissioner immediately in case I no longer satisfy any  change in my particulars or status.");
		profile_sentence.add(l2);
		
		profile_datesig = new JPanel();
		profile_datesig.setLayout(new GridLayout(1,4,100,100)); 
		profile_acknowledgement.add(profile_datesig);
		
		date = new JLabel("Date :");
		datetxt = new JTextField(5);
		//datetxt.setText(rs.getString("date"));
		datetxt.setEditable(false);
		profile_datesig.add(date);
		profile_datesig.add(datetxt);
		
		signature = new JLabel("Signature :");
		signaturetxt = new JTextField(5);
		//signaturetxt.setText(rs.getString("signature"));
		signaturetxt.setEditable(false);
		profile_datesig.add(signature);
		profile_datesig.add(signaturetxt);
		//-----------------------------------------------------------------
		*/
		profile_buttons = new JPanel();
		profile_buttons.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		profile_bigpanel.add(profile_buttons);
		
		back_viewProfile = new JButton("Back");
		profile_buttons.add(back_viewProfile, BorderLayout.WEST);
		
		back_viewProfile.addActionListener(this);

		edit_profile = new JButton("Edit");
		profile_buttons.add(edit_profile, BorderLayout.CENTER);
        
        save_profile = new JButton("Save");
		profile_buttons.add(save_profile, BorderLayout.EAST);

        save_profile.addActionListener(this);
        save_profile.setEnabled(false);
        edit_profile.addActionListener(this);
		
		profile_main_panel.hide();
        add(profile_main_panel);
    }

    void enable_profileField(boolean enable){
        profile_descrption.setEditable(enable);
        postHeldtxt_profile.setEditable(enable);
        //paysiteCodetxt_profile.setEditable(enable);
        mindeptxt_profile.setEnabled(enable);
        actualworktxt_profile.setEditable(enable);
        officetxt_profile.setEditable(enable);
        hometxt_profile.setEditable(enable);
        mobiletxt_profile.setEditable(enable);
        emailtxt_profile.setEditable(enable);
        addrtxt_profile.setEditable(enable);
        profile_yes.setEnabled(enable);
        profile_no.setEnabled(enable);
		//regtxt_profile.setEnabled(enable);

    }

    void setProfile_fields(){
        Object[] data = candidate_person.getProfile();
        if (data.length ==0 ){
            JOptionPane.showMessageDialog(this, "Error: Please close program and try again.", "Error",JOptionPane.ERROR_MESSAGE);
            return ;
        }

        nictxt_profile.setText(candidate_person.getNIC());
        profile_descrption.setText(String.valueOf(data[0]));
        postHeldtxt_profile.setText(String.valueOf(data[1]));
        //paysiteCodetxt_profile.setText(String.valueOf(data[2]));
        mindeptxt_profile.setSelectedIndex(Integer.parseInt(String.valueOf(data[8])));
        actualworktxt_profile.setText(String.valueOf(data[2]));
        officetxt_profile.setText(String.valueOf(data[3]));
        hometxt_profile.setText(String.valueOf(data[4]));
        mobiletxt_profile.setText(String.valueOf(data[5]));
        emailtxt_profile.setText(String.valueOf(data[6]));;
        int own_car_int = Integer.parseInt(String.valueOf(data[7]));
        regtxt_profile.setSelectedIndex(Integer.parseInt(String.valueOf(data[9])));
        addrtxt_profile.setText(String.valueOf(data[10]));

        if (own_car_int == 1)
            profile_yes.setSelected(true);
        else 
            profile_no.setSelected(true);

    }
}