package com.askdog.android.model;

/**
 * Created by Administrator on 2016/8/20.
 */
public class TokenMessageBean {
    public String type;                                              // Token类别
    public String policy;                                      // 上传限制策略
    public String signature;                                  // 上传限制策略校验签名
    public String host;                                        // 上传URL
    public long expire;                                      // token过期时间
    public String callback;                                  // 上传回调信息
    public String OSSAccessKeyId;                           // AK
    public String key;                                       // 上传文件名
    public String secret_key;                               // SK（仅当type为EXPERIENCE_VIDEO时才包含此项）
    public String link_id;                                  // linkId（仅当type为EXPERIENCE_VIDEO时才包含此项）
    public String bucket;                                   // bucket（仅当type为EXPERIENCE_VIDEO时才包含此项）
    public String endpoint;                                 // endpoint（仅当type为EXPERIENCE_VIDEO时才包含此项）
}
