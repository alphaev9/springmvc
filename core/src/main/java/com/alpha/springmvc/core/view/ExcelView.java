package com.alpha.springmvc.core.view;

import com.alpha.springmvc.domain.Backlog;
import com.alpha.springmvc.domain.Cooperator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelView extends AbstractXlsView {
    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        /*设置响应头部信息：指示客户端下载保存文件*/
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=backlog.xlsx");
        Sheet backlogSheet = workbook.createSheet("backlog");
        /*从视图绑定的模型中获取要在excel中显示的对象*/
        Backlog backlog = (Backlog) map.get("backlog");

        Row header = backlogSheet.createRow(0);
        Row content = backlogSheet.createRow(1);
        header.createCell(0).setCellValue("backlogTitle");
        content.createCell(0).setCellValue(backlog.getTitle());

        header.createCell(1).setCellValue("description");
        content.createCell(1).setCellValue(backlog.getDescription());

        header.createCell(2).setCellValue("dueTime");
        content.createCell(2).setCellValue(backlog.getDueTime());

        header.createCell(3).setCellValue("street");
        content.createCell(3).setCellValue(backlog.getAddress().getStreet());
        header.createCell(4).setCellValue("number");
        content.createCell(4).setCellValue(backlog.getAddress().getNumber());

        List<Cooperator> cooperators = backlog.getCooperators();
        for (int i = 0,j=5; i < cooperators.size(); i++,j+=2) {
            header.createCell(j).setCellValue("cooperator"+(i+1)+"_name");
            header.createCell(j+1).setCellValue("cooperator"+(i+1)+"_email");

            content.createCell(j).setCellValue(backlog.getCooperators().get(i).getName());
            content.createCell(j+1).setCellValue(backlog.getCooperators().get(i).getEmail());
        }

    }
}
