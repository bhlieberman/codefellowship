package com.bhlieberman.codefellowship.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class SiteUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "followers_to_followees",
            joinColumns = {@JoinColumn(name = "userWhoIsFollowing")},
            inverseJoinColumns = {@JoinColumn(name = "FollowedUser")}
    )
    Set<SiteUser> usersIFollow = new HashSet<>();

    @ManyToMany(mappedBy = "usersIFollow")
    Set<SiteUser> usersWhoFollowMe = new HashSet<>();

    public Set<SiteUser> getUsersIFollow() {
        return usersIFollow;
    }

    public Set<SiteUser> getUsersWhoFollowMe() {
        return usersWhoFollowMe;
    }

    public SiteUser() {}

    public SiteUser(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Object getFirstName() {
        return this.firstName;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private void createPost(Post p) {
        posts.add(p);
    }

    public Set<Post> getPosts() {
        return posts;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
