package com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Usuario;
import com.model.dto.UsuarioDto;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private com.service.UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Este endpoint permite agregar usuarios")
    public ResponseEntity<String> crearUsuario(@Valid @RequestBody Usuario usuario) {
        usuarioService.crearUsuario(usuario);
        return ResponseEntity.ok("Usuario creado exitosamente");
    }

    @GetMapping("/{nombreUsuario}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String nombreUsuario) {
        Usuario usuario = usuarioService.obtenerUsuario(nombreUsuario);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{nombreUsuario}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable String nombreUsuario,
            @Valid @RequestBody Usuario usuario) {
        try {
            String resultado = usuarioService.actualizarUsuario(nombreUsuario, usuario);

            if (resultado.equals("Usuario no encontrado")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", "Usuario no encontrado"));

            }
            return ResponseEntity.ok(Map.of("mensaje", "Usuario actualizado exitosamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al actualizar el Usuario" + e.getMessage()));

        }
    }

    @DeleteMapping("/{nombreUsuario}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable String nombreUsuario) {
        String mensaje = usuarioService.eliminarUsuario(nombreUsuario);
        if ("Usuario eliminado correctamente".equals(mensaje)) {
            return ResponseEntity.ok(mensaje);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/obtenerUsuarioDto/{IdUsuario}")
    public ResponseEntity<UsuarioDto> obtenerUsuarioDto(@PathVariable Long IdUsuario) {
        UsuarioDto usuarioDto = usuarioService.obtenerUsuarioPorId(IdUsuario);
        if (usuarioDto != null) {
            return ResponseEntity.ok(usuarioDto);
        }
        return ResponseEntity.notFound().build();
    }

}
