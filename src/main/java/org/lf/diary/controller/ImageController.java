package org.lf.diary.controller;

import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.lf.diary.core.Result;
import org.lf.diary.core.ResultGenerator;
import org.lf.diary.model.Vo.ImageUploadVo;
import org.lf.diary.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/15 22:32
 * @Description: TODO
 */
@Controller
@Slf4j
public class ImageController {

    @Autowired
    private ImageUtils imageUtils;


    @RequestMapping(value = "/doUploadImage", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadImage(MultipartFile file) throws IOException {
        String url = "";
        try {
            url = imageUtils.uploadImage(file);
        } catch (MyException e) {
            e.printStackTrace();
        }

        return ResultGenerator.genSuccessResult(url);
    }

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public ImageUploadVo uploadImg(@RequestParam("upload") MultipartFile file) throws IOException {
        log.info("开始上传图片");
        String url = "";
        ImageUploadVo imageUploadVo = new ImageUploadVo();
        try {
            url = imageUtils.uploadImage(file);
            imageUploadVo.setUploaded(1);
            imageUploadVo.setFileName(file.getOriginalFilename());
            imageUploadVo.setUrl(url);
            log.info("图片上传完成");
        } catch (MyException e) {
            e.printStackTrace();
        }

        return imageUploadVo;
    }


}
