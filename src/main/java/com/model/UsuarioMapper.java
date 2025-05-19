package com.model;

import com.model.dto.UsuarioDto;
import com.model.entity.UsuarioEntity;


public class UsuarioMapper {

    public static UsuarioDto toDto(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }

        return new UsuarioDto(
            entity.getIdUsuario(),
            entity.getNombreUsuario(),
            entity.getAppaterno(),
            entity.getEmailUsuario()          
       
        );
    }

    public static UsuarioEntity toEntity(UsuarioDto dto) {
        if (dto == null) {
            return null;
        }
        UsuarioEntity entity = new UsuarioEntity();
        entity.setIdUsuario(dto.getIdUsuario());
        entity.setNombreUsuario(dto.getNombreUsuario());
        entity.setAppaterno(dto.getAppaterno());
        entity.setEmailUsuario(dto.getEmailUsuario());
        return entity;
    }

    
}
