package org.example.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * ClassName: CommonContoller
 * Package: org.example.controller
 * Description: 上传与下载
 *
 * @Autehor 屈子岩
 * @Create 2024/8/17 17:56
 * @Version 1.0
 */
@RestController
@RequestMapping("common")
@Slf4j
public class CommonContoller {

    @Value("${reggie.path}")
    private String basepath;

    @PostMapping("upload")
    public Result<String> upload(MultipartFile file) {
        log.info("获取文件：{}", file);
        // 创建一个File对象，指向基础路径
        File dir = new File(basepath);
        // 如果目录不存在，则创建目录
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 获取文件的原始名称
        String originalFilename = file.getOriginalFilename();
        // 提取文件的后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 生成一个新的文件名，使用UUID确保唯一性，并保留原始文件的后缀
        String fileName = UUID.randomUUID() + suffix;
        // 尝试将文件上传到指定路径
        try {
            // 执行文件传输操作
            file.transferTo(new File(basepath + fileName));
        } catch (IOException e) {
            // 如果发生IO异常，抛出运行时异常
            throw new RuntimeException(e);
        }
        return Result.success(fileName);
    }

    @GetMapping("download")
    public void download(String name, HttpServletResponse response) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basepath + name));
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;

            byte[] bytes = new byte[1024];

            while ((len = fileInputStream.read(bytes)) != -1) {

                outputStream.write(bytes, 0, len);

                outputStream.flush();
            }

            outputStream.close();

            fileInputStream.close();
        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
