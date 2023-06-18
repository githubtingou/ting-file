package com.ting.file.controller;

import com.ting.file.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件
 *
 * @author ting
 * @version 1.0
 * @date 2023/6/18
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    public IFileService fileService;


    @PostMapping(value = "/upload")
    public void uploadFile(@RequestParam(value = "file") MultipartFile multipartFile) {
        this.fileService.upload(multipartFile);


    }

    @GetMapping(value = "/down")
    public void downFile(@RequestParam(value = "file", required = false) String filePath, HttpServletResponse response) {
        this.fileService.down(filePath, response);


    }
}
