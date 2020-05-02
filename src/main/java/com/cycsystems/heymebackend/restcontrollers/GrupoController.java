package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.convert.CGrupo;
import com.cycsystems.heymebackend.input.GrupoRequest;
import com.cycsystems.heymebackend.models.entity.Grupo;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IGrupoService;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.output.GrupoResponse;
import com.cycsystems.heymebackend.util.Constants;
import com.cycsystems.heymebackend.util.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/group")
public class GrupoController {

    private Logger LOG = LogManager.getLogger(GrupoController.class);

    @Autowired
    private IGrupoService grupoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Async
    @PostMapping("/save")
    public ListenableFuture<ResponseEntity<GrupoResponse>> save(@RequestBody GrupoRequest request) {
        LOG.info("METHOD: save() --PARAMS: " + request);
        GrupoResponse response = new GrupoResponse();

        if (request.getGrupo() == null) {
            response.setCodigo(Response.GROUP_NOT_EMPTY.getCodigo());
            response.setDescripcion(Response.GROUP_NOT_EMPTY.getMessage());
            response.setIndicador(Response.GROUP_NOT_EMPTY.getIndicador());
        } else if (request.getGrupo().getNombre() == null || request.getGrupo().getNombre().trim().isEmpty()) {
            response.setCodigo(Response.GROUP_NAME_NOT_EMPTY.getCodigo());
            response.setDescripcion(Response.GROUP_NAME_NOT_EMPTY.getMessage());
            response.setIndicador(Response.GROUP_NAME_NOT_EMPTY.getIndicador());
        } else {
            Usuario usuario = this.usuarioService.findById(request.getIdUsuario());
            if (usuario == null) {
                response.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
                response.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
                response.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
            } else {
                Grupo grupo = CGrupo.ModelToEntity(request.getGrupo());
                grupo.setEmpresa(usuario.getEmpresa());
                grupo = this.grupoService.save(grupo);

                if (grupo == null) {
                    response.setCodigo(Response.GROUP_SAVE_ERROR.getCodigo());
                    response.setDescripcion(Response.GROUP_SAVE_ERROR.getMessage());
                    response.setIndicador(Response.GROUP_SAVE_ERROR.getIndicador());
                } else {
                    response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
                    response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
                    response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
                    response.setGrupo(CGrupo.EntityToModel(grupo));
                }
            }
        }
        return new AsyncResult<>(ResponseEntity.ok(response));
    }

    @Async
    @PostMapping("/findById")
    public ListenableFuture<ResponseEntity<GrupoResponse>> findById(@RequestBody GrupoRequest request) {
        LOG.info("METHOD: findById() --PARAMS: " + request);
        GrupoResponse response = new GrupoResponse();

        if (request.getGrupo() == null) {
            response.setCodigo(Response.GROUP_NOT_EMPTY.getCodigo());
            response.setDescripcion(Response.GROUP_NOT_EMPTY.getMessage());
            response.setIndicador(Response.GROUP_NOT_EMPTY.getIndicador());
        } else if (request.getGrupo().getIdGrupo() == null || request.getGrupo().getIdGrupo().compareTo(0) == 0) {
            response.setCodigo(Response.GROUP_ID_NOT_EMPTY.getCodigo());
            response.setDescripcion(Response.GROUP_ID_NOT_EMPTY.getMessage());
            response.setIndicador(Response.GROUP_ID_NOT_EMPTY.getIndicador());
        } else  {
            Grupo grupo = this.grupoService.findById(request.getGrupo().getIdGrupo());

            if (grupo == null) {
                response.setCodigo(Response.GROUP_NOT_EXIST.getCodigo());
                response.setDescripcion(Response.GROUP_NOT_EXIST.getMessage());
                response.setIndicador(Response.GROUP_NOT_EXIST.getIndicador());
            } else {
                response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
                response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
                response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
                response.setGrupo(CGrupo.EntityToModel(grupo));
            }
        }

        return new AsyncResult<>(ResponseEntity.ok(response));
    }

    @Async
    @PostMapping("/findAll")
    public ListenableFuture<ResponseEntity<GrupoResponse>> findAll(@RequestBody GrupoRequest request) {
        LOG.info("METHOD: findAll() --PARAMS: " + request);
        GrupoResponse response = new GrupoResponse();

        if (request.getGrupo() == null) {
            response.setCodigo(Response.GROUP_NOT_EMPTY.getCodigo());
            response.setDescripcion(Response.GROUP_NOT_EMPTY.getMessage());
            response.setIndicador(Response.GROUP_NOT_EMPTY.getIndicador());
        } else {
            Usuario usuario = this.usuarioService.findById(request.getIdUsuario());
            if (usuario == null) {
                response.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
                response.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
                response.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
            } else {
                List<com.cycsystems.heymebackend.common.Grupo> grupos = this.grupoService
                        .findAll(usuario.getEmpresa().getIdEmpresa())
                        .stream()
                        .map(grupo -> CGrupo.EntityToModel(grupo))
                        .collect(Collectors.toList());

                response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
                response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
                response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
                response.setGrupos(grupos);
            }
        }
        return new AsyncResult<>(ResponseEntity.ok(response));
    }

    @Async
    @PostMapping("/findByName")
    public ListenableFuture<ResponseEntity<GrupoResponse>> findByName(@RequestBody GrupoRequest request) {
        LOG.info("METHOD: findByName() --PARAMS: " + request);
        GrupoResponse response = new GrupoResponse();

        if (request.getGrupo() == null) {
            response.setCodigo(Response.GROUP_NOT_EMPTY.getCodigo());
            response.setDescripcion(Response.GROUP_NOT_EMPTY.getMessage());
            response.setIndicador(Response.GROUP_NOT_EMPTY.getIndicador());
        } else if (request.getGrupo().getNombre() == null ||
            request.getGrupo().getNombre().isEmpty()) {
            response.setCodigo(Response.GROUP_NAME_NOT_EMPTY.getCodigo());
            response.setDescripcion(Response.GROUP_NAME_NOT_EMPTY.getMessage());
            response.setIndicador(Response.GROUP_NAME_NOT_EMPTY.getIndicador());
        } else {

            Usuario usuario = this.usuarioService.findById(request.getIdUsuario());
            if (usuario == null) {
                response.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
                response.setDescripcion(Response.USER_NOT_EXIST_ERROR.getMessage());
                response.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
            } else {

                List<com.cycsystems.heymebackend.common.Grupo> grupos = this.grupoService
                        .findByName(request.getGrupo().getNombre(), usuario.getEmpresa().getIdEmpresa())
                        .stream()
                        .filter(grupo -> grupo.getEmpresa().getIdEmpresa() == request.getGrupo().getEmpresa().getIdEmpresa())
                        .map(grupo -> CGrupo.EntityToModel(grupo))
                        .collect(Collectors.toList());

                response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
                response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
                response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
                response.setGrupos(grupos);
            }
        }

        return new AsyncResult<>(ResponseEntity.ok(response));
    }
}
