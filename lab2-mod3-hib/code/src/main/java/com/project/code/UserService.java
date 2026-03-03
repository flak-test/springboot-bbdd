package com.project.code;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    @PersistenceContext //The persistence context is a set of entity instances in which each entity identity is unique. It manages the lifecycle of entities and tracks changes made to them.
    private EntityManager entityManager; // The EntityManager is the core interface in JPA for interacting with the persistence context. It allows you to create, read, update and delete entities, as well as manage transactions.
    @Transactional
    public void saveUserWithProfile(User user) {
        entityManager.persist(user); // cascade saves profile too
    }
    public User getUserById(int userId) {
        return entityManager.find(User.class, userId);
    }
    @Transactional
    public void savePost(Post post) {
        entityManager.persist(post);
    }
    public List<Post> getPostsByUserId(int userId) {
        return entityManager
                .createQuery("FROM Post p WHERE p.user.id = :userId", Post.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}