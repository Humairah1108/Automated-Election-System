import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class CandidateProfile extends JFrame {
	CandidateProfile(){
		//Letting user know which window they are in by displaying a page title
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JLabel candidateProf = new JLabel("Candidate Profile");
		candidateProf.setFont(new Font("times new roman", Font.BOLD,30));
		setLayout(null);
		candidateProf.setBounds(450,10,500,50);
		add(candidateProf);
		
		JPanel bigpanel = new JPanel();
		bigpanel.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		bigpanel.setBounds(10,70,1100,620);
		bigpanel.setLayout(new BoxLayout(bigpanel, BoxLayout.Y_AXIS));
		add(bigpanel);
		
		//main jpanel to include other jpanels
		JPanel jmain = new JPanel();
		jmain.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		bigpanel.add(jmain);
		
		jmain.setLayout(new BoxLayout(jmain, BoxLayout.Y_AXIS));
		jmain.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		JPanel candidate = new JPanel();
		candidate.setLayout(new GridLayout(1,2,5,10));
		jmain.add(candidate);
		
		//Personal Details
		//Creating a big Panel to include other small panels
		JPanel persoDet = new JPanel();
		persoDet.setLayout(new BoxLayout(persoDet, BoxLayout.Y_AXIS));
		candidate.add(persoDet);
		
		//Creating a jpanel inside persoDet
		JPanel details = new JPanel();
		details.setBorder(BorderFactory.createTitledBorder(null, "Particulars of Applicant", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		persoDet.add(details);
		
		//this panel contains 5 rows and 2 columms
		details.setLayout(new GridLayout(6,2,5,10));
		
		JLabel nic = new JLabel("1.1 National Identity Card No. :");
		JTextField nictxt = new JTextField();
		details.add(nic);
		details.add(nictxt);
		
		JLabel title = new JLabel("1.2 Title (Mr/Mrs/Ms) :");
		JTextField titletxt = new JTextField();
		details.add(title);
		details.add(titletxt);
		
		JLabel surname = new JLabel("1.3 Surname:");
		JTextField surnametxt = new JTextField();
		details.add(surname);
		details.add(surnametxt);
		
		JLabel onames = new JLabel("1.4 Other Name(s) in full:");
		JTextField onamestxt = new JTextField();
		details.add(onames);
		details.add(onamestxt);
		
		JLabel maidenname = new JLabel("1.5 Maiden Name (Where Applicable):");
		JTextField maidennametxt = new JTextField();
		details.add(maidenname);
		details.add(maidennametxt);
		
		JLabel addr = new JLabel("1.6 Permanent Residential Address:");
		JTextField addrtxt = new JTextField();
		details.add(addr);
		details.add(addrtxt);
		//---------------------------------------------------------------------------
		//creating small JPanel for contact details
		JPanel contactDet = new JPanel();
		contactDet.setBorder(BorderFactory.createTitledBorder(null, "Particulars of Present Occupation", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		persoDet.add(contactDet);
		contactDet.setLayout(new GridLayout(4,2,5,30));  
		
		JLabel postHeld = new JLabel("2.1 Post Held :");
		JTextField postHeldtxt = new JTextField();
		contactDet.add(postHeld);
		contactDet.add(postHeldtxt);
		
		JLabel paysiteCode = new JLabel("2.2 Paysite Code No. :");
		JTextField paysiteCodetxt = new JTextField();
		contactDet.add(paysiteCode);
		contactDet.add(paysiteCodetxt);
		
		JLabel mindep = new JLabel("2.3 Ministry/Department :");
		JTextField mindeptxt = new JTextField();
		contactDet.add(mindep);
		contactDet.add(mindeptxt);
		
		JLabel num = new JLabel("2.4 Actual workplace :");
		JTextField numtxt = new JTextField();
		contactDet.add(num);
		contactDet.add(numtxt);
		
		//----------------------------------------------------
		//second big panel
		JPanel otherDetails = new JPanel();
		candidate.add(otherDetails);
		otherDetails.setLayout(new BoxLayout(otherDetails, BoxLayout.Y_AXIS));
		
		JPanel general = new JPanel();
		general.setBorder(BorderFactory.createTitledBorder(null, "General Information", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		general.setLayout(new GridLayout(5,2,5,10));  
		otherDetails.add(general);
		
		JLabel office = new JLabel("3.1 Office No. :");
		JTextField officetxt = new JTextField();
		general.add(office);
		general.add(officetxt);
		
		JLabel home = new JLabel("3.2 Home No. :");
		JTextField hometxt = new JTextField();
		general.add(home);
		general.add(hometxt);
	
		JLabel mobile = new JLabel("3.3 Mobile No. :");
		JTextField mobiletxt = new JTextField();
		general.add(mobile);
		general.add(mobiletxt);
		
		JLabel email = new JLabel("3.4 Email Address :");
		JTextField emailtxt = new JTextField();
		
		general.add(email);
		general.add(emailtxt);
		
		JLabel owncar = new JLabel("3.5 Do you own a car(Y/N) :");
		JTextField owncartxt = new JTextField();
		
		general.add(owncar);
		general.add(owncartxt);
		
		//-------------------------------------------------------------
		JPanel decl = new JPanel();
		decl.setBorder(BorderFactory.createTitledBorder(null, "Declaration of Applicant", TitledBorder.LEFT, TitledBorder.TOP, new Font("calibri",Font.BOLD,16), Color.BLACK));
		decl.setLayout(new GridLayout(9,1,10,10));  
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
		
		//------------------------------------------------------------------------------------------------
		JPanel acknowledgement = new JPanel();
		acknowledgement.setLayout(new GridLayout(2,1,5,20));
		jmain.add(acknowledgement);
		
		acknowledgement.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		
		JPanel sentence = new JPanel();
		acknowledgement.add(sentence);
		sentence.setLayout(new GridLayout(1,1,5,10));  
		JLabel l2 = new JLabel("4.2	 I also undertake to inform the Office of the Electoral Commissioner immediately in case I no longer satisfy any  change in my particulars or status.");
		sentence.add(l2);
		
		JPanel datesig = new JPanel();
		datesig.setLayout(new GridLayout(1,4,100,100)); 
		acknowledgement.add(datesig);
		
		JLabel date = new JLabel("Date :");
		JTextField datetxt = new JTextField();
		
		datesig.add(date);
		datesig.add(datetxt);
		
		JLabel signature = new JLabel("Signature :");
		JTextField signaturetxt = new JTextField();
		
		datesig.add(signature);
		datesig.add(signaturetxt);
		//-----------------------------------------------------------------
		
		JPanel buttons = new JPanel();
		buttons.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		bigpanel.add(buttons);
		JButton back = new JButton("Back");
		JButton submit = new JButton("Submit");
		buttons.add(back, BorderLayout.WEST);
		buttons.add(submit, BorderLayout.EAST);
		
		this.setSize(1150,750);
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new CandidateProfile();
	}
}
