package androidapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemsModel {
    private int userId;
    private int productId;
    private int quantity;
}
