package org.example.user_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "username", length = 64, nullable = false, unique = true)
  private String username;
  @Column(name = "password", length = 128, nullable = false)
  private String password;
  @Column(name = "email", length = 64, nullable = false, unique = true)
  private String email;
  @Column(name = "age", nullable = false)
  private Integer age;
  @Column(name = "active", nullable = false)
  private boolean active;
  @ManyToMany
  @JoinTable(name = "subscriptions",
          joinColumns = @JoinColumn(name = "followee_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
  private List<User> followers;
  @ManyToMany(mappedBy = "followers")
  private List<User> followees;


}
