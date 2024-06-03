package controller;

import Dao.ThongKeServiceDao;
import Dao.ThongKeServiceDaoImpl;
import bean.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.ClassTableModel;
import model.Order;
import model.Shipper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

public class QuanLyThongKeController {

    private ThongKeServiceDao thongkeServiceDao = null;
    private int month;
    private JComboBox<String> jcbFillter;
    private JLabel jlbID;
    private JLabel jlbName;
    private Shipper shipper = null;
    private JLabel jlbMess;

    public QuanLyThongKeController(JComboBox<String> jcbFillter) {
        this.jcbFillter = jcbFillter;
        thongkeServiceDao = new ThongKeServiceDaoImpl();

    }

    public QuanLyThongKeController(int month, JComboBox<String> jcbFillter) {
        this.jcbFillter = jcbFillter;
        thongkeServiceDao = new ThongKeServiceDaoImpl();
        this.month = month;
    }

    public QuanLyThongKeController(Shipper shipper, JLabel jlbID, JLabel jlbName, JLabel jlbMess) {
        //this.jcbFillter = jcbFillter;
        this.jlbID = jlbID;
        this.jlbName = jlbName;
        this.shipper = shipper;
        this.jlbMess = jlbMess;
        thongkeServiceDao = new ThongKeServiceDaoImpl();

    }

    public void setDataToChart3(JPanel jpnItem) {
        jlbID.setText(shipper.getId() + "");
        jlbName.setText(shipper.getName());
        LocalDate currentDate = LocalDate.now();
        int year = currentDate.getYear();
        List<CalculateProportion> listItem_1 = thongkeServiceDao.getProportion();
        for (CalculateProportion item : listItem_1) {
            if (item.getId() == shipper.getId()) {
                jlbMess.setText("Tỉ lệ giao hàng thành công: ".toUpperCase() + item.getProportion());
            }
        }

        List<IncomeBean> listItem = thongkeServiceDao.getListIncomeByShipper(shipper);
        if (listItem != null) {
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
            // Create a map to store income data by month
            Map<Integer, Float> incomeMap = new HashMap<>();
            for (IncomeBean item : listItem) {
                incomeMap.put(item.getMonth(), item.getIncome());
            }

            // Ensure every month is accounted for, even if income is 0
            for (int month = 1; month <= 12; month++) {
                Float income = incomeMap.getOrDefault(month, 0f);
                dataSet.addValue(income, "Thu nhập", month + "/" + year);
            }
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Biểu đồ thu nhập".toUpperCase(),
                    "Tháng", "Thu nhập",
                    dataSet, PlotOrientation.VERTICAL, false, true, false);

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 321));

            jpnItem.removeAll();
            jpnItem.setLayout(new CardLayout());
            jpnItem.add(chartPanel);
            jpnItem.validate();
            jpnItem.repaint();

        }

    }

    public void setDataToChart2(JPanel jpnItem) {
        List<IncomeBean> listItem = thongkeServiceDao.getIncomeByShipperAndMonth(month);
        if (listItem != null) {
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
            for (IncomeBean item : listItem) {
                dataSet.addValue(item.getIncome(), "Thu nhập", item.getId() + "-" + item.getNameShipper());
            }
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Biểu đồ thu nhập mỗi tháng".toUpperCase(),
                    "Tên", "Thu nhập",
                    dataSet, PlotOrientation.VERTICAL, false, true, false);

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 321));

            jpnItem.removeAll();
            jpnItem.setLayout(new CardLayout());
            jpnItem.add(chartPanel);
            jpnItem.validate();
            jpnItem.repaint();
        }
    }

    public void setDataToChart1(JPanel jpnItem) {
        List<CalculateProportion> listItem = thongkeServiceDao.getProportion();
        if (listItem != null) {
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
            for (CalculateProportion item : listItem) {
                dataSet.addValue(item.getProportion(), "Tỉ lệ", item.getId() + "-" + item.getName());
            }
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Biểu đồ tỉ lệ giao hàng thành công".toUpperCase(),
                    "Tên", "Tỉ lệ",
                    dataSet, PlotOrientation.VERTICAL, false, true, false);

            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 321));

            jpnItem.removeAll();
            jpnItem.setLayout(new CardLayout());
            jpnItem.add(chartPanel);
            jpnItem.validate();
            jpnItem.repaint();

        }

    }

    public void initFilter(JPanel jpnItem) {
        jcbFillter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<IncomeBean> listItem = null;
                String selected = (String) jcbFillter.getSelectedItem();
                LocalDate currentDate = LocalDate.now();
                int month = currentDate.getMonthValue();
                switch (selected) {
                    case "Tháng 1":
                        month = 1;
                        break;
                    case "Tháng 2":
                        month = 2;
                        break;
                    case "Tháng 3":
                        month = 3;
                        break;
                    case "Tháng 4":
                        month = 4;
                        break;
                    case "Tháng 5":
                        month = 5;
                        break;
                    case "Tháng 6":
                        month = 6;
                        break;
                    case "Tháng 7":
                        month = 7;
                        break;
                    case "Tháng 8":
                        month = 8;
                        break;
                    case "Tháng 9":
                        month = 9;
                        break;
                    case "Tháng 10":
                        month = 10;
                        break;
                    case "Tháng 11":
                        month = 11;
                        break;
                    case "Tháng 12":
                        month = 12;
                        break;
                    default:
                        break;
                }
                listItem = thongkeServiceDao.getIncomeByShipperAndMonth(month);
                DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
                for (IncomeBean item : listItem) {
                    dataSet.addValue(item.getIncome(), "Thu nhập", item.getId() + "-" + item.getNameShipper());
                }
                JFreeChart barChart = ChartFactory.createBarChart(
                        "Biểu đồ thu nhập mỗi tháng".toUpperCase(),
                        "Tên", "Thu nhập",
                        dataSet, PlotOrientation.VERTICAL, false, true, false);

                ChartPanel chartPanel = new ChartPanel(barChart);
                chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 321));

                jpnItem.removeAll();
                jpnItem.setLayout(new CardLayout());
                jpnItem.add(chartPanel);
                jpnItem.validate();
                jpnItem.repaint();
            }
        });
    }
}
