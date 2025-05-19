package com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Usuario;
import com.model.dto.UsuarioDto;
import com.model.entity.UsuarioEntity;
import com.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public String crearUsuario(Usuario usuario) {
        if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
            return "El usuario ya existe";
        }
        UsuarioEntity usuarioNuevo = mapToEntity(usuario);
        usuarioRepository.save(usuarioNuevo);
        return "Usuario creado exitosamente";
    }

    public Usuario obtenerUsuario(String nombreUsuario) {
        UsuarioEntity usuario = usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
        if (usuario != null) {
            return mapToModel(usuario);
        }
        return null;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public String actualizarUsuario(String nombreUsuario, Usuario usuario) {
        UsuarioEntity existente = usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
        if (existente != null) {
            existente.setNombreUsuario(usuario.getNombreUsuario());
            existente.setAppaterno(usuario.getAppaterno());
            existente.setAppaterno(usuario.getAppaterno());
            existente.setEmailUsuario(usuario.getEmailUsuario());
            existente.setDireccionUsuario(usuario.getDireccionUsuario());
            existente.setTelefonoUsuario(usuario.getTelefonoUsuario());
            existente.setGeneroUsuario(usuario.getGeneroUsuario());
            existente.setContrasenaUsuario(usuario.getContrasenaUsuario());
            return "Usuario actualizado correctamente";
        }
        return "Usuario no encontrado";
    }

    @Transactional
    public String eliminarUsuario(String nombreUsuario) {
        UsuarioEntity existente = usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
        if (existente != null) {
            usuarioRepository.delete(existente);
            return "Usuario eliminado correctamente";
        }
        return "Usuario no encontrado";
    }

    // Métodos auxiliares para mapear entre Producto y ProductoEntity
    private UsuarioEntity mapToEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNombreUsuario(usuario.getNombreUsuario());
        entity.setAppaterno(usuario.getAppaterno());
        entity.setApmaterno(usuario.getAppaterno());
        entity.setEmailUsuario(usuario.getEmailUsuario());
        entity.setDireccionUsuario(usuario.getDireccionUsuario());
        entity.setTelefonoUsuario(usuario.getTelefonoUsuario());
        entity.setGeneroUsuario(usuario.getGeneroUsuario());
        entity.setContrasenaUsuario(usuario.getContrasenaUsuario());
        return entity;
    }

    private Usuario mapToModel(UsuarioEntity entity) {
        return new Usuario(
                entity.getIdUsuario(),
                entity.getNombreUsuario(),
                entity.getAppaterno(),
                entity.getApmaterno(),
                entity.getEmailUsuario(),
                entity.getDireccionUsuario(),
                entity.getTelefonoUsuario(),
                entity.getGeneroUsuario(),
                entity.getContrasenaUsuario()

        );
    }

    public UsuarioDto obtenerUsuarioPorId(Long idUsuario) {
        try {
            UsuarioEntity usuario = usuarioRepository.findById(idUsuario).orElse(null);
            if (usuario == null) {
                return null;
            }

            UsuarioDto nuevoUsuario = new UsuarioDto();
            nuevoUsuario.setNombreUsuario(usuario.getNombreUsuario());
            nuevoUsuario.setAppaterno(usuario.getAppaterno());
            nuevoUsuario.setEmailUsuario(usuario.getEmailUsuario());

            return nuevoUsuario;
        } catch (Exception e) {
            return null;
        }
    }
}
