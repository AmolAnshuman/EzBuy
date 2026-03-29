package com.ecommerce.EzBuy.repositories;

import com.ecommerce.EzBuy.model.AppRole;
import com.ecommerce.EzBuy.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
