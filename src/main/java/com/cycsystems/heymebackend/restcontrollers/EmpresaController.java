package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.convert.CEmpresa;
import com.cycsystems.heymebackend.input.EmpresaRequest;
import com.cycsystems.heymebackend.models.entity.Empresa;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IEmpresaService;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.output.EmpresaResponse;
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

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/company")
public class EmpresaController {

    private Logger LOG = LogManager.getLogger(EmpresaController.class);

    @Autowired
    private IEmpresaService empresaService;

    @Autowired
    private IUsuarioService usuarioService;

    @Async
    @PostMapping("/findByUser")
    public ListenableFuture<ResponseEntity<EmpresaResponse>> obtenerEmpresaPorUsuario(@RequestBody EmpresaRequest input) {
        LOG.info("METHOD: obtenerEmpresaPorUsuario() --PARAMS: " + input);
        EmpresaResponse output = new EmpresaResponse();

        if (input.getIdUsuario() == null || input.getIdUsuario() <= 0) {
            output.setCodigo(Response.USER_NOT_EMPTY.getCodigo());
            output.setDescripcion(Response.USER_NOT_EMPTY.getMessage());
            output.setIndicador(Response.USER_NOT_EMPTY.getIndicador());
        } else {

            Usuario usuario = this.usuarioService.findById(input.getIdUsuario());

            if (usuario == null) {
                output.setCodigo(Response.USER_NOT_EXIST_ERROR.getCodigo());
                output.setDescripcion(Response.USER_NOT_EMPTY.getMessage());
                output.setIndicador(Response.USER_NOT_EXIST_ERROR.getIndicador());
            } else {
                output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
                output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
                output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
                output.setEmpresa(CEmpresa.EntityToModel(usuario.getEmpresa()));
            }
        }

        return new AsyncResult<>(ResponseEntity.ok(output));
    }

    @Async
    @PostMapping("/save")
    public ListenableFuture<ResponseEntity<EmpresaResponse>> guardarEmpresa(@RequestBody EmpresaRequest input) {
        LOG.info("METHOD: guardarEmpresa() --PARAMS: " + input);
        EmpresaResponse output = new EmpresaResponse();

        if (input.getEmpresa() == null) {
            output.setCodigo(Response.COMPANY_NOT_EMPTY_ERROR.getCodigo());
            output.setDescripcion(Response.COMPANY_NOT_EMPTY_ERROR.getMessage());
            output.setIndicador(Response.COMPANY_NOT_EMPTY_ERROR.getIndicador());
        } else if (input.getEmpresa().getIdEmpresa() == null || input.getEmpresa().getIdEmpresa() <= 0) {
            output.setCodigo(Response.COMPANY_ID_NOT_EMPTY.getCodigo());
            output.setDescripcion(Response.COMPANY_ID_NOT_EMPTY.getMessage());
            output.setIndicador(Response.COMPANY_ID_NOT_EMPTY.getIndicador());
        } else if (input.getEmpresa().getNombreEmpresa() == null || input.getEmpresa().getNombreEmpresa().isEmpty()) {
            output.setCodigo(Response.COMPANY_NAME_NOT_EMPTY.getCodigo());
            output.setDescripcion(Response.COMPANY_NAME_NOT_EMPTY.getMessage());
            output.setIndicador(Response.COMPANY_NAME_NOT_EMPTY.getIndicador());
        } else if (input.getEmpresa().getDireccion() == null || input.getEmpresa().getDireccion().isEmpty()) {
            output.setCodigo(Response.COMPANY_ADDRESS_NOT_EMPTY.getCodigo());
            output.setDescripcion(Response.COMPANY_ADDRESS_NOT_EMPTY.getMessage());
            output.setIndicador(Response.COMPANY_ADDRESS_NOT_EMPTY.getIndicador());
        } else if (input.getEmpresa().getTelefono() == null || input.getEmpresa().getTelefono().isEmpty()) {
            output.setCodigo(Response.COMPANY_PHONE_NOT_EMPTY.getCodigo());
            output.setDescripcion(Response.COMPANY_PHONE_NOT_EMPTY.getMessage());
            output.setIndicador(Response.COMPANY_PHONE_NOT_EMPTY.getIndicador());
        } else {

            Empresa empresa = this.empresaService.findById(input.getEmpresa().getIdEmpresa());

            if (empresa == null) {
                output.setCodigo(Response.USER_ERROR_COMPANY_NOT_EXIST.getCodigo());
                output.setDescripcion(Response.USER_ERROR_COMPANY_NOT_EXIST.getMessage());
                output.setIndicador(Response.USER_ERROR_COMPANY_NOT_EXIST.getIndicador());
            } else {
                 empresa.setNombreEmpresa(input.getEmpresa().getNombreEmpresa());
                 empresa.setDireccion(input.getEmpresa().getDireccion());
                 empresa.setTelefono(input.getEmpresa().getTelefono());

                 empresa = this.empresaService.save(empresa);

                 output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
                 output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
                 output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
                 output.setEmpresa(CEmpresa.EntityToModel(empresa));
            }
        }
        return new AsyncResult<>(ResponseEntity.ok(output));
    }
}
