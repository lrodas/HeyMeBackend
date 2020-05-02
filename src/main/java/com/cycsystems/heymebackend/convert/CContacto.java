package com.cycsystems.heymebackend.convert;

import com.cycsystems.heymebackend.models.entity.Contacto;

public class CContacto {

    public static com.cycsystems.heymebackend.common.Contacto EntityToModel(Contacto entity) {
        if (entity != null) {
            com.cycsystems.heymebackend.common.Contacto modelo = new com.cycsystems.heymebackend.common.Contacto();
            modelo.setIdContacto(entity.getIdContacto());
            modelo.setNombre(entity.getNombre());
            modelo.setApellido(entity.getApellido());
            modelo.setDireccion(entity.getDireccion());
            modelo.setEmail(entity.getEmail());
            modelo.setEstado(entity.getEstado());
            modelo.setTelefono(entity.getTelefono());
            modelo.setPais(CPais.EntityToModel(entity.getPais()));
            modelo.setProvincia(CProvincia.EntityToModel(entity.getProvincia()));
            modelo.setGrupo(CGrupo.EntityToModel(entity.getGrupo()));
            modelo.setEmpresa(CEmpresa.EntityToModel(entity.getEmpresa()));
            modelo.setUsuario(CUsuario.EntityToModel(entity.getUsuario()));
            return modelo;
        } else {
            return null;
        }
    }

    public static Contacto ModelToEntity(com.cycsystems.heymebackend.common.Contacto model) {
        if (model != null) {
            Contacto entity = new Contacto();
            entity.setIdContacto(model.getIdContacto());
            entity.setNombre(model.getNombre());
            entity.setApellido(model.getApellido());
            entity.setDireccion(model.getDireccion());
            entity.setEmail(model.getEmail());
            entity.setEstado(model.getEstado());
            entity.setTelefono(model.getTelefono());
            entity.setPais(CPais.ModelToEntity(model.getPais()));
            entity.setProvincia(CProvincia.ModelToEntity(model.getProvincia()));
            entity.setGrupo(CGrupo.ModelToEntity(model.getGrupo()));
            entity.setEmpresa(CEmpresa.ModelToEntity(model.getEmpresa()));
            entity.setUsuario(CUsuario.ModelToEntity(model.getUsuario()));
            return entity;
        } else {
            return null;
        }
    }
}
