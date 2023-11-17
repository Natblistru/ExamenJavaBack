package edu.step.examenJavaRest.dto;

public class LoginResponseDTO {
    private final String token;
    private final String firstName;
    private final String lastName;

    public LoginResponseDTO(String token, String firstName, String lastName) {
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}

