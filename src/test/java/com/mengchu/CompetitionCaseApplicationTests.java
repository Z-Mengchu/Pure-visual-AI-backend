package com.mengchu;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.mengchu.mapper.UserMapper;
import com.mengchu.pojo.Modeling;
import com.mengchu.pojo.Rems;
import com.xiaoleilu.hutool.io.IoUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class CompetitionCaseApplicationTests {

	@Autowired
	private UserMapper userMapper;
	@Test
	void userMapperTest() {
		// Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
		String endpoint = "https://oss-cn-beijing.aliyuncs.com";
		//OSS_ACCESS_KEY_ID
		String accessKeyId = "LTAI5tGokgYC8nGJ4nF3B4d1";
		//OSS_ACCESS_KEY_SECRET
		String secretAccessKey = "IEFXNdwhUKojfQrrXti3gVhfvrO4aD";
		// 填写Bucket名称，competition-case。
		String bucketName = "competition-case";
		// 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
		String objectName = "1.jpg";
		// 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
		// 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
		String filePath= "D:/图片/005D0pgely1hiweer4xnvj335f4qenpf.jpg";

		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey);

		try {
			InputStream inputStream = new FileInputStream(filePath);
			// 创建PutObjectRequest对象。
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
			// 创建PutObject请求。
			ossClient.putObject(putObjectRequest);
		} catch (OSSException oe) {
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message:" + oe.getErrorMessage());
			System.out.println("Error Code:" + oe.getErrorCode());
			System.out.println("Request ID:" + oe.getRequestId());
			System.out.println("Host ID:" + oe.getHostId());
		} catch (ClientException ce) {
			System.out.println("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message:" + ce.getMessage());
		} catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
	}

	@Test
	void test1() throws IOException {
		String root = "http://127.0.0.1:1314";
		RestTemplate restTemplate = new RestTemplate();
		String url1 = root + "/task";
		/*for (MultipartFile multipartFile : image) {
			InputStream is = multipartFile.getInputStream();
			OutputStream os = new FileOutputStream("D:\\demo\\" + UUID.randomUUID() + ".jpg");
			IoUtil.copy(is, os);
		}*/
		Modeling modeling = new Modeling("D:\\demo", new Rems());
		UUID response = restTemplate.postForObject(url1, modeling, UUID.class);
		System.out.println(response);
		String url2 = root + "/task/" + response;
		System.out.println(url2);
		Timer timer = new Timer();
		final Integer[] result = new Integer[1];
		CountDownLatch latch = new CountDownLatch(1); // 创建CountDownLatch
		//向python端定时发送get请求
		try {
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					result[0] = restTemplate.getForObject(url2, Integer.class);
					if (result[0] != null && -2 == result[0]){
						System.out.println(result[0]);
						timer.cancel();
						latch.countDown(); // 定时任务完成，计数减一
					}
				}
			},500, 1000);
			latch.await(); // 等待定时任务完成
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Test
	void test2() {
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid);
		String dir = uuid.toString().replace("-", "");
		System.out.println(dir);
	}

	@Test
	void test3() {
		File file = new File("D:\\图片\\005D0pgely1hiweer4xnvj335f4qenpf.jpg");
		System.out.println(file.getName());
	}

	@Test
	void test4() {
		ScheduledExecutorService service = new ScheduledThreadPoolExecutor(17);
		service.scheduleAtFixedRate(()->System.out.println("这是一条任务")
		, 1, 1, TimeUnit.SECONDS);
	}
}
