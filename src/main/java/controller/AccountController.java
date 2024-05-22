package controller;

import Dao.AccountServiceDao;
import Dao.AccountServiceDaoImpl;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import model.Account;
import view.MainJFrame;

public class AccountController {

    private JFrame jFrame;
    private JTextField jtfUsername;
    private JPasswordField jpwfPassword;
    private JLabel jllbMsg;
    private JButton btnLogin;
    private AccountServiceDao accountServiceDao = null;

    public AccountController(JFrame jFrame, JTextField jtfUsername, JPasswordField jpwfPassword, JLabel jllbMsg, JButton btnLogin) {
        this.jFrame = jFrame;
        this.jtfUsername = jtfUsername;
        this.jpwfPassword = jpwfPassword;
        this.jllbMsg = jllbMsg;
        this.btnLogin = btnLogin;

        accountServiceDao = new AccountServiceDaoImpl();
    }

    public void setEvent() {
        // Thêm sự kiện cho nút đăng nhập
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        // Thêm sự kiện cho ô mật khẩu
        jpwfPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!String.valueOf(jpwfPassword.getPassword()).isEmpty()) {
                    performLogin();
                } else {
                    jllbMsg.setText("Vui lòng nhập mật khẩu");
                }
            }
        });

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
//                    jllbMsg.setText("");
                }
            }
        });
    }

    // Hàm thực hiện đăng nhập
    private void performLogin() {
        String username = jtfUsername.getText();
        char[] passwordChars = jpwfPassword.getPassword();
        String password = new String(passwordChars);

        System.out.println(username);
        System.out.println(password);
        
        if (username.isEmpty() || password.isEmpty()) {
            jllbMsg.setText("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        Account account = accountServiceDao.login(username, password);
        if (account == null) {
            jllbMsg.setText("Tên đăng nhập hoặc mật khẩu không chính xác");
        } else {
            jFrame.dispose();
            MainJFrame mainJFrame = new MainJFrame();
            mainJFrame.setTitle("Quản lý Shipper");
            mainJFrame.setLocationRelativeTo(null);
            mainJFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            mainJFrame.setVisible(true);
        }

        // Xóa mật khẩu từ bộ nhớ sau khi đã sử dụng xong
        java.util.Arrays.fill(passwordChars, ' ');
    }
}
