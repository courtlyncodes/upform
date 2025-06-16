package com.upform.model;

public class User {
    private Long id;
    private String authId;
    private String name;
    private String email;

    private User() {}

    public User (
        Long id,
        String authId,
        String name,
        String email
    ) {
        this.id = id;
        this.authId = authId;
        this.name = name;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAuthId() { return authId; }
    public void setAuthId(String authId) { this.authId = authId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
