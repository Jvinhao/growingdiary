package org.lf.diary.core;

import lombok.Data;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/4 13:58
 * @Description: TODO
 */
@Data
public class AccessTokenDTO {
    private String code;
    private String clientId;
    private String clientSecret;
    private String state;
    private String redirectUri;
    private String accessToken;
    private String openId;
}
