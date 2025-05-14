package androidapp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="addresses")
public class UserAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "is_primary")
    private int isPrimary;
    private String addressDetails;
    private double latitude;
    private double longitude;
    private String username;
    private String note;
    private String sdt;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
