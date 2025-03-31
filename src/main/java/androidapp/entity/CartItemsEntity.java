package androidapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;

    // Một giỏ hàng thuộc về một User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // Một giỏ hàng chứa một sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
