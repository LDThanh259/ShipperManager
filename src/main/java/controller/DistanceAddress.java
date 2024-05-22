package controller;

import model.Address;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class DistanceAddress {

    private static final String DATA_FILE = "HaNoiJson.json";
    private static final Map<String, double[]> coordinatesMap = new HashMap<>();
    private static final Map<String, String> nameMap = new HashMap<>();

    static {
        loadData();
    }

    public static double[] getCoordinatesById(String provinceId, String districtId, String wardId) {
        String id = generateId(provinceId, districtId, wardId);
        return coordinatesMap.get(id);
    }

    public static String getNameById(String provinceId, String districtId, String wardId) {
        String id = generateId(provinceId, districtId, wardId);
        return nameMap.get(id);
    }

    private static void loadData() {
        try (InputStream inputStream = DistanceAddress.class.getClassLoader().getResourceAsStream(DATA_FILE)) {
            if (inputStream == null) {
                throw new RuntimeException("Không thể tìm thấy file " + DATA_FILE);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray cities = new JSONArray(jsonString.toString());
            for (int i = 0; i < cities.length(); i++) {
                JSONObject city = cities.getJSONObject(i);
                String provinceId = city.getString("Id");

                JSONArray districts = city.getJSONArray("Districts");
                for (int j = 0; j < districts.length(); j++) {
                    JSONObject district = districts.getJSONObject(j);
                    String districtId = district.getString("Id");

                    JSONArray wards = district.getJSONArray("Wards");
                    for (int k = 0; k < wards.length(); k++) {
                        JSONObject ward = wards.getJSONObject(k);
                        String wardId = ward.getString("Id");
                        String id = generateId(provinceId, districtId, wardId);

                        double latitude = ward.getDouble("Latitude");
                        double longitude = ward.getDouble("Longitude");
                        String name = ward.getString("Name");

                        coordinatesMap.put(id, new double[]{latitude, longitude});
                        nameMap.put(id, name);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateId(String provinceId, String districtId, String wardId) {
        // Assuming the provided IDs are already in the desired format
        return provinceId + districtId + wardId; 
    }

    
    // Hàm tính khoảng cách sử dụng công thức Haversine
    public static double distance(Address address1, Address address2) {
        double[] coordinates1 = getCoordinatesById(address1.getProvinceId(), address1.getDistrictId(), address1.getWardId());
        double[] coordinates2 = getCoordinatesById(address2.getProvinceId(), address2.getDistrictId(), address2.getWardId());

        if (coordinates1 == null || coordinates2 == null) {
            System.out.println("Không tìm thấy tọa độ cho ID đã cho.");
            return -1; // Trả về -1 để báo lỗi
        }

        double lat1 = coordinates1[0];
        double lon1 = coordinates1[1];
        double lat2 = coordinates2[0];
        double lon2 = coordinates2[1];

        double R = 6371; // Bán kính Trái Đất (km)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
