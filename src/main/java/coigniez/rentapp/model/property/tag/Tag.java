package coigniez.rentapp.model.property.tag;

import java.util.Set;

import coigniez.rentapp.model.property.Property;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tag")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Tag {
    @Id
    @Column(nullable = false, length = 200, unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Property> properties;
}