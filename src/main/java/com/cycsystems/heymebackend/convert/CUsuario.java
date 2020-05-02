package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Usuario;

public class CUsuario {

    public static Usuario ModelToEntity(com.cycsystems.heymebackend.common.Usuario model) {
        if (model != null) {
            Usuario entity = new Usuario();
            entity.setIdUsuario(model.getIdUsuario());
            entity.setEnabled(model.getEnabled());
            entity.setEstadoUsuario(CEstadoUsuario.ModelToEntity(model.getEstadoUsuario()));
            entity.setRole(CRole.ModelToEntity(model.getRole()));
            entity.setEmpresa(CEmpresa.ModelToEntity(model.getEmpresa()));
            entity.setApellidos(model.getApellidos());
            entity.setNombres(model.getNombres());
            entity.setPassword(model.getPassword());
            entity.setDireccion(model.getDireccion());
            entity.setImg(model.getImg());
            entity.setTelefono(model.getTelefono());
            entity.setUsername(model.getUsername());
            entity.setGenero(CGenero.ModelToEntity(model.getGenero()));
            return entity;
        } else {
            return null;
        }
    }

    public static com.cycsystems.heymebackend.common.Usuario EntityToModel(Usuario entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Usuario model = new com.cycsystems.heymebackend.common.Usuario();
            model.setIdUsuario(entity.getIdUsuario());
            model.setEnabled(entity.getEnabled());
            model.setEstadoUsuario(CEstadoUsuario.EntityToModel(entity.getEstadoUsuario()));
            model.setRole(CRole.EntityToModel(entity.getRole()));
            model.setEmpresa(CEmpresa.EntityToModel(entity.getEmpresa()));
            model.setApellidos(entity.getApellidos());
            model.setNombres(entity.getNombres());
            model.setDireccion(entity.getDireccion());
            model.setImg(entity.getImg());
            model.setTelefono(entity.getTelefono());
            model.setUsername(entity.getUsername());
            model.setGenero(CGenero.EntityToModel(entity.getGenero()));
            model.setFechaAlta(entity.getFechaAlta());
            return model;
        } else {
            return null;
        }
    }
}
