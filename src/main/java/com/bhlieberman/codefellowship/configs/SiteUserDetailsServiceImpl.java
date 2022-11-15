package com.bhlieberman.codefellowship.configs;

import com.bhlieberman.codefellowship.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SiteUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SiteUserRepository siteUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return siteUserRepo.findByUsername(username);
    }
}
