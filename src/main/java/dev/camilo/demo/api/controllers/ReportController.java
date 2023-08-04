package dev.camilo.demo.api.controllers;

import dev.camilo.demo.infraestructure.abstract_services.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller para Report
 */
@Tag(name = "Report")
@RestController
@RequestMapping(path = "report")
@RequiredArgsConstructor
public class ReportController {

  //service inyectado
  private final ReportService reportService;

  @GetMapping
  public ResponseEntity<Resource> get(){
    var headers = new HttpHeaders();

    /*setear header*/
    headers.setContentType(FORCE_DOWNLOAD);
    headers.set(HttpHeaders.CONTENT_DISPOSITION, FORCE_DOWNLOAD_HEADER_VALUE);
    /*serializar archivo*/
    var fileInBytes = this.reportService.readFile();
    ByteArrayResource response = new ByteArrayResource(fileInBytes);

    return ResponseEntity.ok()
        .headers(headers)
        .contentLength(fileInBytes.length)
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(response);

  }

  //constantes
  /**
   * constante para descarga automatica
   */
  private static final MediaType FORCE_DOWNLOAD =
      new MediaType("application", "force-download");
  /**
   * constante para agregar en el header informacion de que se incluye un
   * archivo report.xlsx
   */
  private static final String FORCE_DOWNLOAD_HEADER_VALUE = "attachment; filename=report.xlsx";
}
