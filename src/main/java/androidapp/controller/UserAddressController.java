package androidapp.controller;

import androidapp.entity.UserAddressEntity;
import androidapp.model.AddressRequest;
import androidapp.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;
    @PostMapping("/addresses/save")
    public ResponseEntity<?> saveUserAddress(@RequestBody AddressRequest address) {
        userAddressService.saveAddress(address);
        return ResponseEntity.ok("Da luu dia chi!");
    }

    @PostMapping("/addresses/update")
    public ResponseEntity<?> updateUserAddress(@RequestBody AddressRequest address) {
        userAddressService.updateAddress(address);
        return ResponseEntity.ok("Da luu dia chi!");
    }

    @PostMapping("/addresses/add")
    public ResponseEntity<?> addUserAddress(@RequestBody AddressRequest address) {
        userAddressService.addAddress(address);
        return ResponseEntity.ok("Da them dia chi!");
    }

    @PostMapping("/addresses/delete")
    public ResponseEntity<?> deleteUserAddress(@RequestParam int addressId) {
        userAddressService.deleteAddress(addressId);
        return ResponseEntity.ok("Da xoa dia chi!");
    }

    @GetMapping("/addresses")
    public List<UserAddressEntity> getUserAddress(@RequestParam int userId) {
        return userAddressService.findAddress(userId);
    }

    @GetMapping("/addresses/primary")
    public UserAddressEntity getUserAddressPrimary(@RequestParam int userId) {
        return userAddressService.getAddress(userId);
    }

    
}
