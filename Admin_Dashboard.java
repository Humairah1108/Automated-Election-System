import java.sql.*;
import javax.swing.border.Border;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.util.*;
public class Admin_Dashboard extends JFrame implements ActionListener {
    private Admin_Gui loginWindowGui;
  
    //home page
    private JPanel HomePage;
    private JButton viewCandidate_btn, registerCandidate_btn, generateResult_btn, logout_btn, createElection_btn;

    //candidate page 
    private JPanel viewCandidatePanel;
    private JButton viewCand_backbtn;
    private DefaultTableModel model_viewCandidate;
    private JTable candidatesTable;
    //register Candidates
    private JPanel RegisterPanel ;
    private JButton Admit_btn, delete_btn, register_back_btn;
    private DefaultTableModel model;
    private JTable registerTable;
    
    //see result
    private JPanel ResultPanel;
    private JTable resultTable;
    private DefaultTableModel Resultmodel;
    private JButton resultBackBtn;
    private final String[] columnResult = {"ID", "Name", "Region", "Department", "No of Votes"};
    private int election_id=0;
    //admin
    Admin rootAdmin;

    Admin_Dashboard(Admin root, Admin_Gui login){

        super("Admin System");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DBConnection.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Election_system","root", "");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }

        loginWindowGui = login;
        rootAdmin = root;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
   
        //set Home page
        HomePage = new JPanel(null);

        HomePage.setBounds(0, 0, this.getWidth(), this.getHeight());

        JLabel homeFont = new JLabel("Admin Home Page");
        homeFont.setFont(new Font("times new roman", Font.BOLD,30));
        homeFont.setBounds(450,10,500,50);

        JPanel btn_Panel = new JPanel(new GridLayout(5,1,10,10));

        viewCandidate_btn= new JButton("View Candidate");
        registerCandidate_btn= new JButton("Register Candidates");
        generateResult_btn= new JButton("Generate Result");
        createElection_btn = new JButton("Create Election");
        logout_btn= new JButton("Log out");

        btn_Panel.setBounds(431,200,300,300);
        btn_Panel.setBorder(BorderFactory.createTitledBorder( null, "Choose an option", TitledBorder.CENTER, TitledBorder.TOP, new Font("calibri",Font.BOLD,20), Color.BLACK));

        btn_Panel.add(viewCandidate_btn);
        btn_Panel.add(registerCandidate_btn);
        btn_Panel.add(generateResult_btn);
        btn_Panel.add(createElection_btn);
        btn_Panel.add(logout_btn);

        HomePage.add(homeFont);
        HomePage.add(btn_Panel);

        add(HomePage);
        this.setSize(1150,750);
        setResizable(false);
        setVisible(true);

        //set others pannels
        setviewCandidate();
        setRegisterCandidate();
        setResult();

        //add action listener
        registerCandidate_btn.addActionListener(this);
        viewCandidate_btn.addActionListener(this);
        generateResult_btn.addActionListener(this);
        createElection_btn.addActionListener(this);
        logout_btn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewCand_backbtn){// user go back from view candidate
            viewCandidatePanel.hide();
            HomePage.show();
        }else if (e.getSource() == viewCandidate_btn){// user view candidate

            String[][] data = Admin.getAllCandidate();

            if (data.length == 0){  
                JOptionPane.showMessageDialog(this, "There is no candidates", "Error",JOptionPane.WARNING_MESSAGE);
                return;
            }

            
            model_viewCandidate =  (DefaultTableModel)candidatesTable.getModel();

            int rowCount = model_viewCandidate.getRowCount();
            for (int  i = 0; i < data.length; ++i)
                model_viewCandidate.addRow(data[i]);

            //remove selection
            candidatesTable.setRowSelectionInterval(rowCount, rowCount);
            for (int i = rowCount -1; i >= 0; i--)
                model_viewCandidate.removeRow(0);

            viewCandidatePanel.show();
            HomePage.hide();
        }else if(e.getSource() == registerCandidate_btn){ // user enter register panel
            String [][] data = Admin.getAllRegisteredCandidate();

            if (data.length == 0){  
                JOptionPane.showMessageDialog(this, "There is no candidates to register", "Error",JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            model =  (DefaultTableModel)registerTable.getModel();

            int rowCount = model.getRowCount();
            for (int  i = 0; i < data.length; ++i){
                Object[] obj = {data[i][0], data[i][1],  data[i][2], data[i][3], false};
                model.addRow(obj);
            }
            //remove selection
            registerTable.setRowSelectionInterval(rowCount, rowCount);
            for (int i = rowCount -1; i >= 0; i--)
                model.removeRow(0);
            
            HomePage.hide();
            RegisterPanel.show();
        }else if(e.getSource() == register_back_btn){// user exits register panel
            
            HomePage.show();
            RegisterPanel.hide();
        }
        else if(e.getSource() == generateResult_btn){// user enters result panel
            if (checkElection()){
                rootAdmin.displayResult(election_id);//generate election
            }
            if (!getRecentlyClosedElection()){
                return;
            }

            String[][] data = Result.get_report(election_id, 0);

            if (data.length == 0){
                JOptionPane.showMessageDialog(this, "Table Empty.", "Result Error",JOptionPane.WARNING_MESSAGE);
                return;
            }

            Resultmodel =  (DefaultTableModel)resultTable.getModel();

            int rowCount = Resultmodel.getRowCount();
            for (int  i = 0; i < data.length; ++i)
                Resultmodel.addRow(data[i]);

            //remove selection
            resultTable.setRowSelectionInterval(rowCount, rowCount);
            for (int i = rowCount -1; i >= 0; i--)
                Resultmodel.removeRow(0);
            
            //resultTable = new JTable(data, columnResult);

            HomePage.hide();
            ResultPanel.show();
        }
        else if(e.getSource() == resultBackBtn){// user exits result panel
            HomePage.show();
            ResultPanel.hide();
        }else if(e.getSource() == logout_btn){// user logsout
            this.dispose();
            loginWindowGui.setVisible(true);
        }
        else if(e.getSource() == createElection_btn){// create Election
            if (checkElection()){
                JOptionPane.showMessageDialog(this, "Error: cannot create election when another one is open", "Election",JOptionPane.WARNING_MESSAGE);
                return;
            }
            //set all recentlyclosed to 0
            closeRecentlyClosed();
            // create election 
            rootAdmin.CreateElection();
        }
        else if(e.getSource() == Admit_btn){// admit candidates
            int candidate_id[] = getCandidateId();
            if (candidate_id.length == 0|| candidate_id == null)
                return;
            if (candidate_id.length == 1){
                rootAdmin.registerCandidate(candidate_id[0]);
            }else {
                rootAdmin.AdmitCandidate(candidate_id);
            }
            String [][] data = Admin.getAllRegisteredCandidate();

            if (data.length == 0){
                JOptionPane.showMessageDialog(this, "There is no candidates to register", "Message",JOptionPane.PLAIN_MESSAGE);
                register_back_btn.doClick();
                return;
            }
            registerCandidate_btn.doClick();
        }
        else if(e.getSource() == delete_btn){// delete candidates
            int candidate_id[] = getCandidateId();
            if (candidate_id.length == 0|| candidate_id == null)
                return;

            rootAdmin.deleteCandidate(candidate_id);

            String [][] data = Admin.getAllRegisteredCandidate();

            if (data.length == 0){
                JOptionPane.showMessageDialog(this, "There is no candidates to register", "Message",JOptionPane.PLAIN_MESSAGE);
                register_back_btn.doClick();
                return;
            }

            registerCandidate_btn.doClick();
        }

    }

    void setviewCandidate(){
        viewCandidatePanel = new JPanel(null);
        viewCandidatePanel.setBounds(0, 0, this.getWidth(), this.getHeight());

        JLabel gender_txt = new JLabel("Gender:"), address_txt = new JLabel("Address:"), post_held_txt= new JLabel("Post held:"), actual_workplace_txt =new JLabel("Actual workplace:"), office_txt = new JLabel("Office no:"), home_txt = new JLabel("Home no:"), mobile_txt = new JLabel("Mobile no:"), email_txt =new JLabel("Email: "), owncar_txt= new JLabel("Own car:");
        JLabel gender_field = new JLabel("M"), address_field= new JLabel("Railway Road"), post_held_field= new JLabel("Manager"), actual_workplace_field = new JLabel("Port Louis"), office_field = new JLabel("59049437"), home_field = new JLabel("6223595"), mobile_field = new JLabel("59049437"), email_field = new JLabel("abhaybandhu"), owncar_field = new JLabel("NO");

        JLabel candidateFont = new JLabel("View Candidate page");
        candidateFont.setFont(new Font("times new roman", Font.BOLD,30));
        candidateFont.setBounds(450,10,500,50);

        JPanel main_panel = new JPanel(new GridLayout(2,1,10,10)), tablePanel = new JPanel(new GridLayout(1,1,10,10)), btnPanel = new JPanel(), candidate_details  = new JPanel(new GridLayout(9,2,10,10));

        final String[] columns={"ID", "Names","Area"};
        final String[][] data = {
            {"1", "John", "MMMjhbdfhjbfkdbfj"},
            {"2", "Noa", "MMM"},
            {"3", "Hansan", "MSM"},
            {"4", "Vode", "MMM"}
        };

        viewCand_backbtn = new JButton("Back Home");

        candidatesTable = new JTable(data, columns);

        model_viewCandidate = new DefaultTableModel(data, columns)
        {
          public boolean isCellEditable(int row, int column)
          {
            return false;
          }
        };
        JTextArea Description_field = new JTextArea("This is a description text dfjvfjdhvfdjf dhbfvjdvdhfbdhgdvbfgdbjhgdvfdf");
        
        candidatesTable = new JTable(model_viewCandidate);
        
        ListSelectionModel select= candidatesTable.getSelectionModel();  
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        select.addListSelectionListener(new ListSelectionListener() {  
            public void valueChanged(ListSelectionEvent e) {  
                String id_str = "";  
                int row = candidatesTable.getSelectedRow();    
                id_str = (String) candidatesTable.getValueAt(row, 0);  
                Object data[] = Admin.getCandidateInfo(Integer.parseInt(id_str));

                if (data.length ==0 || data == null){
                    return;
                }
                String gen = (String.valueOf(data[4]).equals("M"))? "Male": "Female";
                gender_field.setText(gen);
                address_field.setText(String.valueOf(data[5]));
                post_held_field.setText(String.valueOf(data[6]));
                actual_workplace_field.setText(String.valueOf(data[7]));
                office_field.setText(String.valueOf(data[8]));
                home_field.setText(String.valueOf(data[9]));
                mobile_field.setText(String.valueOf(data[10]));
                email_field.setText(String.valueOf(data[11]));
                String ownCar = (Integer.parseInt(String.valueOf(data[12])) == 0)? "NO" : "YES";
                owncar_field.setText(ownCar);
                Description_field.setText(String.valueOf(data[13]));


                
                
            }       
        });  

        
        JScrollPane scroll = new JScrollPane(candidatesTable);
        //scroll.setSize(500, 500);
        //tablePanel.add(tableFont);
        tablePanel.add(scroll);

        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50,50));
        btnPanel.add(viewCand_backbtn);

        main_panel.setBounds(25, 100, 600,600);
        main_panel.add(tablePanel);
        main_panel.add(btnPanel);

        viewCandidatePanel.add(candidateFont);


        candidate_details.setBounds(725, 100, 400, 200);
        
        candidate_details.add(gender_txt); candidate_details.add(gender_field);
        candidate_details.add(address_txt); candidate_details.add(address_field);
        candidate_details.add(post_held_txt); candidate_details.add(post_held_field);
        candidate_details.add(actual_workplace_txt); candidate_details.add(actual_workplace_field);
        candidate_details.add(office_txt); candidate_details.add(office_field);
        candidate_details.add(home_txt); candidate_details.add(home_field);
        candidate_details.add(mobile_txt); candidate_details.add(mobile_field);
        candidate_details.add(email_txt); candidate_details.add(email_field);
        candidate_details.add(owncar_txt); candidate_details.add(owncar_field);

        JLabel Description_txt = new JLabel("Description: ");
        Description_field.setEditable(false);
        JScrollPane pane = new JScrollPane(Description_field);
        JPanel Description_panel = new JPanel();
        Description_panel.setLayout(new BoxLayout(Description_panel, BoxLayout.X_AXIS));
        Description_panel.add(Description_txt);
        Description_panel.add(Description_field);

        Description_panel.setBounds(725, 310, 300, 100);
        viewCandidatePanel.add(main_panel);
        viewCandidatePanel.add(candidate_details);
        viewCandidatePanel.add(Description_panel);
        viewCand_backbtn.addActionListener(this);

        viewCandidatePanel.hide();

        add(viewCandidatePanel);



    }

    void setRegisterCandidate(){
        RegisterPanel = new JPanel(null);
        RegisterPanel.setBounds(0, 0, this.getWidth(), this.getHeight());

        JLabel id_txt= new JLabel("ID: "),firstname_txt= new JLabel("Firtname: "),Lastname_txt= new JLabel("Lastname: "),department_txt= new JLabel("department: "), gender_txt = new JLabel("Gender:"), address_txt = new JLabel("Address:"), post_held_txt= new JLabel("Post held:"), actual_workplace_txt =new JLabel("Actual workplace:"), office_txt = new JLabel("Office no:"), home_txt = new JLabel("Home no:"), mobile_txt = new JLabel("Mobile no:"), email_txt =new JLabel("Email: "), owncar_txt= new JLabel("Own car:");
        JLabel id_field= new JLabel("no1"),firstname_field= new JLabel("Name 1"),Lastname_field= new JLabel("Lastname 1"),department_field= new JLabel("Department1"), gender_field = new JLabel("M"), address_field= new JLabel("Railway Road"), post_held_field= new JLabel("Manager"), actual_workplace_field = new JLabel("Port Louis"), office_field = new JLabel("59049437"), home_field = new JLabel("6223595"), mobile_field = new JLabel("59049437"), email_field = new JLabel("abhaybandhu"), owncar_field = new JLabel("NO");


        JLabel registerFont = new JLabel("Register Candidates");
        registerFont.setFont(new Font("times new roman", Font.BOLD,30));
        registerFont.setBounds(450,10,500,50);

        JPanel main_panel = new JPanel(new GridLayout(2,1,10,10)), table_panel = new JPanel(new GridLayout(2,1,10,10)), btn_panel = new JPanel();
        main_panel.setBounds(25,200,600,500);
        registerTable = new JTable();
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(registerTable);

        model = new DefaultTableModel()
        {
            @Override public Class<?> getColumnClass(int column){
                switch (column) {
                    
                    case 4:
                       return Boolean.class;
                
                    default:
                        return String.class;
                }
            }
            @Override public boolean isCellEditable(int row, int col){
                return (col == 4);
            }
        };

        registerTable.setModel(model);

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Department");
        model.addColumn("Region");
        model.addColumn("Check");

        table_panel.add(scroll);

        //fake data
        for (int i = 0; i < 7; ++i){
            model.addRow(new Object[0]);
            model.setValueAt(Integer.toString(i+1),i , 0);
            model.setValueAt("Name " + (i+1),i , 1);
            model.setValueAt("Department"+ (i+1), i, 2);
            model.setValueAt("Region"+ (i+1), i, 3);
            model.setValueAt(false, i,4);
        }

        JTextArea Description_field = new JTextArea("This is a description text dfjvfjdhvfdjf dhbfvjdvdhfbdhgdvbfgdbjhgdvfdf");

        ListSelectionModel select= registerTable.getSelectionModel();  
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        select.addListSelectionListener(new ListSelectionListener() {  
            public void valueChanged(ListSelectionEvent e) {  
                String id_str = "";  
                int row = registerTable.getSelectedRow();    
                if (registerTable.getSelectedColumn() == 4) return;
                // rowSelected = row;
                // columnSelected =viewCandidatesAreaTable.getSelectedColumn();
                id_str = (String) registerTable.getValueAt(row, 0);  
                Object[] data = Admin.getCandidateInfo(Integer.parseInt(id_str));

                if (data.length ==0 || data == null){
                    return;
                }
                
                id_field.setText(String.valueOf(data[0]));
                firstname_field.setText(String.valueOf(data[1]));
                Lastname_field.setText(String.valueOf(data[2]));
                department_field.setText(String.valueOf(data[3]));
                String gen = (String.valueOf(data[4]).equals("M"))? "Male": "Female";
                gender_field.setText(gen);
                address_field.setText(String.valueOf(data[5]));
                post_held_field.setText(String.valueOf(data[6]));
                actual_workplace_field.setText(String.valueOf(data[7]));
                office_field.setText(String.valueOf(data[8]));
                home_field.setText(String.valueOf(data[9]));
                mobile_field.setText(String.valueOf(data[10]));
                email_field.setText(String.valueOf(data[11]));
                String ownCar = (Integer.parseInt(String.valueOf(data[12])) == 0)? "NO" : "YES";
                owncar_field.setText(ownCar);
                Description_field.setText(String.valueOf(data[13]));
                
                
            }       
        });
        Admit_btn= new JButton("Admit candidate");
        delete_btn= new JButton("Delete candidate");
        register_back_btn = new JButton("Back home");

        Admit_btn.addActionListener(this);
        delete_btn.addActionListener(this);
        register_back_btn.addActionListener(this);

        btn_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 50 ,50));
        btn_panel.add(Admit_btn);
        btn_panel.add(delete_btn);
        btn_panel.add(register_back_btn);

        main_panel.add(table_panel);
        main_panel.add(btn_panel);

        JPanel candidate_details = new JPanel(new GridLayout(13,2,10,10));
        candidate_details.setBounds(725, 100, 400, 300);

        candidate_details.add(id_txt); candidate_details.add(id_field);
        candidate_details.add(firstname_txt); candidate_details.add(firstname_field);
        candidate_details.add(Lastname_txt); candidate_details.add(Lastname_field);
        candidate_details.add(department_txt); candidate_details.add(department_field);
        candidate_details.add(gender_txt); candidate_details.add(gender_field);
        candidate_details.add(address_txt); candidate_details.add(address_field);
        candidate_details.add(post_held_txt); candidate_details.add(post_held_field);
        candidate_details.add(actual_workplace_txt); candidate_details.add(actual_workplace_field);
        candidate_details.add(office_txt); candidate_details.add(office_field);
        candidate_details.add(home_txt); candidate_details.add(home_field);
        candidate_details.add(mobile_txt); candidate_details.add(mobile_field);
        candidate_details.add(email_txt); candidate_details.add(email_field);
        candidate_details.add(owncar_txt); candidate_details.add(owncar_field);

        JLabel Description_txt = new JLabel("Description: ");
        Description_field.setEditable(false);
        JScrollPane pane = new JScrollPane(Description_field);
        JPanel Description_panel = new JPanel();
        Description_panel.setLayout(new BoxLayout(Description_panel, BoxLayout.X_AXIS));
        Description_panel.add(Description_txt);
        Description_panel.add(Description_field);

        Description_panel.setBounds(725, 410, 300, 100);

        RegisterPanel.add(registerFont);
        RegisterPanel.add(main_panel);
        RegisterPanel.add(candidate_details);
        RegisterPanel.add(Description_panel);

        RegisterPanel.hide();
        add(RegisterPanel);


    }

    void setResult(){
        ResultPanel = new JPanel(null);
        ResultPanel.setBounds(0, 0, this.getWidth(), this.getHeight());

        JLabel resultFont = new JLabel("Result of election Page");
        resultFont.setFont(new Font("times new roman", Font.BOLD,30));
        resultFont.setBounds(450,10,500,50);

        JPanel mainPanel = new JPanel(new GridLayout(2,1,10,10));

        //
        final String[][] data = {
            {"1", "John", "MSM", "Port Louis","20"},
            {"2", "Noa", "MMM", "Port Louis", "25"},
            {"3", "Hansan", "MSM", "Savanne", "12"},
            {"4", "Vode", "MMM", "Port Louis", "8"}
        };

        Resultmodel = new DefaultTableModel(data, columnResult)
        {
          public boolean isCellEditable(int row, int column)
          {
            return false;
          }
        };
        
        resultTable = new JTable(Resultmodel);
        resultTable.setEnabled(false);
        JScrollPane scroll = new JScrollPane(resultTable);
        

        JPanel btn_Panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        resultBackBtn = new JButton("Back to home");
        btn_Panel.add(resultBackBtn);

        mainPanel.add(scroll);
        mainPanel.add(btn_Panel);
        mainPanel.setBounds(331,200,500,300);
        resultBackBtn.addActionListener(this);
        ResultPanel.add(resultFont);
        ResultPanel.add(mainPanel);
        ResultPanel.hide();
        add(ResultPanel);
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
                //JOptionPane.showMessageDialog(this, "Error:Election is closed cannot generate", "Election Close",JOptionPane.WARNING_MESSAGE);
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
                //JOptionPane.showMessageDialog(this, "Error: There is no recently Closed election", "Election CLose",JOptionPane.WARNING_MESSAGE);
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

    void closeRecentlyClosed(){
        try {
            String sql ="Update Election Set RecentlyClosed =0  ;";

            Statement stmt = DBConnection.conn.createStatement();
            int rs = stmt.executeUpdate(sql);
            
            stmt.close();
        } catch (Exception e) {
            election_id =0;
            JOptionPane.showMessageDialog(null, "Error: Access Denied." +e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

    }
    
    int[] getCandidateId(){
        ArrayList<Integer> id = new ArrayList<Integer>();
        for (int i = 0; i< registerTable.getRowCount(); ++i){
            Boolean check = Boolean.valueOf(registerTable.getValueAt(i, 4).toString());
            if (check){
                id.add(Integer.parseInt(registerTable.getValueAt(i, 0).toString()));
            }
        }

        if (id.size()== 0){
            JOptionPane.showMessageDialog(null, "Error: Please at least choose at least a candidate to vote admit", "Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }

        int[] candidate_id= new int[id.size()];
        for (int i = 0; i < id.size(); ++i)
            candidate_id[i] = id.get(i);

        return candidate_id;
    }
}

