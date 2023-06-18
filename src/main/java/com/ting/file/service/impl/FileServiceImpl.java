package com.ting.file.service.impl;

import com.ting.file.service.IFileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * @author ting
 * @version 1.0
 * @date 2023/6/18
 */
@Service
public class FileServiceImpl implements IFileService {


    private static final String basePath = "/var/log/posLog/";

    @Override
    public void upload(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        File file = new File(basePath + originalFilename);

        OutputStream out = null;
        try {
            //获取文件流，以文件流的方式输出到新文件
            out = Files.newOutputStream(file.toPath());
            byte[] ss = multipartFile.getBytes();
            for (int i = 0; i < ss.length; i++) {
                out.write(ss[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void down(String filePath, HttpServletResponse response) {
        File file = new File(basePath);
        File[] listFiles = file.listFiles();
        if (ObjectUtils.isEmpty(listFiles)) {
            System.out.println("-----------------失败");
            return;
        }
        File file1 = listFiles[0];
        try {
            FileInputStream fileInputStream = new FileInputStream(file1);

            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file1.getName());
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                // 4、通过输入流读取文件数据，然后通过上述的输出流写回浏览器
                outputStream.write(bytes, 0, len);
                // 刷新
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
