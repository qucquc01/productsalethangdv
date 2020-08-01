package com.example.productsale.controller;

import com.example.productsale.dto.ResponseMsg;
import com.example.productsale.exception.StorageException;
import com.example.productsale.msg.Msg;
import com.example.productsale.util.ObjectMapperUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public abstract class BaseResponseController {

    protected abstract Logger getLogger();

    protected ResponseEntity response(int code, String message, Object data) {
        ResponseMsg responseMsg = new ResponseMsg(code, message, data);

        if (data instanceof Page) {
            Page page = (Page) data;
            responseMsg.setData(page.getContent());
        }
        getLogger().debug("Response: " + ObjectMapperUtil.writeValueAsString(responseMsg));
        return new ResponseEntity(responseMsg, HttpStatus.OK);
    }

    protected ResponseEntity responseAttachment(String fileName) {
        return responseAttachment(new File(fileName));
    }

    protected ResponseEntity responseAttachment(File file) {
        try {
            HttpHeaders headers = new HttpHeaders();
            String header =  "attachment";

            String fileName = file.getName().toLowerCase();

            if (fileName.endsWith(".pdf")) {
                headers.setContentType(MediaType.APPLICATION_PDF);
                header = "inline";
            } else if (fileName.endsWith(".xls") || fileName.endsWith(".xlsx")) {
                headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
            }

            headers.add("Content-Disposition", header + "; filename=" + file.getName());

            return new ResponseEntity(new InputStreamResource(
                new ByteArrayInputStream(FileUtils.readFileToByteArray(file))), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new StorageException.FileNotFound(Msg.getMessage("failed.to.read.file", new Object[] {file.getName()}));
        }
    }

    protected ResponseEntity response(String message, Object data) {
        return response(0, message , data);
    }

    protected ResponseEntity response(int code, String message) {
        return response(code, message, null);
    }

    protected ResponseEntity response(Object data) {
        return response(null, data);
    }

    protected ResponseEntity response() {
        return response(null);
    }

}