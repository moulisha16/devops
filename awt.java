import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEve
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.Color;
class BackgroundPan extends JPanel {
    private Image backgroundImage;

    public BackgroundPan(String filePath) {
        try {
            backgroundImage = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

public class awt extends JFrame {

    awt() {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "system", "mahitha");
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from userinfo");

            List<String[]> userData = new ArrayList<>();
            while (rs.next()) {
                String[] user = {
                        rs.getString("username"), rs.getString("userid"), rs.getString("score"), rs.getString("wins"), rs.getString("loses"), rs.getString("ach"), rs.getString("password"), rs.getString("gid"), rs.getString("name")
                };
                userData.add(user);
            }
            String imagePath = "C:\\Users\\HP\\Downloads\\db.png";

            BackgroundPan backgroundPanel = new BackgroundPan(imagePath);
            backgroundPanel.setLayout(new BorderLayout());
            add(backgroundPanel);

            JLabel jLabel1 = new JLabel("GAMING ZONE");
            jLabel1.setFont(new Font("Times New Roman", Font.BOLD, 36));
            jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel1.setForeground(Color.yellow);
            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BorderLayout());
            titlePanel.add(jLabel1, BorderLayout.NORTH);
            titlePanel.setOpaque(false);

            JPanel formPanel = new JPanel();
            formPanel.setLayout(null);

            Label l = new Label("ENTER USER NAME OR USER ID :");
            Label l1 = new Label("ENTER PASSWORD :");
            Label l2 = new Label("ENTER GAME NAME OR GAME ID");
            TextField tf = new TextField();
            JPasswordField tf1 = new JPasswordField();
            TextField tf2 = new TextField();
            Button b = new Button("Submit");
            TextArea ta = new TextArea();

            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {


                    formPanel.add(ta);
                    String input = tf.getText();
                    String p = new String(tf1.getPassword());
                    String id = tf2.getText();
                    boolean found = false;
                    for (String[] user : userData) {
                        if ((user[0].equalsIgnoreCase(input) || user[1].equals(input) && user[6].equalsIgnoreCase(p))
                                && (user[7].equalsIgnoreCase(id) || user[8].equalsIgnoreCase(id))) {
                            ta.setText("*\n\n USERNAME : " + user[0] + "\n\n"
                                    + "USER ID : " + user[1] + "\n\n"
                                    + "GAME ID :" + user[7] + "\n\n"
                                    + "GAME NAME :" + user[8] + "\n\n"
                                    + "SCORE : " + user[2] + "\n\n"
                                    + "NO. OF WINS : " + user[3] + "\n\n"
                                    + "NO. OF LOSES : " + user[4] + "\n\n"
                                    + "ACHIEVEMENTS : " + user[5] + "\n\n"
                                    + "*");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        ta.setText("User not found.");
                    }
                }
            });

            l.setBounds(670, 80, 200, 20);
            tf.setBounds(670, 110, 200, 20);
            l1.setBounds(670, 140, 200, 20);
            tf1.setBounds(670, 170, 200, 20);
            l2.setBounds(670, 200, 200, 20);
            tf2.setBounds(670, 230, 200, 20);
            b.setBounds(670, 260, 60, 20);
            ta.setBounds(600, 330, 400, 300);
            ta.setBackground(Color.LIGHT_GRAY);
            formPanel.add(l);
            formPanel.add(tf);
            formPanel.add(l1);
            formPanel.add(tf1);
            formPanel.add(l2);
            formPanel.add(tf2);
            formPanel.add(b);
            formPanel.setBackground(Color.LIGHT_GRAY);
            formPanel.setOpaque(false);

            backgroundPanel.add(titlePanel, BorderLayout.NORTH);
            backgroundPanel.add(formPanel, BorderLayout.CENTER);

            setSize(2000, 2000);
            setVisible(true);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    dispose();
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        new awt();
    }
}