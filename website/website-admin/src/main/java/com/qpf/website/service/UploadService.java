package com.qpf.website.service;

import com.qpf.website.commons.dto.BaseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    BaseResult uploadFile(MultipartFile file);
}
