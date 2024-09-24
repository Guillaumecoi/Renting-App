package coigniez.rentapp.model.property;

import java.util.Set;

import coigniez.rentapp.model.address.Address;
import coigniez.rentapp.model.property.tag.Tag;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "properties")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200, unique = true)
    @NotBlank
    private String name;

    @ElementCollection
    @CollectionTable(name = "property_tags", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "tag_name")
    private Set<Tag> tags;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Property parent;

}
