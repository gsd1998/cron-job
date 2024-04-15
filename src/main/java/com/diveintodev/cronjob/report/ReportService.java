package com.diveintodev.cronjob.report;

import com.diveintodev.cronjob.dto.User;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
@Slf4j
public class ReportService {

    public byte[] generateReport() throws IOException {

        log.info("Inside Generating Report class");

        List<User> users = convertCsvToJavaBean();
        byte[] report =  null;
        if(users !=null){

                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet();

                createHeaderRow(sheet);

                XSSFCellStyle cellStyle = workbook.createCellStyle();
                XSSFFont font = workbook.createFont();
                font.setFontHeight(14);
                cellStyle.setFont(font);

                int rowNum =1;

                for (User user : users) {
                    createDataRows(sheet, rowNum, user, cellStyle);
                    rowNum++;
                }

                ByteArrayOutputStream bos =  new ByteArrayOutputStream();

                try {
                    workbook.write(bos);
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    bos.close();
                }
                report =  bos.toByteArray();
        }
        return report;
    }

    private void createDataRows(XSSFSheet sheet, int rowNum, User user, XSSFCellStyle cellStyle) {
        int columnNum = 0;
        XSSFRow row = sheet.createRow(rowNum);

        XSSFCell cell1 = row.createCell(columnNum++);
        cell1.setCellValue((Integer)user.getId());
        cell1.setCellStyle(cellStyle);

        XSSFCell cell2 =row.createCell(columnNum++);
        cell2.setCellValue((String)user.getFirstName());
        cell2.setCellStyle(cellStyle);

        XSSFCell cell3 =row.createCell(columnNum++);
        cell3.setCellValue((String)user.getLastName());
        cell3.setCellStyle(cellStyle);

        XSSFCell cell4 =row.createCell(columnNum++);
        cell4.setCellValue((String)user.getEmail());
        cell4.setCellStyle(cellStyle);

        XSSFCell cell5 =row.createCell(columnNum++);
        cell5.setCellValue((String)user.getGender());
        cell5.setCellStyle(cellStyle);

        XSSFCell cell6 =row.createCell(columnNum++);
        cell6.setCellValue((String)user.getIpAddress());
        cell6.setCellStyle(cellStyle);
    }

    private void createHeaderRow(XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("First Name");
        row.createCell(2).setCellValue("Last Name");
        row.createCell(3).setCellValue("Email");
        row.createCell(4).setCellValue("Gender");
        row.createCell(5).setCellValue("IP Address");
    }

    private List<User> convertCsvToJavaBean() {

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("users.csv").getInputStream()))){

            CsvToBean<User> csvBean = new CsvToBeanBuilder<User>(reader)
                    .withType(User.class)
                    .build();
            return csvBean.parse();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
