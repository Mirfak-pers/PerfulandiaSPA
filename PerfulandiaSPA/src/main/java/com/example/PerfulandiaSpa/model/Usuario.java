package com.example.PerfulandiaSpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @Column(nullable = false)
    private Long id; // Puede ser Long o int dependiendo de tus necesidades

    @Column(nullable = false, unique = true)
    private String username; // Nombre de usuario único

    @Column(nullable = false)
    private String password; // Contraseña del usuario (idealmente debe ser cifrada)

    @Column(nullable = false, unique = true)
    private String email; // Correo electrónico único

    @Column(nullable  = false)
        private String Rol;

    public Usuario(Long id, String username, String password, String email, String rol) {
            this.setId(id);
            this.setUsername(username);
            this.setPassword(password);
            this.setEmail(email);
            this.setRol(rol);
        }

        public void setUsername(String username) {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
            }
            this.username = username;
        }

        public void setPassword(String password) {
            if (password == null || password.length() < 6) {
                throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
            }
            this.password = password;
        }

        public void setEmail(String email) {
            if (email == null || !email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
                throw new IllegalArgumentException("El correo electrónico no es válido");
            }
            this.email = email;
        }

        public void setRol(String rol) {
            if (rol == null || rol.trim().isEmpty()) {
                throw new IllegalArgumentException("El rol no puede estar vacío");
            }
            this.Rol = rol;
        }
}