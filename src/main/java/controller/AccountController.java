package controller;

import Dao.AccountServiceDao;
import Dao.AccountServiceDaoImpl;
import model.Account;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.util.Base64;

import view.MainJFrame;

public class AccountController {

    private JFrame jFrame;
    private JTextField jtfUsername;
    private JPasswordField jpwfPassword;
    private JLabel jllbMsg;
    private JButton btnLogin;
    private JCheckBox rememberMeCheckBox;
    private AccountServiceDao accountServiceDao = null;

    private static final String CREDENTIALS_DIR = System.getProperty("user.home") + File.separator + "login";
    private static final String CREDENTIALS_FILE = CREDENTIALS_DIR + File.separator + "credentials.txt";
    private static final String KEY_FILE = CREDENTIALS_DIR + File.separator + "key.txt";
    private SecretKey secretKey;

    public AccountController(JFrame jFrame, JTextField jtfUsername, JPasswordField jpwfPassword, JLabel jllbMsg, JButton btnLogin, JCheckBox rememberMeCheckBox) {
        this.jFrame = jFrame;
        this.jtfUsername = jtfUsername;
        this.jpwfPassword = jpwfPassword;
        this.jllbMsg = jllbMsg;
        this.btnLogin = btnLogin;
        this.rememberMeCheckBox = rememberMeCheckBox;

        accountServiceDao = new AccountServiceDaoImpl();

        createCredentialsDirIfNeeded();
        generateSecretKey();
        loadCredentials();
    }

    public void setEvent() {
        ActionListener loginAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        };
        btnLogin.addActionListener(loginAction);

        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnLogin.setBackground(new Color(76, 175, 80));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(new Color(96, 100, 191));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (jtfUsername.getText().isEmpty() || jpwfPassword.getPassword().length == 0) {
                    jllbMsg.setText("Vui lòng nhập đầy đủ thông tin");
                } else {
                    jllbMsg.setText("");
                }
            }
        });

        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        };
        jtfUsername.addKeyListener(enterKeyListener);
        jpwfPassword.addKeyListener(enterKeyListener);
    }

    private void handleLogin() {
        String username = jtfUsername.getText();
        char[] passwordChars = jpwfPassword.getPassword();
        String password = new String(passwordChars);

        if (username.isEmpty() || password.isEmpty()) {
            jllbMsg.setText("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        Account account = accountServiceDao.login(username, password);
        if (account == null) {
            jllbMsg.setText("Tên đăng nhập hoặc mật khẩu không chính xác");
        } else {
            if (rememberMeCheckBox.isSelected()) {
                saveCredentials(username, password);
            } else {
                clearCredentials();
            }
            jFrame.dispose();
            MainJFrame mainJFrame = new MainJFrame();
            mainJFrame.setTitle("Quản lý Shipper");
            mainJFrame.setLocationRelativeTo(null);
            mainJFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            mainJFrame.setVisible(true);
        }

        java.util.Arrays.fill(passwordChars, ' ');
    }

    private void saveCredentials(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE))) {
            String encryptedUsername = encrypt(username);
            String encryptedPassword = encrypt(password);
            writer.write(encryptedUsername);
            writer.newLine();
            writer.write(encryptedPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCredentials() {
        File file = new File(CREDENTIALS_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String encryptedUsername = reader.readLine();
                String encryptedPassword = reader.readLine();
                String username = decrypt(encryptedUsername);
                String password = decrypt(encryptedPassword);
                jtfUsername.setText(username);
                jpwfPassword.setText(password);
                rememberMeCheckBox.setSelected(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearCredentials() {
        File file = new File(CREDENTIALS_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    private void createCredentialsDirIfNeeded() {
        File dir = new File(CREDENTIALS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void generateSecretKey() {
        try {
            File keyFile = new File(KEY_FILE);
            if (keyFile.exists()) {
                byte[] keyBytes = new byte[(int) keyFile.length()];
                try (FileInputStream fis = new FileInputStream(keyFile)) {
                    fis.read(keyBytes);
                    secretKey = new SecretKeySpec(keyBytes, "AES");
                }
            } else {
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                keyGen.init(128);
                secretKey = keyGen.generateKey();
                try (FileOutputStream fos = new FileOutputStream(keyFile)) {
                    fos.write(secretKey.getEncoded());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
