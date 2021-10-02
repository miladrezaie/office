package com.javasampleapproach.jqueryboostraptable.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QRCodeController {
	
	private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/images/QRCode.png";

	
    @GetMapping(value = "/genrateAndDownloadQRCode/{codeText}")
		public void download(
				@PathVariable("codeText") String codeText)
			    throws Exception {
			        QRCodeGenerator.generateQRCodeImage(codeText, 350, 350, QR_CODE_IMAGE_PATH);
			    }

    @GetMapping(value = "/genrateQRCode/{codeText}")
   	public ResponseEntity<byte[]> generateQRCode(
   			@PathVariable("codeText") String codeText)
   		    throws Exception {
   		        return ResponseEntity.status(HttpStatus.OK).body(QRCodeGenerator.getQRCodeImage(codeText, 350, 350));
   		    }
}

