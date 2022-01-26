package com.javasampleapproach.jqueryboostraptable.Service;


import java.io.ByteArrayInputStream;
import java.util.Map;


public interface ExportPdfService {
    ByteArrayInputStream exportReceiptPdf(String templateName, Map<String, Object> data);
}