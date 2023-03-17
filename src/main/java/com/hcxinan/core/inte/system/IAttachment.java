package com.hcxinan.core.inte.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author liudk
 * @Description:
 * @date 21-9-10 下午3:36
 */
public interface IAttachment {
    //附件的ID
    String getAttachmentId();
    //文件名称
    String getName();
    //获取文件的byte数组
    byte[] getBytes() throws IOException;
    //文件大小（带上单位）
    String getSize();
    //相对路径信息
    String getPath();
    //获取文件所在绝对路径
    String getAbsolutePath();
    //获得文件的流
    FileInputStream getInputStream();
    //获取文件令牌
    String getFileToken();
    //获取文件上传人
    String getUploader();
    //获取文件上传时间
    Date getUploadTime();
}
