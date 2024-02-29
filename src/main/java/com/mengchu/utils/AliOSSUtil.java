package com.mengchu.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
public class AliOSSUtil {
    private static final String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    private static final String accessKeyId = "LTAI5tGokgYC8nGJ4nF3B4d1";
    private static final String secretAccessKey = "IEFXNdwhUKojfQrrXti3gVhfvrO4aD";
    private static final String bucketName = "competition-case";
    private static final OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey);

    /**
     * 实现上传文件
     * @param file:上传的文件
     * @return url： 返回的url
     */
    public static String upload(MultipartFile file) throws IOException {
        //获取上传文件的输入流
        InputStream is = file.getInputStream();
        String url = null;
        try {
            //源文件名
            String originalFilename = file.getOriginalFilename();
            //文件名后缀
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //文件名
            String fileName = UUID.randomUUID() + suffix;
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, is);
            // 创建PutObject请求。
            ossClient.putObject(putObjectRequest);
            //返回访问url
            url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.error("Request ID:" + oe.getRequestId());
            log.error("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.error("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
    public static String upload(File file) throws IOException {
        //获取上传文件的输入流
        InputStream is = new FileInputStream(file);
        String url = null;
        try {
            //源文件名
            String originalFilename = file.getName();
            //文件名后缀
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //文件名
            String fileName = UUID.randomUUID() + suffix;
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, is);
            // 创建PutObject请求。
            ossClient.putObject(putObjectRequest);
            //返回访问url
            url = endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + fileName;
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.error("Request ID:" + oe.getRequestId());
            log.error("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.error("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}
