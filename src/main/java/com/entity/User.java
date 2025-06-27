package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity            // default entity name will be the simple class name "User"
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_user_email",       columnList = "email"),
                @Index(name = "idx_user_username",    columnList = "username"),
                @Index(name = "idx_user_status",      columnList = "status")
        }
)
@Cacheable
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"passwordHash","crawlSessions","roles"})
@Builder
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Setter(AccessLevel.NONE)
    private Long userId;

    @NotBlank @Size(min=2,max=50)
    @Column(name="first_name", nullable=false, length=50)
    private String firstName;

    @NotBlank @Size(min=2,max=50)
    @Column(name="last_name", nullable=false, length=50)
    private String lastName;

    @NotBlank @Size(min=3,max=30)
    @Pattern(regexp="^[a-zA-Z0-9_]+$")
    @Column(name="username", unique=true, nullable=false, length=30)
    private String username;

    @NotBlank @Email
    @Column(name="email", unique=true, nullable=false, length=255)
    private String email;

    @JsonIgnore @NotBlank
    @Column(name="password_hash", nullable=false, length=60)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false, length=20)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    @Column(name="email_verified", nullable=false)
    @Builder.Default
    private Boolean emailVerified = false;

    @Column(name="last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name="login_attempts", nullable=false)
    @Builder.Default
    private Integer loginAttempts = 0;

    @Column(name="newsletter_subscribed", nullable=false)
    @Builder.Default
    private Boolean newsletterSubscribed = false;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at", nullable=false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
    @Builder.Default
    private Set<CrawlSession> crawlSessions = new HashSet<>();

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name="user_roles",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    // Business methods...
    public String getFullName() { return firstName + " " + lastName; }
    public boolean isActive() { return status == UserStatus.ACTIVE; }
    public boolean canLogin() { return isActive() && loginAttempts < 5; }
    public void incrementLoginAttempts() { loginAttempts++; }
    public void resetLoginAttempts() { loginAttempts = 0; }
    public boolean hasRole(String roleName) {
        return roles.stream().anyMatch(r->r.getName().equals(roleName));
    }
    public boolean isAdmin() { return hasRole("ADMIN"); }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (!(o instanceof User)) return false;
        User u = (User)o;
        return userId != null && userId.equals(u.userId);
    }
    @Override public int hashCode() { return getClass().hashCode(); }

    public void addUserRole(Role userRole) {
        if (userRole == null) return;
        if (roles.contains(userRole)) return;
        roles.add(userRole);
        userRole.getUsers().add(this);
    }

    public enum UserStatus { ACTIVE, SUSPENDED, INACTIVE }
}
