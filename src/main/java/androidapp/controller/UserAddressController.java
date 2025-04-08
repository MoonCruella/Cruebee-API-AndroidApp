package androidapp.controller;

import androidapp.model.AddressRequest;
import androidapp.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;
    @PostMapping("/addresses/save")
    public ResponseEntity<?> saveUserAddress(@RequestBody AddressRequest address) {
        userAddressService.saveAddress(address);
        return ResponseEntity.ok("Da luu dia chi!");
    }

}
