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
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Json/HaNoiJson.json");
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

}
