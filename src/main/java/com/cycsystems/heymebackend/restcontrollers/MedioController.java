package com.cycsystems.heymebackend.restcontrollers;

import com.cycsystems.heymebackend.convert.CCanal;
import com.cycsystems.heymebackend.input.CanalRequest;
import com.cycsystems.heymebackend.models.entity.Canal;
import com.cycsystems.heymebackend.models.service.ICanalService;
import com.cycsystems.heymebackend.output.CanalResponse;
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
@RequestMapping("/api/" + Constants.VERSION + "/medium")
public class MedioController {

    private Logger LOG = LogManager.getLogger(MedioController.class);

    @Autowired
    private ICanalService canalService;

    @Async
    @PostMapping("/findAll")
    public ListenableFuture<ResponseEntity<CanalResponse>> findAll(@RequestBody CanalRequest request) {
        LOG.info("METHOD: findAll() --PARAMS: " + request);

        CanalResponse response = new CanalResponse();
        response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
        response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
        response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
        response.setCanals(this.canalService
                .findAll()
                .stream()
                .map(canal -> CCanal.EntityToModel(canal)).collect(Collectors.toList()));
        return new AsyncResult<>(ResponseEntity.ok(response));
    }

    @Async
    @PostMapping("/findById")
    public ListenableFuture<ResponseEntity<CanalResponse>> findbyId(@RequestBody CanalRequest request) {
        LOG.info("METHOD: findById() --PARAMS: " + request);
        CanalResponse response = new CanalResponse();

        if (request.getCanal() == null ||
                request.getCanal().getIdCanal() == null ||
                request.getCanal().getIdCanal() <= 0) {
            response.setCodigo(Response.MEDIUM_ID_NOT_EMPTY.getCodigo());
            response.setDescripcion(Response.MEDIUM_ID_NOT_EMPTY.getMessage());
            response.setIndicador(Response.MEDIUM_ID_NOT_EMPTY.getIndicador());
        } else  {

            Canal canal = this.canalService.findById(request.getCanal().getIdCanal());

            if (canal == null) {
                response.setCodigo(Response.MEDIUM_NOT_EXIST.getCodigo());
                response.setDescripcion(Response.MEDIUM_NOT_EXIST.getMessage());
                response.setIndicador(Response.MEDIUM_NOT_EXIST.getIndicador());
            } else {
                response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
                response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
                response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
                response.setCanal(CCanal.EntityToModel(canal));
            }
        }

        return new AsyncResult<>(ResponseEntity.ok(response));
    }

    @Async
    @PostMapping("/findByStatus")
    public ListenableFuture<ResponseEntity<CanalResponse>> findByStatus(@RequestBody CanalRequest request) {

        LOG.info("METHOD: findByStatus() --PARAMS: " + request);
        CanalResponse response = new CanalResponse();

        if (request.getCanal() == null || request.getCanal().getEstado() == null) {
            response.setCodigo(Response.MEDIUM_STATUS_NOT_EMPTY.getCodigo());
            response.setDescripcion(Response.MEDIUM_STATUS_NOT_EMPTY.getMessage());
            response.setIndicador(Response.MEDIUM_STATUS_NOT_EMPTY.getIndicador());
        } else {
            List<Canal> canales = this.canalService.findByStatus(request.getCanal().getEstado());

            response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
            response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
            response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
            response.setCanals(canales
                .stream()
                .map(canal -> CCanal.EntityToModel(canal))
                .collect(Collectors.toList()));

        }
        return new AsyncResult<>(ResponseEntity.ok(response));
    }

    @Async
    @PostMapping("/saveCanal")
    public ListenableFuture<ResponseEntity<CanalResponse>> saveCanal(@RequestBody CanalRequest request) {
        LOG.info("METHOD: saveCanal() --PARAMS " + request);
        CanalResponse response = new CanalResponse();

        if (request.getCanal() == null) {
            response.setCodigo(Response.MEDIUM_DATA_NOT_EMPTY.getCodigo());
            response.setDescripcion(Response.MEDIUM_DATA_NOT_EMPTY.getMessage());
            response.setIndicador(Response.MEDIUM_DATA_NOT_EMPTY.getIndicador());
        } else if (request.getCanal().getNombre() == null || request.getCanal().getNombre().isEmpty()) {
            response.setCodigo(Response.MEDIUM_NAME_NOT_EMPTY.getCodigo());
            response.setDescripcion(Response.MEDIUM_NAME_NOT_EMPTY.getMessage());
            response.setIndicador(Response.MEDIUM_NAME_NOT_EMPTY.getIndicador());
        } else {
            request.getCanal().setEstado(true);
            Canal canal = this.canalService.save(CCanal.ModelToEntity(request.getCanal()));

            if (canal != null && canal.getIdCanal() != null) {
                response.setCodigo(Response.SUCCESS_RESPONSE.getCodigo());
                response.setDescripcion(Response.SUCCESS_RESPONSE.getMessage());
                response.setIndicador(Response.SUCCESS_RESPONSE.getIndicador());
                response.setCanal(CCanal.EntityToModel(canal));
            } else {
                response.setCodigo(Response.MEDIUM_SAVE_ERROR.getCodigo());
                response.setDescripcion(Response.MEDIUM_SAVE_ERROR.getMessage());
                response.setIndicador(Response.MEDIUM_SAVE_ERROR.getIndicador());
            }
        }
        return new AsyncResult<>(ResponseEntity.ok(response));
    }
}
