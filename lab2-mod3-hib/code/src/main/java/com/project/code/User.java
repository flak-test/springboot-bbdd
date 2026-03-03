package com.project.code;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.springframework.context.annotation.Profile;
import java.util.List;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "users")//solo es necesario si la tabla de la BD tiene nombre diferente de la identidad
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // "id") This annotation is used for managing circular references in JSON serialization. It ensures that objects with the same ID are referenced by their ID rather than being serialized repeatedly when serializing an object graph. The property = "id" part refers to the field that will be used as the identity.
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    @OneToOne(mappedBy = "user", cascade = jakarta.persistence.CascadeType.ALL)
    //es un enum que hace esto: Cascade all operations
    //  @Cascade(CascadeType.ALL) // Cascade operation will be handled by Hibernate - esto está deprecated, asi que lo usamos dentro del OneToOne y listo
    private com.project.code.Profile profile;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER) // "user" refers to the user field in the Post entity
    private List<Post> posts;

    // Constructors, getters, and setters
    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public com.project.code.Profile getProfile() {
        return profile;
    }

    public void setProfile(com.project.code.Profile profile) {
        this.profile = (com.project.code.Profile) profile;
    }
}