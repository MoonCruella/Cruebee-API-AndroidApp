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

    // Ham update dia chi mac dinh
    @Override
    public void saveAddress(AddressRequest address) {
        Optional<UserEntity> userEntity = userRepository.findById(address.getUserId());
        if (userEntity.isPresent()) {
            UserEntity user = userEntity.get();
            updateIsPrimary(user,address);
        } else {
            System.out.println("User not found");
        }
    }

    @Override
    public void updateIsPrimary(UserEntity user,AddressRequest address) {
        UserAddressEntity userAddressEntity = userAddressRepository.findByUserAndIsPrimary(user,1);
        if (userAddressEntity != null) {
            userAddressEntity.setAddressDetails(address.getAddress_details());
            userAddressEntity.setLatitude(address.getLatitude());
            userAddressEntity.setLongitude(address.getLongitude());
            userAddressEntity.setUsername(address.getUsername());
            userAddressEntity.setNote(address.getNote());
            userAddressEntity.setSdt(address.getSdt());
            userAddressRepository.save(userAddressEntity);
        }
        else {
            UserAddressEntity userAddress = new UserAddressEntity();
            userAddress.setAddressDetails(address.getAddress_details());
            userAddress.setLatitude(address.getLatitude());
            userAddress.setLongitude(address.getLongitude());
            userAddress.setIsPrimary(address.getIs_primary());
            userAddress.setUsername(user.getUsername());
            userAddress.setUsername(address.getUsername());
            userAddress.setNote(address.getNote());
            userAddress.setSdt(address.getSdt());
            userAddressRepository.save(userAddress);
        }

    }

    @Override
    public void deleteAddress(int addressId) {
        Optional<UserAddressEntity> userAddressEntity = userAddressRepository.findById(addressId);
        if (userAddressEntity.isPresent()) {
            UserAddressEntity userAddress = userAddressEntity.get();
            userAddressRepository.delete(userAddress);
        }

    }

    @Override
    public void addAddress(AddressRequest address) {
        Optional<UserEntity> userEntity = userRepository.findById(address.getUserId());
        if (userEntity.isPresent()) {
            UserEntity user = userEntity.get();

            // Kiem tra neu set dia chi nay la mac dinh => doi dia chi mac dinh hien tai sang 0
            if(address.getIs_primary() == 1){
                UserAddressEntity userAddressEntity = userAddressRepository.findByUserAndIsPrimary(user,1);
                if(userAddressEntity != null){
                    userAddressEntity.setIsPrimary(0);
                    userAddressRepository.save(userAddressEntity);
                }
            }
            UserAddressEntity userAddress = new UserAddressEntity();
            userAddress.setAddressDetails(address.getAddress_details());
            userAddress.setLatitude(address.getLatitude());
            userAddress.setLongitude(address.getLongitude());
            userAddress.setIsPrimary(address.getIs_primary());
            userAddress.setUsername(address.getUsername());
            userAddress.setNote(address.getNote());
            userAddress.setSdt(address.getSdt());
            userAddress.setUser(user);
            userAddressRepository.save(userAddress);
        } else {
            System.out.println("User not found");
        }
    }

    @Override
    public void updateAddress(AddressRequest address) {
        Optional<UserEntity> userEntity = userRepository.findById(address.getUserId());
        if (userEntity.isPresent()) {
            UserEntity user = userEntity.get();

            // Kiem tra neu set dia chi nay la mac dinh => doi dia chi mac dinh hien tai sang 0
            if(address.getIs_primary() == 1){
                UserAddressEntity userAddressEntity = userAddressRepository.findByUserAndIsPrimary(user,1);
                if(userAddressEntity != null){
                    userAddressEntity.setIsPrimary(0);
                    userAddressRepository.save(userAddressEntity);
                }
            }

            UserAddressEntity userAddress = userAddressRepository.findById(address.getId()).get();
            userAddress.setAddressDetails(address.getAddress_details());
            userAddress.setLatitude(address.getLatitude());
            userAddress.setLongitude(address.getLongitude());
            userAddress.setIsPrimary(address.getIs_primary());
            userAddress.setUsername(address.getUsername());
            userAddress.setNote(address.getNote());
            userAddress.setSdt(address.getSdt());
            userAddress.setUser(user);
            userAddressRepository.save(userAddress);
        } else {
            System.out.println("User not found");
        }
    }

    @Override
    public List<UserAddressEntity> findAddress(int userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            UserEntity user = userEntity.get();
            return userAddressRepository.findUserAddressEntityByUser(user);
        }
        return List.of();
    }

    @Override
    public UserAddressEntity getAddress(int userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            UserEntity user = userEntity.get();
            return userAddressRepository.findByUserAndIsPrimary(user, 1);
        }
        return null;
    }
}
