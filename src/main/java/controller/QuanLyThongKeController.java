package controller;

import Dao.ThongkeServiceDao;
import Dao.ThongkeServiceDaoImpl;
import bean.IncomeBean;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JPanel;
import model.Shipper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

public class QuanLyThongKeController {

    private ThongkeServiceDao thongkeServiceDao = null;
    private Shipper shipper;

    public QuanLyThongKeController() {
        thongkeServiceDao = new ThongkeServiceDaoImpl();
    }

    public QuanLyThongKeController(Shipper shipper) {
        thongkeServiceDao = new ThongkeServiceDaoImpl();
        this.shipper = shipper;
    }
    
    public void setDataToChart1(JPanel jpnItem) {
        List<IncomeBean> listItem = thongkeServiceDao.getListIncome();
        if (listItem != null) {
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
            for (IncomeBean item : listItem) {
                dataSet.addValue(item.getIncome(), "Thu nhập", item.getId()+"-"+item.getNameShipper());
            }
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Biểu đồ thu nhập".toUpperCase(),
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
    
    public void setDataToChart2(JPanel jpnItem) {
        List<IncomeBean> listItem = thongkeServiceDao.getIncomeByShipperAndMonth(shipper);
        if (listItem != null) {
            DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
            for (IncomeBean item : listItem) {
                dataSet.addValue(item.getIncome(), "Thu nhập", item.getMonth()+ "");
            }
            JFreeChart barChart = ChartFactory.createBarChart(
                    "Biểu đồ thu nhập mỗi tháng".toUpperCase(),
                    "Tháng","Thu nhập",
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
}
