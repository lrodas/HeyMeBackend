package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.common.Pais;
import com.cycsystems.heymebackend.input.PaisRequest;
import com.cycsystems.heymebackend.models.service.IPaisService;
import com.cycsystems.heymebackend.output.PaisResponse;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/country")
public class PaisController {

    private Logger LOG = LogManager.getLogger(PaisController.class);

    @Autowired
    private IPaisService paisService;

    @Async
    @PostMapping("/findAll")
    public ListenableFuture<ResponseEntity<PaisResponse>> obtenerPaises(@RequestBody PaisRequest input) {
        LOG.info("METHOD: obtenerPaises() --PARAMS: PaisRequest: " + input);

        PaisResponse output = new PaisResponse();
        List<com.cycsystems.heymebackend.models.entity.Pais> paises = this.paisService.findAllCounties();

        for (com.cycsystems.heymebackend.models.entity.Pais pais: paises) {
            if (output.getPaises() == null) output.setPaises(new ArrayList<>());
            output.getPaises().add(this.mapearModelo(pais));
        }

        output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
        output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
        output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
        return new AsyncResult<>(ResponseEntity.ok(output));
    }

    @Async
    @PostMapping("/findById")
    public ListenableFuture<ResponseEntity<PaisResponse>> obtenerPaisPorId(@RequestBody PaisRequest input) {

        LOG.info("METHOD: obtenerPaisPorId() --PARAMS: paisRequest: " + input);
        PaisResponse output = new PaisResponse();

        if (input.getPais() == null) {
            output.setCodigo(Response.COUNTRY_CONTENT_EMPTY.getCodigo());
            output.setDescripcion(Response.COUNTRY_CONTENT_EMPTY.getMessage());
            output.setIndicador(Response.COUNTRY_CONTENT_EMPTY.getIndicador());
        } else if (input.getPais().getIdPais() == null || input.getPais().getIdPais() <= 0) {
            output.setCodigo(Response.COUNTRY_ID_EMPTY.getCodigo());
            output.setDescripcion(Response.COUNTRY_ID_EMPTY.getMessage());
            output.setIndicador(Response.COUNTRY_ID_EMPTY.getIndicador());
        } else {
            com.cycsystems.heymebackend.models.entity.Pais pais = this.paisService.findCountryById(input.getPais().getIdPais());
            if (pais != null) {
                output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
                output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
                output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
                output.setPais(this.mapearModelo(pais));
            } else {
                output.setCodigo(Response.COUNTRY_NOT_EXIST.getCodigo());
                output.setDescripcion(Response.COUNTRY_NOT_EXIST.getMessage());
                output.setIndicador(Response.COUNTRY_NOT_EXIST.getIndicador());
            }
        }
        return new AsyncResult<>(ResponseEntity.ok(output));
    }

    @Async
    @PostMapping("/findByName")
    public ListenableFuture<ResponseEntity<PaisResponse>> obtenerPaisesPorNombre(@RequestBody PaisRequest input) {
        LOG.info("METHOD: obtenerPaisesPorNombre() --PARAMS: paisRequest: " + input);
        PaisResponse output = new PaisResponse();

        if (input.getPais() == null) {
            output.setCodigo(Response.COUNTRY_CONTENT_EMPTY.getCodigo());
            output.setDescripcion(Response.COUNTRY_CONTENT_EMPTY.getMessage());
            output.setIndicador(Response.COUNTRY_CONTENT_EMPTY.getIndicador());
        } else if (input.getPais().getNombre() == null || input.getPais().getNombre().isEmpty()) {
            output.setCodigo(Response.COUNTRY_NAME_NOT_EMPTY.getCodigo());
            output.setDescripcion(Response.COUNTRY_NAME_NOT_EMPTY.getMessage());
            output.setIndicador(Response.COUNTRY_NAME_NOT_EMPTY.getIndicador());
        } else {
            List<com.cycsystems.heymebackend.models.entity.Pais> paises = this.paisService.findCountryByNameAndEstado(input.getPais().getNombre(), true);

            for (com.cycsystems.heymebackend.models.entity.Pais pais: paises) {
                if (output.getPaises() == null) output.setPaises(new ArrayList<>());
                output.getPaises().add(this.mapearModelo(pais));
            }
            output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
            output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
            output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());

        }

        return new AsyncResult<>(ResponseEntity.ok(output));
    }

    @Async
    @PostMapping("/findByCode")
    public ListenableFuture<ResponseEntity<PaisResponse>> obtenerPaisesPorCodigo(@RequestBody PaisRequest input) {
        LOG.info("METHOD: obtenerPaisesCodigo() --PARAMS: PaisRequest: " + input);
        PaisResponse output = new PaisResponse();

        if (input.getPais() == null) {
            output.setCodigo(Response.COUNTRY_CONTENT_EMPTY.getCodigo());
            output.setDescripcion(Response.COUNTRY_CONTENT_EMPTY.getMessage());
            output.setIndicador(Response.COUNTRY_CONTENT_EMPTY.getIndicador());
        } else if (input.getPais().getCodigo() == null || input.getPais().getCodigo().isEmpty()) {
            output.setCodigo(Response.COUNTRY_CODE_NOT_EMPTY.getCodigo());
            output.setDescripcion(Response.COUNTRY_CODE_NOT_EMPTY.getMessage());
            output.setIndicador(Response.COUNTRY_CODE_NOT_EMPTY.getIndicador());
        } else {
            List<com.cycsystems.heymebackend.models.entity.Pais> paises = this.paisService.findCountryByCodeAndEstado(input.getPais().getCodigo(), true);

            for (com.cycsystems.heymebackend.models.entity.Pais pais: paises) {
                if (output.getPaises() == null) output.setPaises(new ArrayList<>());
                output.getPaises().add(this.mapearModelo(pais));
            }
            output.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
            output.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
            output.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
        }
        return new AsyncResult<>(ResponseEntity.ok(output));
    }

    private Pais mapearModelo(com.cycsystems.heymebackend.models.entity.Pais pais) {

        Pais modelo = new Pais();
        modelo.setIdPais(pais.getIdPais());
        modelo.setNombre(pais.getNombre());
        modelo.setCodigo(pais.getCodigo());
        modelo.setEstado(pais.getEstado());

        return modelo;
    }
}
