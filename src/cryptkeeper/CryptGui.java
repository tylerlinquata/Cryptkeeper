package cryptkeeper;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.JTextField;
import java.awt.ComponentOrientation;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by u1068832 on 7/20/2017.
 */
public class CryptGui extends JFrame {

    private JPanel contentPane;
    private JTextField txtEnterFilepathHere;
    private JTextField txtEnterKeyHere;
    private JButton btnEncrypt_1;
    private JButton btnDecrypt_1;
    private JLabel lblSelectFileTo;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CryptKeepergui frame = new CryptKeepergui();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public CryptKeepergui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panelMenuBar = new JPanel();
        panelMenuBar.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        panelMenuBar.setBounds(0, 0, 450, 50);
        contentPane.add(panelMenuBar);

        JPanel panelSelectFile = new JPanel();
        panelSelectFile.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        panelSelectFile.setBounds(0, 50, 450, 50);
        contentPane.add(panelSelectFile);

        JPanel panelStatus = new JPanel();
        panelStatus.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        panelStatus.setPreferredSize(new Dimension(450, 100));
        panelStatus.setBounds(0, 225, 450, 75);
        contentPane.add(panelStatus);
        panelStatus.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JPanel panelEncrypt = new JPanel();
        panelEncrypt.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        panelEncrypt.setPreferredSize(new Dimension(450, 100));
        panelEncrypt.setBounds(0, 150, 450, 75);
        contentPane.add(panelEncrypt);
        panelEncrypt.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JPanel panelKey = new JPanel();
        panelKey.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        panelKey.setBounds(0, 100, 450, 50);
        contentPane.add(panelKey);

        JLabel lblLogoHere = new JLabel("LOGO HERE");
        lblLogoHere.setHorizontalAlignment(SwingConstants.LEFT);
        lblLogoHere.setPreferredSize(new Dimension(50, 25));
        lblLogoHere.setOpaque(true);
        lblLogoHere.setBackground(Color.WHITE);
        panelMenuBar.add(lblLogoHere);

        lblSelectFileTo = new JLabel("Select File to Encrypt");
        lblSelectFileTo.setOpaque(true);
        lblSelectFileTo.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        lblSelectFileTo.setPreferredSize(new Dimension(150, 50));
        panelSelectFile.add(lblSelectFileTo);

        txtEnterFilepathHere = new JTextField();
        txtEnterFilepathHere.setPreferredSize(new Dimension(100, 25));
        txtEnterFilepathHere.setText("Enter Filepath Here");
        panelSelectFile.add(txtEnterFilepathHere);
        txtEnterFilepathHere.setColumns(15);


        JLabel lblEntergenerateKey = new JLabel("Enter/Generate Key");
        lblEntergenerateKey.setPreferredSize(new Dimension(150, 50));
        panelKey.add(lblEntergenerateKey);

        txtEnterKeyHere = new JTextField();
        txtEnterKeyHere.setPreferredSize(new Dimension(100, 25));
        txtEnterKeyHere.setText("Enter key here");
        panelKey.add(txtEnterKeyHere);
        txtEnterKeyHere.setColumns(15);

        JLabel lblStatus = new JLabel("Success/Failure");
        panelStatus.add(lblStatus);

        JButton btnBrowse = new JButton("Browse");
        panelSelectFile.add(btnBrowse);
        btnBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File("c:\\"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton btnEncrypt = new JButton("Encrypt");
        btnEncrypt.setBackground(UIManager.getColor("Button.darkShadow"));
        btnEncrypt.setPreferredSize(new Dimension(100, 25));
        panelMenuBar.add(btnEncrypt);
        btnEncrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelEncrypt.remove(btnDecrypt_1);
                panelEncrypt.repaint();
                panelEncrypt.add(btnEncrypt_1);
                lblSelectFileTo.setText("Select File to Encrypt");
                panelEncrypt.revalidate();
            }
        });

        JButton btnDecrypt = new JButton("Decrypt");
        btnDecrypt.setPreferredSize(new Dimension(100, 25));
        panelMenuBar.add(btnDecrypt);
        btnDecrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelEncrypt.remove(btnEncrypt_1);
                panelEncrypt.repaint();
                panelEncrypt.add(btnDecrypt_1);
                lblSelectFileTo.setText("Select File to Decrypt");
                panelEncrypt.revalidate();
            }
        });

        JButton btnAbout = new JButton("About");
        btnAbout.setPreferredSize(new Dimension(100, 25));
        panelMenuBar.add(btnAbout);
        btnAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton btnGenerate = new JButton("Generate");
        panelKey.add(btnGenerate);
        btnGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnEncrypt_1 = new JButton("Encrypt");
        btnEncrypt_1.setPreferredSize(new Dimension(100, 50));
        panelEncrypt.add(btnEncrypt_1);
        btnEncrypt_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        btnDecrypt_1 = new JButton("Decrypt");
        btnDecrypt_1.setPreferredSize(new Dimension(100,50));
        btnDecrypt_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });


    }
}
