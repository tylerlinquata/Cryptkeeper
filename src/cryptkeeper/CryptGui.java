package cryptkeeper;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by u1068832 on 7/20/2017.
 */
public class CryptGui extends JFrame {

    //pnl
    private JPanel contentPane;
    private JPanel panelMenuBar;
    private JPanel panelEncrypt;
    private JPanel panelStatus;
    private JPanel panelSelectFile;
    private JPanel panelKey;
    //txt field
    private JTextField txtEnterFilepathHere;
    private JPasswordField txtEnterKeyHere;
    //btn
    private JButton btnBrowse;
    private JRadioButton btnEncrypt;
    private JRadioButton btnDecrypt;
    private JButton btnAbout;
    //private JButton btnGenerate;
    private JButton btnEncrypt_1;
    private JButton btnDecrypt_1;
    //lbls
    private JLabel lblLogoHere;
    private JLabel lblEntergenerateKey;
    private JLabel lblSelectFileTo;
    private JLabel lblStatus;
    //file chooser
    private JFileChooser chooser;
    private String chooserTitle = "Select a file";


    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    CryptGui frame = new CryptGui();
                    frame.setVisible(true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public CryptGui()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setTitle("CryptKeeper File Encryption");
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png"));
        setIconImage(image);

        //build gui
        initPnl();
        initLbl();
        initTxtField();
        initBtn();
    }

    private void initPnl()
    {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panelMenuBar = new JPanel();
        panelMenuBar.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        panelMenuBar.setBounds(0, 0, 450, 50);
        contentPane.add(panelMenuBar);

        panelSelectFile = new JPanel();
        panelSelectFile.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        panelSelectFile.setBounds(0, 50, 450, 50);
        contentPane.add(panelSelectFile);

        panelStatus = new JPanel();
        panelStatus.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        panelStatus.setPreferredSize(new Dimension(450, 100));
        panelStatus.setBounds(0, 225, 450, 75);
        contentPane.add(panelStatus);
        panelStatus.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        panelEncrypt = new JPanel();
        panelEncrypt.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        panelEncrypt.setPreferredSize(new Dimension(450, 100));
        panelEncrypt.setBounds(0, 150, 450, 75);
        contentPane.add(panelEncrypt);
        panelEncrypt.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelEncrypt.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        panelKey = new JPanel();
        panelKey.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        panelKey.setBounds(0, 100, 450, 50);
        contentPane.add(panelKey);
    }

    private void initLbl()
    {
        lblLogoHere = new JLabel();
        lblLogoHere.setHorizontalAlignment(SwingConstants.LEFT);
        lblLogoHere.setPreferredSize(new Dimension(120, 40));
        lblLogoHere.setOpaque(true);
        lblLogoHere.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        lblLogoHere.setIcon(new ImageIcon(this.getClass().getResource("c-logo.png")));
        panelMenuBar.add(lblLogoHere);

        lblSelectFileTo = new JLabel("Select File to Encrypt");
        lblSelectFileTo.setOpaque(true);
        lblSelectFileTo.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        lblSelectFileTo.setPreferredSize(new Dimension(150, 50));
        panelSelectFile.add(lblSelectFileTo);

        lblEntergenerateKey = new JLabel("Enter/Generate Key");
        lblEntergenerateKey.setPreferredSize(new Dimension(150, 50));
        panelKey.add(lblEntergenerateKey);

        lblStatus = new JLabel();
        panelStatus.add(lblStatus);
    }

    private void initTxtField()
    {
        txtEnterFilepathHere = new JTextField();
        txtEnterFilepathHere.setPreferredSize(new Dimension(100, 25));
        panelSelectFile.add(txtEnterFilepathHere);
        txtEnterFilepathHere.setColumns(15);

        txtEnterKeyHere = new JPasswordField();
        txtEnterKeyHere.setPreferredSize(new Dimension(100, 25));
        panelKey.add(txtEnterKeyHere);
        txtEnterKeyHere.setColumns(15);
    }

    private void initBtn()
    {
        //browse
        btnBrowse = new JButton("Browse");
        panelSelectFile.add(btnBrowse);
        btnBrowse.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle(chooserTitle);
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                if (chooser.showOpenDialog(contentPane) == JFileChooser.APPROVE_OPTION)
                {
                    txtEnterFilepathHere.setText(chooser.getSelectedFile().toString()); //update txtbox with selection
                }
                else
                {
                    System.out.println("No Selection ");
                }
            }
        });

        ButtonGroup btnGroup = new ButtonGroup();

        btnEncrypt = new JRadioButton("Encrypt");
        btnEncrypt.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        btnEncrypt.setPreferredSize(new Dimension(100, 25));
        btnEncrypt.setSelected(true);
        btnGroup.add(btnEncrypt);
        btnEncrypt.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                panelEncrypt.remove(btnDecrypt_1);
                panelEncrypt.repaint();
                panelEncrypt.add(btnEncrypt_1);
                lblSelectFileTo.setText("Select File to Encrypt");
                panelEncrypt.revalidate();
                clearInput();
            }
        });


        btnDecrypt = new JRadioButton("Decrypt");
        btnDecrypt.setBackground(UIManager.getColor("Button.disabledToolBarBorderBackground"));
        btnDecrypt.setPreferredSize(new Dimension(100, 25));
        btnGroup.add(btnDecrypt);
        btnDecrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelEncrypt.remove(btnEncrypt_1);
                panelEncrypt.repaint();
                panelEncrypt.add(btnDecrypt_1);
                lblSelectFileTo.setText("Select File to Decrypt");
                panelEncrypt.revalidate();
                clearInput();
            }
        });
        panelMenuBar.add(btnEncrypt);
        panelMenuBar.add(btnDecrypt);


        btnAbout = new JButton("About");
        btnAbout.setPreferredSize(new Dimension(100, 25));
        btnAbout.setMargin(new Insets(0, 0, 0, 5));
        panelMenuBar.add(btnAbout);
        btnAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                sb.append("CryptKeeper is a file encryption and decryption utility.\n");
                sb.append("Team Loading... 2017");
                JOptionPane.showMessageDialog(btnAbout, sb.toString(), "CryptKeeper - About", 0, new ImageIcon(getClass().getResource("icon.png")));
            }
        });

//        btnGenerate = new JButton("Generate");
//        panelKey.add(btnGenerate);
//        btnGenerate.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });

        btnEncrypt_1 = new JButton("Encrypt");
        btnEncrypt_1.setPreferredSize(new Dimension(100, 50));
        panelEncrypt.add(btnEncrypt_1);
        btnEncrypt_1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
               cryptKeep(true);
            }
        });

        btnDecrypt_1 = new JButton("Decrypt");
        btnDecrypt_1.setPreferredSize(new Dimension(100,50));
        btnDecrypt_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cryptKeep(false); //decrypt
            }
        });
    }

    private void clearInput()
    {
        txtEnterFilepathHere.setText("");
        txtEnterKeyHere.setText("");
    }

    private void cryptKeep(boolean encrypt)
    {
        char[] pw = txtEnterKeyHere.getPassword();
        String path = txtEnterFilepathHere.getText();
        if(path != "" && pw.length > 0)
        {
            CryptKeeper ck = new CryptKeeper();
            String status = "";
            try {
                //set the status message text with outcome
                if(encrypt)
                {
                    status = ck.Encrypt(pw, path, true);
                }
                else
                {
                    //decrypt
                    status = ck.Decrypt(pw, path);
                }
            }
            catch(Exception ex)
            {
                System.out.println(path);
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
            finally
            {
                lblStatus.setText(status);
            }
        }
    }

}
