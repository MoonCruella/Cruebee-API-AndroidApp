package androidapp.entity;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	private String role;
	private String sdt;
	private String gender;
	private String otp;
	private boolean active;
	private LocalDateTime optGeneratedTime;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<PaymentEntity> userPayments;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<CartItemsEntity> cartItems;


	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<TokenEntity> tokens;

}
