package AddressAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class LocationApp extends JFrame {

    private JComboBox<String> provinceComboBox1, districtComboBox1, wardComboBox1;
    private JComboBox<String> provinceComboBox2, districtComboBox2, wardComboBox2;

    private JSONArray jsonData;

    // constructor và phương thức khác
    public LocationApp() {
        // Cài đặt cơ bản
        setTitle("Location App - Khoảng Cách Địa Điểm");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadDataFromJSON();

        provinceComboBox1 = new JComboBox<>();
        districtComboBox1 = new JComboBox<>();
        wardComboBox1 = new JComboBox<>();
        provinceComboBox2 = new JComboBox<>();
        districtComboBox2 = new JComboBox<>();
        wardComboBox2 = new JComboBox<>();

        provinceComboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDistrictComboBox(provinceComboBox1, districtComboBox1);
            }
        });

        districtComboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateWardComboBox(provinceComboBox1, districtComboBox1, wardComboBox1);
            }
        });

        provinceComboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDistrictComboBox(provinceComboBox2, districtComboBox2);
            }
        });

        districtComboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateWardComboBox(provinceComboBox2, districtComboBox2, wardComboBox2);
            }
        });

        setLayout(new FlowLayout());

        add(new JLabel("Địa Điểm 1 - Tỉnh/Thành phố:"));
        add(provinceComboBox1);
        add(new JLabel("Quận/Huyện:"));
        add(districtComboBox1);
        add(new JLabel("Phường/Xã:"));
        add(wardComboBox1);

        add(new JLabel("Địa Điểm 2 - Tỉnh/Thành phố:"));
        add(provinceComboBox2);
        add(new JLabel("Quận/Huyện:"));
        add(districtComboBox2);
        add(new JLabel("Phường/Xã:"));
        add(wardComboBox2);

        JButton calculateButton = new JButton("Tính Khoảng Cách");
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateDistance(); // Phương thức tính khoảng cách
            }
        });
        add(calculateButton);

        populateProvinceComboBox(provinceComboBox1);
        populateProvinceComboBox(provinceComboBox2);

        setVisible(true);
    }

    public LocationApp(JComboBox<String> provinceComboBox1, JComboBox<String> districtComboBox1, JComboBox<String> wardComboBox1) {
        this.provinceComboBox1 = provinceComboBox1;
        this.districtComboBox1 = districtComboBox1;
        this.wardComboBox1 = wardComboBox1;
        
        loadDataFromJSON();
        
        provinceComboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDistrictComboBox(provinceComboBox1, districtComboBox1);
            }
        });

        districtComboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateWardComboBox(provinceComboBox1, districtComboBox1, wardComboBox1);
            }
        });

        populateProvinceComboBox(provinceComboBox1);
        
    }

    
    private void loadDataFromJSON() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("HaNoiJson.json");
            if (inputStream == null) {
                throw new Exception("File jsonDemo.json could not be found!");
            }

            JSONTokener tokener = new JSONTokener(new InputStreamReader(inputStream)); // Cần sử dụng InputStreamReader
            jsonData = new JSONArray(tokener); // jsonData là JSONArray
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateProvinceComboBox(JComboBox<String> provinceComboBox) {
        for (int i = 0; i < jsonData.length(); i++) {
            JSONObject province = jsonData.getJSONObject(i);
            String name = province.getString("Name")+"/"+province.getString("Id");
            provinceComboBox.addItem(name);
        }
    }

    private void updateDistrictComboBox(JComboBox<String> provinceComboBox, JComboBox<String> districtComboBox) {
        districtComboBox.removeAllItems();
        int provinceIndex = provinceComboBox.getSelectedIndex();
        if (provinceIndex >= 0) {
            JSONObject province = jsonData.getJSONObject(provinceIndex);
            JSONArray districts = province.getJSONArray("Districts");
            for (int i = 0; i < districts.length(); i++) {
                JSONObject district = districts.getJSONObject(i);
                String name = district.getString("Name")+"/"+district.getString("Id");
                districtComboBox.addItem(name);
            }
        }
    }

    private void updateWardComboBox(JComboBox<String> provinceComboBox, JComboBox<String> districtComboBox, JComboBox<String> wardComboBox) {
        wardComboBox.removeAllItems();
        int provinceIndex = provinceComboBox.getSelectedIndex();
        int districtIndex = districtComboBox.getSelectedIndex();
        if (provinceIndex >= 0 && districtIndex >= 0) {
            JSONObject province = jsonData.getJSONObject(provinceIndex);
            JSONArray districts = province.getJSONArray("Districts");
            JSONObject district = districts.getJSONObject(districtIndex);
            JSONArray wards = district.getJSONArray("Wards");
            for (int i = 0; i < wards.length(); i++) {
                JSONObject ward = wards.getJSONObject(i);
                String name = ward.getString("Name")+"/"+ward.getString("Id");
                wardComboBox.addItem(name);
            }
        }
    }

    private Point2D.Double getWardCoordinates(JComboBox<String> wardComboBox) {
        int provinceIndex = provinceComboBox1.getSelectedIndex();
        int districtIndex = districtComboBox1.getSelectedIndex();
        int wardIndex = wardComboBox.getSelectedIndex();

        if (provinceIndex >= 0 && districtIndex >= 0 && wardIndex >= 0) {
            JSONObject province = jsonData.getJSONObject(provinceIndex);
            JSONArray districts = province.getJSONArray("Districts");
            JSONObject district = districts.getJSONObject(districtIndex);
            JSONArray wards = district.getJSONArray("Wards");
            JSONObject ward = wards.getJSONObject(wardIndex);

            double latitude = ward.getDouble("Latitude");
            double longitude = ward.getDouble("Longitude");
            return new Point2D.Double(latitude, longitude);
        }

        return null; // Trả về null nếu không lấy được dữ liệu
    }

    private void calculateDistance() {
        // Lấy tọa độ từ comboBox1 và comboBox2
        Point2D.Double coordinates1 = getWardCoordinates(wardComboBox1);
        Point2D.Double coordinates2 = getWardCoordinates(wardComboBox2);

        if (coordinates1 != null && coordinates2 != null) {
            double lat1 = coordinates1.x;
            double lon1 = coordinates1.y;
            double lat2 = coordinates2.x;
            double lon2 = coordinates2.y;

            double earthRadius = 6371; // Bán kính Trái Đất tính bằng km

            double dLat = Math.toRadians(lat2 - lat1);
            double dLng = Math.toRadians(lon2 - lon1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                    * Math.sin(dLng / 2) * Math.sin(dLng / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = earthRadius * c;

            JOptionPane.showMessageDialog(this, "Khoảng cách giữa hai điểm là: " + distance + " km");
        } else {
            JOptionPane.showMessageDialog(this, "Không thể lấy tọa độ từ các địa điểm được chọn!");
        }
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new LocationApp();
//            }
//        });
//    }
}
