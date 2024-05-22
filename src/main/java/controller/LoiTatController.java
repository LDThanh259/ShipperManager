package controller;

//import Dao.OrderServiceDao;
//import Dao.OrderServiceDaoImpl;
import Dao.ShipperServiceDao;
import Dao.ShipperServiceDaoImpl;
import bean.DanhMucBean;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JPanel;
import view.CustomerJPanel;
import view.ShipperOrOrderJPanel;
//import view.ThongKeJPanel;
import view.TrangChuJPanel;

/**
 *
 * @author ADMIN
 */
public class LoiTatController {

    private TrangChuJPanel root;
    private String kindSelected = "";
    private List<DanhMucBean> listLoiTat;
    private ShipperServiceDao shipperServiceDao = null;
    //private OrderServiceDao orderServiceDao = null;

    public LoiTatController(TrangChuJPanel root) {
        this.root = root;
        shipperServiceDao = new ShipperServiceDaoImpl();
        //orderServiceDao = new OrderServiceDaoImpl();
        this.root.getNumOfShipper().setText("" + shipperServiceDao.getNumOfShipper());
        //this.root.getNumOfOrder().setText("" + orderServiceDao.getNumOfOrder());
    }

    public void setEventLoiTat(List<DanhMucBean> listLoiTat) {
        this.listLoiTat = listLoiTat;
        for (DanhMucBean item : listLoiTat) {
            item.getJpn().addMouseListener(new PanelEvent(item.getKind(), item.getJpn()));
        }

    }

    class PanelEvent implements MouseListener {

        private JPanel node;
        private String kind;
        private JPanel jpnItem;
//        private JLabel jlbItem;

        public PanelEvent(String kind, JPanel jpnItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            //this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "Shipper":
                    node = new ShipperOrOrderJPanel("Shipper");
                    break;
                case "Order":
                    node = new ShipperOrOrderJPanel("Order");
                    break;

                case "Customer":
                    node = new CustomerJPanel("Customer");
                    break;
//                case "ThongKe":
//                    node = new ThongKeJPanel();
//                    break;
                default:
                    break;
            }

            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            //setChangeBackground(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(new Color(96, 100, 191));
            //jlbItem.setBackground(new Color(96, 100, 191));

        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(96, 100, 191));
            //jlbItem.setBackground(new Color(96, 100, 191));

        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(76, 175, 80));
                //jlbItem.setBackground(new Color(76, 175, 80));

            }
        }

    }

    private void setChangeBackground(String kind) {
        for (DanhMucBean item : listLoiTat) {
            if (item.getKind().equalsIgnoreCase(kind)) {
                item.getJpn().setBackground(new Color(96, 100, 191));
                //item.getJlb().setBackground(new Color(96, 100, 191));

            } else {
                item.getJpn().setBackground(new Color(76, 175, 80));
                //item.getJlb().setBackground(new Color(76, 175, 80));

            }
        }
    }
}
