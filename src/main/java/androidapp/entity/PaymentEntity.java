package androidapp.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String addressUser;
    private String fullName;
    private String sdt;
    private String note;
    private Boolean utensils;
    private Long totalPrice;
    private LocalDateTime orderDate;
    private String paymentMethod;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private List<PaymentProductEntity> products;


    @ManyToOne
    @JoinColumn(name = "shopId")
    private ShopEntity shop;
}