package org.lf.diary.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Author: Jvinh
 * @DateTime: 2020/2/7 20:24
 * @Description: 验证码工具类
 */

public class ValidateCodeUtils {


    //默认生成图片的宽高
    public static final int DEFAULT_IMG_HEIGHT = 32;
    public static final int DEFAULT_IMG_WIDTH = 90;

    //默认code长度
    public static final int DEFAULT_CODE_LENGTH = 6;

    /**
     * 生成默认长度随机码
     *
     * @return
     */
    public static String createCode() {
        return createCode(DEFAULT_CODE_LENGTH);
    }

    /**
     * 根据长度生成随机码
     *
     * @param length
     * @return
     */
    public static String createCode(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int n = random.nextInt(10);
            sb.append(n);
        }
        return sb.toString();
    }

    /**
     * 根据验证码生成验证码图片
     *
     * @return
     */
    public static BufferedImage createVerifyCodeImg(String verifyCode) {
        return createVerifyCodeImg(verifyCode, DEFAULT_IMG_HEIGHT, DEFAULT_IMG_WIDTH);
    }

    /**
     * 根据验证码和宽高生成验证码图片
     *
     * @return
     */
    public static BufferedImage createVerifyCodeImg(String verifyCode, int height, int width) {

        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        return image;
    }
}
