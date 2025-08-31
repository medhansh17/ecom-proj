package com.medhansh.ecom_proj.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> roles; // e.g. ROLE_USER, ROLE_ADMIN
}
