package controller;

import bean.DanhMucBean;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import view.CustomerJPanel;
import view.ShipperOrOrderJPanel;
import view.ThongkeJPanel;
//import view.ThongKeJPanel;
import view.TrangChuJPanel;

public class ChuyenManHinhController {

    private JPanel root;
    private String kindSelected = "";
    private List<DanhMucBean> listItem;
    private final Border whiteBorder = BorderFactory.createLineBorder(Color.WHITE, 2); // Border trắng
    private final Border originalBorder = null; // Border gốc của các thành phần

    public ChuyenManHinhController() {
    }

    public ChuyenManHinhController(JPanel jpnRoot) {
        this.root = jpnRoot;
    }

    public void setView(JPanel jpnItem, JLabel jlbItem) {
        kindSelected = "TrangChu";

        root.removeAll();
        root.setLayout(new BorderLayout());
        TrangChuJPanel trangChuJPanel = new TrangChuJPanel();
        root.add(trangChuJPanel);
        root.validate();
        root.repaint();
    }

    public void setEvent(List<DanhMucBean> listItem) {
        this.listItem = listItem;
        for (DanhMucBean item : listItem) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
            item.getJpn().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    class LabelEvent extends MouseAdapter {

        private final String kind;
        private final JPanel jpnItem;
        private final JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "TrangChu":
                    setNode(new TrangChuJPanel());
                    break;
                case "Shipper":
                    setNode(new ShipperOrOrderJPanel("Shipper"));
                    break;
                case "Order":
                    setNode(new ShipperOrOrderJPanel("Order"));
                    break;
                case "Customer":
                    setNode(new CustomerJPanel("Customer"));
                    break;
                case "Service":
                    setNode(new ShipperOrOrderJPanel("Service"));
                    break;
                case "ThongKe":
                    setNode(new ThongkeJPanel());
                    break;
                default:
                    break;
            }
            kindSelected = kind;
            setBorder(jpnItem, whiteBorder);

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            setBorder(jpnItem, whiteBorder);

        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                setBorder(jpnItem, originalBorder);

            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            setBorder(jpnItem, whiteBorder);

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (kindSelected.equalsIgnoreCase(kind)) {
                setBorder(jpnItem, whiteBorder);

            } else {
                setBorder(jpnItem, originalBorder);

            }
        }

        private void setBorder(JPanel panel, Border border) {
            panel.setBorder(border);
            panel.repaint();
        }

        private void setNode(JPanel node) {
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
        }
    }
}
