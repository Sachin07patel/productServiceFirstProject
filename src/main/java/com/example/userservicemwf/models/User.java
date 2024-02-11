package com.example.userservicemwf.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import javax.management.relation.Role;
import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel{
    private String name;
    private String email;
    private String hashedPassword;
    @ManyToMany
    private List<Roles> roles;
    private boolean isEmailVerified;
}
