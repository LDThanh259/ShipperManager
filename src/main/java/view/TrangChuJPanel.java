/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import bean.DanhMucBean;
import controller.ChuyenManHinhController;
import controller.LoiTatController;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TrangChuJPanel extends javax.swing.JPanel {

    public TrangChuJPanel() {
        initComponents();

        List<DanhMucBean> listLoiTat = new ArrayList<>();
        listLoiTat.add(new DanhMucBean("Shipper", jpnShipper));
        listLoiTat.add(new DanhMucBean("Order", jpnOrder));
        listLoiTat.add(new DanhMucBean("Customer", jpnCustomer));
        listLoiTat.add(new DanhMucBean("Service", jpnService));
        listLoiTat.add(new DanhMucBean("ThongKe", jpnThongke));

        List<DanhMucBean> listDanhMuc = new ArrayList<>();
        LoiTatController loiTatController = new LoiTatController(this);
        loiTatController.setEventLoiTat(listLoiTat);
    }

    public JLabel getNumOfShipper() {
        return numOfShipper;
    }

    public JLabel getNumOfOrder() {
        return numOfOrder;
    }

    public JLabel getNumOfCustomer() {
        return numOfCustomer;

    }

    public JLabel getNumOfService() {
        return numofService;

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnShipper = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        numOfShipper = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jpnOrder = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        numOfOrder = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jpnCustomer = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        numOfCustomer = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jpnService = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        numofService = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jpnThongke = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jpnView = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jpnShipper.setBackground(new java.awt.Color(76, 175, 80));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/users_32px.png"))); // NOI18N

        numOfShipper.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        numOfShipper.setForeground(new java.awt.Color(255, 255, 255));
        numOfShipper.setText("100");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Shipper");

        javax.swing.GroupLayout jpnShipperLayout = new javax.swing.GroupLayout(jpnShipper);
        jpnShipper.setLayout(jpnShipperLayout);
        jpnShipperLayout.setHorizontalGroup(
            jpnShipperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnShipperLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(numOfShipper)
                .addGap(47, 47, 47))
            .addGroup(jpnShipperLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnShipperLayout.setVerticalGroup(
            jpnShipperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnShipperLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnShipperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(numOfShipper))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(20, 20, 20))
        );

        jpnOrder.setBackground(new java.awt.Color(76, 175, 80));
        jpnOrder.setPreferredSize(new java.awt.Dimension(108, 91));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/users_32px.png"))); // NOI18N

        numOfOrder.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        numOfOrder.setForeground(new java.awt.Color(255, 255, 255));
        numOfOrder.setText("100");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Order");

        javax.swing.GroupLayout jpnOrderLayout = new javax.swing.GroupLayout(jpnOrder);
        jpnOrder.setLayout(jpnOrderLayout);
        jpnOrderLayout.setHorizontalGroup(
            jpnOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnOrderLayout.createSequentialGroup()
                .addGroup(jpnOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnOrderLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(numOfOrder))
                    .addGroup(jpnOrderLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel6)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jpnOrderLayout.setVerticalGroup(
            jpnOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnOrderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(numOfOrder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(20, 20, 20))
        );

        jpnCustomer.setBackground(new java.awt.Color(76, 175, 80));
        jpnCustomer.setPreferredSize(new java.awt.Dimension(108, 91));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/users_32px.png"))); // NOI18N

        numOfCustomer.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        numOfCustomer.setForeground(new java.awt.Color(255, 255, 255));
        numOfCustomer.setText("100");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Customer");

        javax.swing.GroupLayout jpnCustomerLayout = new javax.swing.GroupLayout(jpnCustomer);
        jpnCustomer.setLayout(jpnCustomerLayout);
        jpnCustomerLayout.setHorizontalGroup(
            jpnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnCustomerLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(numOfCustomer)
                .addGap(32, 32, 32))
            .addGroup(jpnCustomerLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnCustomerLayout.setVerticalGroup(
            jpnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnCustomerLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jpnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(numOfCustomer))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(14, 14, 14))
        );

        jpnService.setBackground(new java.awt.Color(76, 175, 80));
        jpnService.setPreferredSize(new java.awt.Dimension(108, 91));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/shipping-fast (1).png"))); // NOI18N

        numofService.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        numofService.setForeground(new java.awt.Color(255, 255, 255));
        numofService.setText("100");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Service");

        javax.swing.GroupLayout jpnServiceLayout = new javax.swing.GroupLayout(jpnService);
        jpnService.setLayout(jpnServiceLayout);
        jpnServiceLayout.setHorizontalGroup(
            jpnServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnServiceLayout.createSequentialGroup()
                .addGroup(jpnServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpnServiceLayout.createSequentialGroup()
                        .addContainerGap(15, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(24, 24, 24)
                        .addComponent(numofService))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpnServiceLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel12)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jpnServiceLayout.setVerticalGroup(
            jpnServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnServiceLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jpnServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(numofService))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addGap(20, 20, 20))
        );

        jpnThongke.setBackground(new java.awt.Color(76, 175, 80));
        jpnThongke.setPreferredSize(new java.awt.Dimension(108, 91));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/stats (1).png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("100");

        jLabel18.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Thống kê");

        javax.swing.GroupLayout jpnThongkeLayout = new javax.swing.GroupLayout(jpnThongke);
        jpnThongke.setLayout(jpnThongkeLayout);
        jpnThongkeLayout.setHorizontalGroup(
            jpnThongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnThongkeLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jpnThongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addGroup(jpnThongkeLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel17)))
                .addGap(15, 15, 15))
        );
        jpnThongkeLayout.setVerticalGroup(
            jpnThongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnThongkeLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jpnThongkeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addGap(20, 20, 20))
        );

        jpnView.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jpnViewLayout = new javax.swing.GroupLayout(jpnView);
        jpnView.setLayout(jpnViewLayout);
        jpnViewLayout.setHorizontalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jpnViewLayout.setVerticalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(539, 539, 539))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpnShipper, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(jpnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jpnCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(jpnService, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(jpnThongke, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 507, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnThongke, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnService, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnShipper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(jpnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jpnCustomer;
    private javax.swing.JPanel jpnOrder;
    private javax.swing.JPanel jpnService;
    private javax.swing.JPanel jpnShipper;
    private javax.swing.JPanel jpnThongke;
    private javax.swing.JPanel jpnView;
    private javax.swing.JLabel numOfCustomer;
    private javax.swing.JLabel numOfOrder;
    private javax.swing.JLabel numOfShipper;
    private javax.swing.JLabel numofService;
    // End of variables declaration//GEN-END:variables
}
