package androidapp.service;

import androidapp.entity.UserAddressEntity;
import androidapp.entity.UserEntity;
import androidapp.model.AddressRequest;

import java.util.List;

public interface UserAddressService {
    void saveAddress(AddressRequest address);
    void updateIsPrimary(UserEntity user,AddressRequest address);
    void deleteAddress(int addressId);
    void updateAddress(AddressRequest address);
    void addAddress(AddressRequest address);
    List<UserAddressEntity> findAddress(int userId);
    UserAddressEntity getAddress(int userId);
}
