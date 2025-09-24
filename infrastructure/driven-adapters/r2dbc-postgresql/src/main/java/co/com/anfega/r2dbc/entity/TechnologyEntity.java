package co.com.anfega.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import lombok.Data;

@Data
@Table(name = "tecnologia")
public class TechnologyEntity {
    @Id
    private Long id;

    @Column("nombre")
    private String name;

    @Column("descripcion")
    private String description;
}
