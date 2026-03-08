package com.ecommerce.EzBuy.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Enumerated(EnumType.STRING) //by default enum is persisted as integer, we are changing it to string
    @Column(length = 20,name = "role_name")
    private AppRole roleName ;

    public Role(AppRole roleName) {
        this.roleName = roleName;
    }

}
