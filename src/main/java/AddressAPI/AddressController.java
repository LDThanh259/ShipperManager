package AddressAPI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import model.HanoiDistricts;
import org.json.JSONArray;
import org.json.JSONObject;

public class AddressController {

    private JComboBox<String> jcbDistinct;
    private JComboBox<String> jcbProvince;
    private JComboBox<String> jcbWard;

    private Map<String, Integer> provinceNameToIdMap = new HashMap<>();
    private Map<String, Integer> districtNameToIdMap = new HashMap<>();

    public AddressController(JComboBox<String> jcbDistinct, JComboBox<String> jcbProvince, JComboBox<String> jcbWard) {
        this.jcbDistinct = jcbDistinct;
        this.jcbProvince = jcbProvince;
        this.jcbWard = jcbWard;

        setEvent();
        loadProvinces(); // Load dữ liệu tỉnh/thành phố khi khởi tạo
    }

    public void setEvent() {
        jcbProvince.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String provinceName = (String) jcbProvince.getSelectedItem();
                if (provinceName != null && !provinceName.isEmpty()) {
                    int provinceId = provinceNameToIdMap.get(provinceName);
                    loadDistricts(provinceId);
                }
            }
        });

        jcbDistinct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String districtName = (String) jcbDistinct.getSelectedItem();
                if (districtName != null && !districtName.isEmpty()) {
                    int districtId = districtNameToIdMap.get(districtName);
                    loadWards(districtId);
                }
            }
        });
    }

    private void loadProvinces() {
        JSONObject response = callAPI("https://vapi.vnappmob.com/api/province");
        if (response != null) {
            JSONArray provincesArray = response.getJSONArray("results");
            provinceNameToIdMap.clear(); // Reset map
            jcbProvince.removeAllItems(); // Xóa danh sách hiện tại
            for (int i = 0; i < provincesArray.length(); i++) {
                JSONObject province = provincesArray.getJSONObject(i);
                String provinceName = province.getString("province_name");
                int provinceId = province.getInt("province_id");
                provinceNameToIdMap.put(provinceName, provinceId);
                jcbProvince.addItem(provinceName);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến dữ liệu tỉnh/thành phố. Vui lòng thử lại sau.");
        }
    }

    private void loadDistricts(int provinceId) {
        JSONArray districtsArray = null;

        if (provinceId != 1) {
            System.out.println(provinceId);
            JSONObject response = callAPI("https://vapi.vnappmob.com/api/province/district/" + provinceId);
            if (response != null) {
                districtsArray = response.getJSONArray("results");
                districtNameToIdMap.clear(); // Reset map
                jcbDistinct.removeAllItems();
                for (int i = 0; i < districtsArray.length(); i++) {
                    JSONObject district = districtsArray.getJSONObject(i);
                    String districtName = district.getString("district_name");
                    int districtId = district.getInt("district_id");
                    districtNameToIdMap.put(districtName, districtId);
                    jcbDistinct.addItem(districtName);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không thể kết nối đến dữ liệu quận/huyện. Vui lòng thử lại sau.");
            }
        } else {
            HanoiDistricts hanoiDistricts = new HanoiDistricts();
            districtsArray = hanoiDistricts.getHanoiDistricts();
            districtNameToIdMap.clear(); //Reset map
            jcbDistinct.removeAllItems();
            for (int i = 0; i < districtsArray.length(); i++) {
                JSONObject district = districtsArray.getJSONObject(i);
                String districtName = district.getString("district_name");
                int districtId = district.getInt("district_id");
                districtNameToIdMap.put(districtName, districtId);
                jcbDistinct.addItem(districtName);
            }
        }

    }

    private void loadWards(int districtId) {
        JSONObject response = callAPI("https://vapi.vnappmob.com/api/province/ward/" + districtId);
        if (response != null) {
            JSONArray wardsArray = response.getJSONArray("results");
            jcbWard.removeAllItems();
            for (int i = 0; i < wardsArray.length(); i++) {
                JSONObject ward = wardsArray.getJSONObject(i);
                String wardName = ward.getString("ward_name");
                jcbWard.addItem(wardName);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến dữ liệu xã/phường. Vui lòng thử lại sau.");
        }
    }

    private JSONObject callAPI(String urlString) {
        HttpURLConnection conn = null;
        Scanner scanner = null;

        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // Thiết lập timeout cho việc kết nối
            conn.setReadTimeout(5000); // Và timeout cho việc đọc dữ liệu
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                if (responseCode == 308) {
                    // Lấy URL mới từ header 'Location' và kết nối lại
                    String newUrl = conn.getHeaderField("Location");
                    return callAPI(newUrl); // Gọi đệ quy với URL mới
                }
            } else {
                try (InputStream inputStream = conn.getInputStream()) {
                    scanner = new Scanner(inputStream);
                    StringBuilder inline = new StringBuilder();
                    while (scanner.hasNext()) {
                        inline.append(scanner.nextLine());
                    }
                    return new JSONObject(inline.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (scanner != null) {
                scanner.close(); // Đảm bảo scanner được đóng nếu không dùng try-with-resources
            }
            if (conn != null) {
                conn.disconnect(); // Ngắt kết nối khi hoàn thành
            }
        }
        return null;
    }
}
