package androidapp.service;

import androidapp.entity.UserEntity;
import androidapp.model.AddressRequest;

public interface UserAddressService {
    void saveAddress(AddressRequest address);
    void updateIsPrimary(UserEntity user);
}
