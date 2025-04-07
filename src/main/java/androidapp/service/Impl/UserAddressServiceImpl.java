package androidapp.service.Impl;

import androidapp.entity.UserAddressEntity;
import androidapp.entity.UserEntity;
import androidapp.model.AddressRequest;
import androidapp.repository.UserAddressRepository;
import androidapp.repository.UserRepository;
import androidapp.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveAddress(AddressRequest address) {
        Optional<UserEntity> userEntity = userRepository.findById(address.getUserId());
        if (userEntity.isPresent()) {
            UserEntity user = userEntity.get();

            // Update all addresses to ensure only one address is marked as primary
            updateIsPrimary(user);

            // Check if the address already exists based on coordinates
            Optional<UserAddressEntity> existedAddress = userAddressRepository.findUserAddressEntityByUserAndCoordinates(user, address.getLatitude(), address.getLongitude());

            if (existedAddress.isPresent()) {
                // If the address exists, update the primary flag and save it
                UserAddressEntity userAddressEntity = existedAddress.get();
                userAddressEntity.setIsPrimary(address.getIs_primary());
                userAddressRepository.save(userAddressEntity);
            } else {
                // If the address does not exist, create a new one
                UserAddressEntity userAddressEntity = new UserAddressEntity();
                userAddressEntity.setUser(user);
                userAddressEntity.setAddressDetails(address.getAddress_details());
                userAddressEntity.setLatitude(address.getLatitude());
                userAddressEntity.setLongitude(address.getLongitude());
                userAddressEntity.setIsPrimary(address.getIs_primary());
                userAddressRepository.save(userAddressEntity);
            }
        } else {
            System.out.println("User not found");
        }
    }

    @Override
    public void updateIsPrimary(UserEntity user) {
        // Fetch all addresses of the user
        List<UserAddressEntity> userAddressEntities = userAddressRepository.findUserAddressEntityByUser(user);

        // Iterate over the user's addresses and reset all primary flags to 0
        for (UserAddressEntity userAddressEntity : userAddressEntities) {
            if (userAddressEntity.getIsPrimary() == 1) {
                userAddressEntity.setIsPrimary(0);  // Set the old primary address to non-primary
                userAddressRepository.save(userAddressEntity); // Save the update
            }
        }
    }
}
