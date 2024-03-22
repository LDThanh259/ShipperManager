/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import view.LoginJFrame;

/**
 *
 * @author ADMIN
 */
public class ShipperTest {

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginJFrame loginJDialog = new LoginJFrame();

                loginJDialog.setTitle("Đăng nhập hệ thống");
                loginJDialog.setResizable(false);
                loginJDialog.setLocationRelativeTo(null);
                loginJDialog.setVisible(true);
            }
        });
    }
}
