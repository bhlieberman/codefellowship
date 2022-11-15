package com.bhlieberman.codefellowship.repositories;

import com.bhlieberman.codefellowship.models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    SiteUser findByUsername(String username);
}
