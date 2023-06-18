package com.ting.file.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 文件
 *
 * @author ting
 * @version 1.0
 * @date 2023/6/18
 */
public interface IFileService {
    /**
     * 文件上传
     *
     * @param multipartFile
     */
    void upload(MultipartFile multipartFile);

    /**
     * 文件下载
     *
     * @param filePath
     * @param response
     */
    void down(String filePath, HttpServletResponse response);
}
