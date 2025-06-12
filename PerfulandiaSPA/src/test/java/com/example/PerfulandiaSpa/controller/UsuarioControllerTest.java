package com.example.PerfulandiaSpa.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
//copiar y pegar estos import static OJO
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.PerfulandiaSpa.model.Usuario;
import com.example.PerfulandiaSpa.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
// Update these imports to match the actual package structure of your project.
// For example, if the classes are under 'com.example.PerfulandiaSpa.controllers' (note plural), update accordingly.
// If you are unsure, check your 'src/main/java/com/example/PerfulandiaSpa/' directory for the correct package names.



@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("Juan");
        usuario.setEmail("juan@example.com");
        usuario.setPassword("password123");
        usuario.setRol("ROLE_USER");
    }

    @Test
    public void testCreateUsuario() throws Exception {
        when(usuarioService.saveUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@example.com"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andExpect(jsonPath("$.rol").value("ROLE_USER"));
    }

    @Test
    public void testGetUsuarioById() throws Exception {
        when(usuarioService.getUsuarioById(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@example.com"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andExpect(jsonPath("$.rol").value("ROLE_USER"));
    }

    @Test
    public void testGetAllUsuarios() throws Exception {
        List<Usuario> usuarios = List.of(usuario);
        when(usuarioService.getAllUsuarios()).thenReturn(usuarios);

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("Juan"))
                .andExpect(jsonPath("$[0].email").value("juan@example.com"))
                .andExpect(jsonPath("$[0].password").value("password123"))
                .andExpect(jsonPath("$[0].rol").value("ROLE_USER"));
    }

    @Test
    public void testUpdateUsuario() throws Exception {
        doNothing().when(usuarioService).updateUsuario(anyLong(), any(Usuario.class));

        mockMvc.perform(put("/api/v1/usuarios/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@example.com"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andExpect(jsonPath("$.rol").value("ROLE_USER"));
    }

    @Test
    public void testGetUsuarioByEmail() throws Exception {
        when(usuarioService.getUsuarioByEmail("juan@example.com")).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/v1/usuarios/email/juan@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@example.com"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andExpect(jsonPath("$.rol").value("ROLE_USER"));
    }

    
    @Test
    public void testDeleteUsuario() throws Exception {
        doNothing().when(usuarioService).deleteUsuario(1L);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isOk());
    }

    

}
