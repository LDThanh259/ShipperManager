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
import javax.swing.JLabel;

public class TrangChuJPanel extends javax.swing.JPanel {

    public TrangChuJPanel() {
        initComponents();
        
        List<DanhMucBean> listLoiTat = new ArrayList<>();
        listLoiTat.add(new DanhMucBean("Shipper", jpnShipper));
        listLoiTat.add(new DanhMucBean("Order", jpnOrder));
        listLoiTat.add(new DanhMucBean("ThongKe", jpnCustomer));
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
        jpnIncident = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        numOfIncident = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jpnTk = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jpnView = new javax.swing.JPanel();

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
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jpnShipperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jpnShipperLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numOfShipper)))
                .addGap(15, 15, 15))
        );
        jpnShipperLayout.setVerticalGroup(
            jpnShipperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnShipperLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jpnShipperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(numOfShipper))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(20, 20, 20))
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
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jpnOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jpnOrderLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numOfOrder)))
                .addGap(15, 15, 15))
        );
        jpnOrderLayout.setVerticalGroup(
            jpnOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnOrderLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jpnOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(numOfOrder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(20, 20, 20))
        );

        jpnCustomer.setBackground(new java.awt.Color(76, 175, 80));

        jLabel7.setIcon(new javax.swing.ImageIcon("F:\\JAVA\\NETBEAN\\ShipperMaven\\src\\main\\java\\resourse\\statistic-up-icon.png")); // NOI18N

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnCustomerLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jpnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jpnCustomerLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numOfCustomer)))
                .addGap(15, 15, 15))
        );
        jpnCustomerLayout.setVerticalGroup(
            jpnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnCustomerLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jpnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(numOfCustomer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addGap(20, 20, 20))
        );

        jpnService.setBackground(new java.awt.Color(76, 175, 80));

        jLabel10.setIcon(new javax.swing.ImageIcon("F:\\JAVA\\NETBEAN\\ShipperMaven\\src\\main\\java\\resourse\\statistic-up-icon.png")); // NOI18N

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnServiceLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jpnServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jpnServiceLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numofService)))
                .addGap(15, 15, 15))
        );
        jpnServiceLayout.setVerticalGroup(
            jpnServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnServiceLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jpnServiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(numofService))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addGap(20, 20, 20))
        );

        jpnIncident.setBackground(new java.awt.Color(76, 175, 80));

        jLabel13.setIcon(new javax.swing.ImageIcon("F:\\JAVA\\NETBEAN\\ShipperMaven\\src\\main\\java\\resourse\\statistic-up-icon.png")); // NOI18N

        numOfIncident.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        numOfIncident.setForeground(new java.awt.Color(255, 255, 255));
        numOfIncident.setText("100");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Incident");

        javax.swing.GroupLayout jpnIncidentLayout = new javax.swing.GroupLayout(jpnIncident);
        jpnIncident.setLayout(jpnIncidentLayout);
        jpnIncidentLayout.setHorizontalGroup(
            jpnIncidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnIncidentLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jpnIncidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(jpnIncidentLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numOfIncident)))
                .addGap(15, 15, 15))
        );
        jpnIncidentLayout.setVerticalGroup(
            jpnIncidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnIncidentLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jpnIncidentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(numOfIncident))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addGap(20, 20, 20))
        );

        jpnTk.setBackground(new java.awt.Color(76, 175, 80));

        jLabel16.setIcon(new javax.swing.ImageIcon("F:\\JAVA\\NETBEAN\\ShipperMaven\\src\\main\\java\\resourse\\statistic-up-icon.png")); // NOI18N

        jLabel17.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("100");

        jLabel18.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Thống kê");

        javax.swing.GroupLayout jpnTkLayout = new javax.swing.GroupLayout(jpnTk);
        jpnTk.setLayout(jpnTkLayout);
        jpnTkLayout.setHorizontalGroup(
            jpnTkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnTkLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jpnTkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addGroup(jpnTkLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)))
                .addGap(15, 15, 15))
        );
        jpnTkLayout.setVerticalGroup(
            jpnTkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnTkLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jpnTkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
            .addGap(0, 421, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpnShipper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jpnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jpnCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jpnService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jpnIncident, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jpnTk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnTk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnIncident, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnShipper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jpnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jpnCustomer;
    private javax.swing.JPanel jpnIncident;
    private javax.swing.JPanel jpnOrder;
    private javax.swing.JPanel jpnService;
    private javax.swing.JPanel jpnShipper;
    private javax.swing.JPanel jpnTk;
    private javax.swing.JPanel jpnView;
    private javax.swing.JLabel numOfCustomer;
    private javax.swing.JLabel numOfIncident;
    private javax.swing.JLabel numOfOrder;
    private javax.swing.JLabel numOfShipper;
    private javax.swing.JLabel numofService;
    // End of variables declaration//GEN-END:variables
}
