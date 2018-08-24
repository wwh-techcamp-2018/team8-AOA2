package com.aoa.springwebservice.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileStorageProperties {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${base.menuImgUrl-dir}")
    private String baseMenuUrl;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getBaseMenuUrl() {
        return baseMenuUrl;
    }

    public void setBaseMenuUrl(String baseMenuUrl) {
        this.baseMenuUrl = baseMenuUrl;
    }
}