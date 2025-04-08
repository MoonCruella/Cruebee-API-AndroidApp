package androidapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    private int userId;
    private int is_primary;
    private String address_details;
    private double latitude;
    private double longitude;


}
