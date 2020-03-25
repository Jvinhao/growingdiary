package org.lf.diary.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/16 17:24
 * @Description: TODO
 */
@Component
public class ImageUtils {

    private static String serverAddr = "http://growingdiary.club";

    public String uploadImage(MultipartFile multipartFile) throws IOException, MyException {
        StorageClient storageClient = getStorageClient();
        /*
         * 上传文件
         * */
        String ext = multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().indexOf('.') + 1);
        String[] uploadFile = storageClient.upload_file(multipartFile.getBytes(), ext, null);

        StringBuilder url = new StringBuilder(serverAddr);
        for (int i = 0; i < uploadFile.length; i++) {
            String s = uploadFile[i];
            url.append("/").append(s);
        }
        return url.toString();
    }


    /**
     * 获取仓库
     *
     * @return
     * @throws IOException
     * @throws MyException
     */
    public StorageClient getStorageClient() throws IOException, MyException {
        /*
         * 获取配置文件
         * */

       /* ResourceLoader loader = new DefaultResourceLoader();
        Resource r = loader
                .getResource("classpath:/tracker.conf");
        String file = r.getFile().toString();*/

        ClassPathResource resource = new ClassPathResource("tracker.conf");
        String path = resource.getPath();
        /*
         * 初始化，获取仓库
         * */

        ClientGlobal.init(path);
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return new StorageClient(trackerServer, null);
    }

    public int removeImage(String imageUrl) throws IOException, MyException {
        StorageClient storageClient = getStorageClient();
        //
        int index = imageUrl.indexOf("group");
        String group = imageUrl.substring(index, index + 6);
        String remoteName = imageUrl.substring(index + 7);
        return storageClient.delete_file(group, remoteName);
    }


    /**
     * 本地文件上传
     *
     * @param imageUrl
     * @throws IOException
     * @throws MyException
     */
    public void uploadImage(String imageUrl) throws IOException, MyException {
        StorageClient storageClient = getStorageClient();
        String[] strings = storageClient.upload_file(imageUrl, "jpg", null);

        StringBuilder url = new StringBuilder("http://182.92.198.237");
        for (int i = 0; i < strings.length; i++) {
            String s = strings[i];
            url.append("/").append(s);
        }
        System.out.println(url.toString());
    }

}
