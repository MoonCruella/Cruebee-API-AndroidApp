package androidapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int price;
    private int soldCount;
    private boolean active;
    private LocalDateTime generatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    @JsonIgnore
    private CategoryEntity category;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imageId")
    @JsonIgnore
    private Attachment attachment;
    // Custom getter for attachment ID only
    public String getAttachmentId() {
        if (attachment != null) {
            return attachment.getId();
        }
        return null;
    }

}
