/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HanoiDistricts {

    private JSONArray hanoiDistricts = new JSONArray();
    
    public HanoiDistricts() {
        
        addDistrict(hanoiDistricts, 1, "Quận Ba Đình");
        addDistrict(hanoiDistricts, 16, "Thị xã Sơn Tây");
        addDistrict(hanoiDistricts, 2, "Quận Hoàn Kiếm");
        addDistrict(hanoiDistricts, 17, "Huyện Ba Vì");
        addDistrict(hanoiDistricts, 3, "Quận Hai Bà Trưng");
        addDistrict(hanoiDistricts, 18, "Huyện Phúc Thọ");
        addDistrict(hanoiDistricts, 4, "Quận Đống Đa");
        addDistrict(hanoiDistricts, 19, "Huyện Thạch Thất");
        addDistrict(hanoiDistricts, 5, "Quận Tây Hồ");
        addDistrict(hanoiDistricts, 20, "Huyện Quốc Oai");
        addDistrict(hanoiDistricts, 6, "Quận Cầu Giấy");
        addDistrict(hanoiDistricts, 21, "Huyện Chương Mỹ");
        addDistrict(hanoiDistricts, 7, "Quận Thanh Xuân");
        addDistrict(hanoiDistricts, 22, "Huyện Đan Phượng");
        addDistrict(hanoiDistricts, 8, "Quận Hoàng Mai");
        addDistrict(hanoiDistricts, 23, "Huyện Hoài Đức");
        addDistrict(hanoiDistricts, 9, "Quận Long Biên");
        addDistrict(hanoiDistricts, 24, "Huyện Thanh Oai");
        addDistrict(hanoiDistricts, 10, "Quận Bắc Từ Liêm");
        addDistrict(hanoiDistricts, 25, "Huyện Mỹ Đức");
        addDistrict(hanoiDistricts, 11, "Huyện Thanh Trì");
        addDistrict(hanoiDistricts, 26, "Huyện Ứng Hòa");
        addDistrict(hanoiDistricts, 12, "Huyện Gia Lâm");
        addDistrict(hanoiDistricts, 27, "Huyện Thường Tín");
        addDistrict(hanoiDistricts, 13, "Huyện Đông Anh");
        addDistrict(hanoiDistricts, 28, "Huyện Phú Xuyên");
        addDistrict(hanoiDistricts, 14, "Huyện Sóc Sơn");
        addDistrict(hanoiDistricts, 29, "Huyện Mê Linh");
        addDistrict(hanoiDistricts, 15, "Quận Hà Đông");
        addDistrict(hanoiDistricts, 30, "Quận Nam Từ Liêm");
        
    }

    private static void addDistrict(JSONArray array, int id, String name) {
        JSONObject district = new JSONObject();
        try {
            district.put("district_id", id);
            district.put("district_name", name);
            array.put(district);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getHanoiDistricts() {
        return hanoiDistricts;
    }
    
    
}
