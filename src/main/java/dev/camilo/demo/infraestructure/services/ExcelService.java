package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.domain.entities.CustomerEntity;
import dev.camilo.demo.domain.repositories.CustomerRepository;
import dev.camilo.demo.infraestructure.abstract_services.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

/**
 * Service para generar el excel de las ventas totales por customer
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelService implements ReportService {

  //repositorios inyectados
  private final CustomerRepository customerRepository;

  @Override
  public byte[] readFile() {
    try {
      /*crear reporte*/
      this.createReport();
      var path = Paths.get(REPORTS_PATH,String.format(FILE_NAME,LocalDate.now().getMonth())).toAbsolutePath();
      return Files.readAllBytes(path);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  //private methods
  private void createReport() {
    /*crear libro*/
    var workbook = new XSSFWorkbook();
    /*crear pagina*/
    var sheet = workbook.createSheet(SHEET_NAME);

    /*headers longitud*/
    sheet.setColumnWidth(0, 5000);
    sheet.setColumnWidth(1, 7000);
    sheet.setColumnWidth(2, 3000);

    /*asignar en la primera fila estilos*/
    var header = sheet.createRow(0);
    var headerStyle = workbook.createCellStyle();
    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    /*tipo de letra de libro*/
    var font = workbook.createFont();
    font.setFontName(FONT_TYPE);
    font.setFontHeightInPoints((short) 16);
    font.setBold(true);
    headerStyle.setFont(font);

    /*contenido y estilo header primera columna*/
    var headerCell = header.createCell(0);
    headerCell.setCellValue(COLUMN_CUSTOMER_ID);
    headerCell.setCellStyle(headerStyle);

    /*contenido y estilo header segunda columna*/
    headerCell = header.createCell(1);
    headerCell.setCellValue(COLUMN_CUSTOMER_NAME);
    headerCell.setCellStyle(headerStyle);

    /*contenido y estilo header tercera columna*/
    headerCell = header.createCell(2);
    headerCell.setCellValue(COLUMN_CUSTOMER_PURCHASES);
    headerCell.setCellStyle(headerStyle);

    /*estilo para body*/
    var style = workbook.createCellStyle();
    style.setWrapText(true);

    /*traemos los customers*/
    var customers = this.customerRepository.findAll();

    /*posicion primera de body*/
    var rowPos = 1;

    /*asignando valores al body*/
    for (CustomerEntity customer: customers) {
      var row = sheet.createRow(rowPos);
      var cell = row.createCell(0);
      cell.setCellValue(customer.getDni());
      cell.setCellStyle(style);

      cell = row.createCell(1);
      cell.setCellValue(customer.getFullName());
      cell.setCellStyle(style);

      cell = row.createCell(2);
      cell.setCellValue(intGetTotalPurchase(customer));
      cell.setCellStyle(style);

      rowPos ++;
    }

    /*creacion de archivo con su nombre y localizacion*/
    var report = new File(String.format(REPORTS_PATH_WITH_NAME, LocalDate.now().getMonth()));
    var path = report.getAbsolutePath();
    var fileLocation = path + FILE_TYPE;

    /*escribimos el archivo en el computador*/
    try (var outputStream = new FileOutputStream(fileLocation)) {
      workbook.write(outputStream);
      workbook.close();
    } catch (IOException e) {
      log.error("Cant create Excel", e);
      throw new RuntimeException();
    }
  }

  /**
   * Metodo para traer sumatoria total de vuelos y reservaciones
   *
   * @param customer CustomerEntity
   * @return int
   */
  private static int intGetTotalPurchase(CustomerEntity customer) {
    return customer.getTotalLodgings() + customer.getTotalFlights();
  }

  //constantes
  /**
   * nombre pagina
   */
  private static final String SHEET_NAME = "Customer total sales";
  /**
   * tipo letra documento
   */
  private static final String FONT_TYPE = "Arial";
  /**
   * nombre primera columna
   */
  private static final String COLUMN_CUSTOMER_ID = "id";
  /**
   * nombre segunda columna
   */
  private static final String COLUMN_CUSTOMER_NAME = "name";
  /**
   * nombre tercera columna
   */
  private static final String COLUMN_CUSTOMER_PURCHASES = "purchases";
  /**
   * directorio y nombre de archivo
   */
  private static final String REPORTS_PATH_WITH_NAME = "reports/Sales-%s";
  /**
   * path de guardado
   */
  private static final String REPORTS_PATH = "reports";
  /**
   * extension de documento generado
   */
  private static final String FILE_TYPE = ".xlsx";
  /**
   * nombre de archivo de documento generado
   */
  private static final String FILE_NAME = "Sales-%s.xlsx";
}
