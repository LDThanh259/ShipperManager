/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import bean.DanhMucBean;
import controller.LoiTatController;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

public class TrangChuJPanel extends javax.swing.JPanel {

    public TrangChuJPanel() {
        initComponents();
        
        List<DanhMucBean> listLoiTat = new ArrayList<>();
        listLoiTat.add(new DanhMucBean("Shipper", jpnShipper));
        listLoiTat.add(new DanhMucBean("Order", jpnOrder));
        listLoiTat.add(new DanhMucBean("ThongKe", jpnTk));
        
        LoiTatController loiTatController = new LoiTatController(this);
        loiTatController.setEventLoiTat(listLoiTat);
    }

    public JLabel getNumOfShipper() {
        return numOfShipper;
    }

    public JLabel getNumOfOrder() {
        return numOfOrder;
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
        jpnTk = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jpnShipper.setBackground(new java.awt.Color(76, 175, 80));

        jLabel1.setIcon(new javax.swing.ImageIcon("F:\\JAVA\\NETBEAN\\ShipperMaven\\src\\main\\java\\resourse\\trangchuPlaces-user-identity-icon.png")); // NOI18N

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
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(jpnShipperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jpnShipperLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numOfShipper)))
                .addGap(44, 44, 44))
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
                .addGap(25, 25, 25))
        );

        jpnOrder.setBackground(new java.awt.Color(76, 175, 80));

        jLabel4.setIcon(new javax.swing.ImageIcon("F:\\JAVA\\NETBEAN\\ShipperMaven\\src\\main\\java\\resourse\\trangchuFull-Cart-icon.png")); // NOI18N

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnOrderLayout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(jpnOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jpnOrderLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numOfOrder)))
                .addGap(44, 44, 44))
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
                .addGap(25, 25, 25))
        );

        jpnTk.setBackground(new java.awt.Color(76, 175, 80));

        jLabel7.setIcon(new javax.swing.ImageIcon("F:\\JAVA\\NETBEAN\\ShipperMaven\\src\\main\\java\\resourse\\statistic-up-icon.png")); // NOI18N

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("100");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Thống kê");

        javax.swing.GroupLayout jpnTkLayout = new javax.swing.GroupLayout(jpnTk);
        jpnTk.setLayout(jpnTkLayout);
        jpnTkLayout.setHorizontalGroup(
            jpnTkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnTkLayout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(jpnTkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jpnTkLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addGap(44, 44, 44))
        );
        jpnTkLayout.setVerticalGroup(
            jpnTkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnTkLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnTkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jpnShipper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jpnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jpnTk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnTk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnShipper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(299, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jpnOrder;
    private javax.swing.JPanel jpnShipper;
    private javax.swing.JPanel jpnTk;
    private javax.swing.JLabel numOfOrder;
    private javax.swing.JLabel numOfShipper;
    // End of variables declaration//GEN-END:variables
}
