package com.cycsystems.heymebackend.reportControllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cycsystems.heymebackend.util.Constants;

@RestController
@RequestMapping("/api/" + Constants.VERSION + "/template")
public class TemplatesController {

    private Logger LOG = LogManager.getLogger(ReportNotificationController.class);

    @Value("${file.template.excel.contact}")
    private String RUTA_TEMPLATE_EXCEL_CONTACTOS;

    @Async
    @PostMapping("/contacts")
    public ListenableFuture<ResponseEntity<?>> descargaPlantillaContactos() throws IOException {
        LOG.info("METHOD: descargaPlantillaContactos() --PATH: " + this.RUTA_TEMPLATE_EXCEL_CONTACTOS);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=carga_masiva_contactos.xlsx");

        File file = new File(this.RUTA_TEMPLATE_EXCEL_CONTACTOS);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return new AsyncResult<>(ResponseEntity.ok()
            .headers(headers)
            .contentLength(file.length())
            .body(resource));
    }
}
