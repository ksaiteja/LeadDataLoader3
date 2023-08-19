package com.fsdbackend.Login;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {
    public static List<Lead> parseCSV(MultipartFile file) throws IOException {
        List<Lead> leads = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) { // Assuming 6 columns: firstname, lastname, email, phone, status, assignedTo
                    Lead lead = new Lead(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                    leads.add(lead);
                }
            }
        }
        
        return leads;
    }
    public static ByteArrayResource generateCSV(List<Lead> leads) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), CSVFormat.DEFAULT);

        // Write CSV headers
        csvPrinter.printRecord("First Name", "Last Name", "Email", "Phone", "Status", "Assigned To");

        // Write bad leads
        for (Lead lead : leads) {
            csvPrinter.printRecord(
                    lead.getFirstName(), lead.getLastName(), lead.getEmail(),
                    lead.getPhone(), lead.getStatus(), lead.getAssignedTo()
            );
        }

        csvPrinter.flush();
        csvPrinter.close();

        return new ByteArrayResource(outputStream.toByteArray());
    }
}
