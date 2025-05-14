package androidapp.service;

import org.springframework.stereotype.Service;

@Service
public class DistanceService {

    // Hàm tính khoảng cách giữa hai điểm (lat1, lon1) và (lat2, lon2) theo công thức Haversine
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        // Chuyển từ độ sang radian
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Bán kính trái đất (Km)
        double R = 6371; // Km

        // Sự thay đổi vĩ độ và kinh độ
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Công thức Haversine
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Tính khoảng cách
        return R * c;
    }
}