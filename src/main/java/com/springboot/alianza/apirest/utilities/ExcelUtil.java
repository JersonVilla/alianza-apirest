package com.springboot.alianza.apirest.utilities;

import com.springboot.alianza.apirest.dto.ClienteDto;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class ExcelUtil {

	public static void exportToExcel(HttpServletResponse response, List<ClienteDto> listClients) throws IOException {
		response.setContentType("text/csv");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=clients_" + currentDateTime + ".csv";
		response.setHeader(headerKey, headerValue);

		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader = { "ID", "sharedKey", "businessId", "email", "phone", "dataAdd" };
		String[] nameMapping = { "id", "sharedKey", "businessId", "email", "phone", "dataAdd" };

		csvWriter.writeHeader(csvHeader);

		for (ClienteDto cliente : listClients) {
			csvWriter.write(cliente, nameMapping);
		}

		csvWriter.close();
	}

}
