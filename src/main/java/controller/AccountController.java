/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Dao.AccountServiceDao;
import Dao.AccountServiceDaoImpl;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import model.Account;
import view.MainJFrame;

public class AccountController {

    private JFrame jFrame;
    private JTextField jtfUsername;
    private JPasswordField jpwfPassword;
    private JLabel jllbMsg;
    private JButton btnLogin;
    private AccountServiceDao accountServiceDao = null;

    public AccountController() {
    }

    public AccountController(JFrame jFrame, JTextField jtfUsername, JPasswordField jpwfPassword, JLabel jllbMsg, JButton btnLogin) {
        this.jFrame = jFrame;
        this.jtfUsername = jtfUsername;
        this.jpwfPassword = jpwfPassword;
        this.jllbMsg = jllbMsg;
        this.btnLogin = btnLogin;

        accountServiceDao = new AccountServiceDaoImpl();

    }

    public void setEvent() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

}
