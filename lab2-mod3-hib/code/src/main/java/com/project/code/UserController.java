package com.project.code;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
// Endpoint to create a user with a profile
    @PostMapping("/createUser")
    public String createUser(@RequestBody UserRequest userRequest) {
// Create User and Profile based on the input
        User user = new User(userRequest.getUsername(), userRequest.getEmail());
        com.project.code.Profile profile = new Profile(userRequest.getProfileBio(), user);
        user.setProfile(profile);
// Save user with profile using Hibernate
        userService.saveUserWithProfile(user);
        return "User and profile created successfully!";
    }
    // Endpoint to get a user by ID
    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/createPost")
    public String createPost(@RequestBody PostRequest postRequest) {
// Find the user by ID
        User user = userService.getUserById(postRequest.getUserId());
        if (user == null) {
            return "User not found!";
        }
// Create the post and associate it with the user
        Post post = new Post(postRequest.getContent(), user);
// Save the post
        userService.savePost(post);
        return "Post created successfully!";
    }
    @GetMapping("/getPosts/{userId}")
    public List<Post> getPosts(@PathVariable int userId) {
        List<Post> posts = userService.getPostsByUserId(userId);
        if (posts == null || posts.isEmpty()) {
            throw new RuntimeException("No posts found for user with ID: " + userId);
        }
        return posts;
    }



}

/*
* Tu petición HTTP llega como JSON
* POST /createUser HTTP/1.1
Content-Type: application/json

{
    "username": "john_doe",
    "email": "john.doe@example.com",
    "profileBio": "Software Developer"
}
*
* Paso 2: Spring detecta @RequestBody y Content-Type
*
* @PostMapping("/createUser")
public String createUser(@RequestBody UserRequest userRequest) {
    // Spring ve: "application/json" + @RequestBody
    // "Necesito convertir el JSON a un objeto UserRequest"
}
*
* Paso 3: Jackson (el convertidor JSON) entra en acción

Jackson analiza el JSON y busca en UserRequest:
*
*
* public class UserRequest {
    private String username;     // ← Coincide con "username" en JSON
    private String email;        // ← Coincide con "email" en JSON
    private String profileBio;   // ← Coincide con "profileBio" en JSON

    // Jackson llama a los setters automáticamente
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setProfileBio(String profileBio) { this.profileBio = profileBio; }
}
*
* Paso 4: Mapeo campo por campo

Jackson hace reflection (introspección) para emparejar
* Paso 5: Tu controller recibe el objeto YA construido pero OJO que los nombres de los atrbutos en el JSON deben coincidir con lo que se define en el DTO  (o usar @JsonProperty)
*
*
* */