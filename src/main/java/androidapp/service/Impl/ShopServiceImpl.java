package androidapp.service.Impl;

import androidapp.entity.ShopEntity;
import androidapp.model.ResponseShop;
import androidapp.repository.ShopRepository;
import androidapp.service.DistanceService;
import androidapp.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;
    @Override
    public List<ResponseShop> findShopIn10km(double uLat, double uLong) {
        List<ShopEntity> list = shopRepository.findAll();
        List<ResponseShop> shopsInRange = new ArrayList<>(); // Khởi tạo một danh sách mới để lưu cửa hàng trong phạm vi 10km
        for (ShopEntity shopEntity : list) {
            double shopLat = shopEntity.getLatitude();
            double shopLong = shopEntity.getLongitude();
            DistanceService distanceService = new DistanceService();
            double distance = distanceService.calculateDistance(uLat,uLong,shopLat,shopLong);

            String formattedDistance = String.format("%.2f", distance);
            double roundedDistance = Double.parseDouble(formattedDistance);
            if(roundedDistance <= 30)
            {
                shopsInRange.add(new ResponseShop(roundedDistance,shopEntity));
            }
        }

        // Sort theo thu tu distance tu nho den lon
        shopsInRange.sort(Comparator.comparingDouble(ResponseShop::getDistance));
        return shopsInRange;
    }
}
