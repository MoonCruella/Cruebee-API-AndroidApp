package androidapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean active;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<ProductEntity> products;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imageId")
    @JsonIgnore
    private Attachment attachment;
    // Custom getter for attachment ID only
    public String getAttachmentId() {
        if (attachment != null) {
            return attachment.getId();
        }
        return null; // or return -1, depending on your requirements for a missing attachment
    }

}
