import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

class CustomeBorder extends AbstractBorder{
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        // TODO Auto-generated method stubs
        super.paintBorder(c, g, x, y, width, height);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(12));
        g2d.setColor(Color.WHITE);
        g2d.drawRoundRect(x, y, width-1, height-1, 25, 25);
    }   
}

public class Placeholder implements FocusListener{
    private JTextField field;
    private String text;

    Placeholder(String text, int size){
        this.text = text;
        field = new JTextField(text, size);
        field.setEnabled(true);
        field.setRequestFocusEnabled(true);
        field.addFocusListener(this);
        field.setFont(new Font("Arial", Font.PLAIN, 15));
        //field.setBackground(Color.White);
        /*field.setBorder(BorderFactory.createCompoundBorder(
            new CustomeBorder(),
            new EmptyBorder(new Insets(15,25,15,25))
        ));*/
    }

    public void focusGained(FocusEvent e){
        if (field.getText().trim().toLowerCase().equals(text.toLowerCase())){
            field.setText("");
        }
    }

    public void focusLost(FocusEvent e){
        if (field.getText().trim().equals("")){
            field.setText(text);
        }
    }
    
    public JTextField getObject(){
        return field;
    }
}
