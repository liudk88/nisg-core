package com.hcxinan.core.inte.system;

import java.io.IOException;
import java.util.Map;

/**
 * @author liudk
 * @Description: 带有缓存实现的附件管理器
 * @date 21-9-10 下午8:52
 */
public interface ICacheAttachmentManager extends IAttachmentManager {
    //根据文件令牌id持久化对应文件,持久化入库并清空缓存
    boolean persistent(String fileToken) throws IOException;
    /**
     *@Description 缓存文件，附件只会先缓存到redis中，后面需要调用persistent()方法，这样才会真正存储到硬盘当中
     *
     *@Param fileToken：文件令牌环，如果为空，那么表示第一次新增，如果不为空，那么表示在指定文件令牌环上追加文件
     *@Param files：需要添加的文件
     *
     *@Return java.lang.String[]:数组第一个元素是fileToken，后面依次是附件id
     *@Author liudk
     *@DateTime 21-9-11 上午9:35
    */
    String[] upload(String fileToken, Map<String,byte[]> attachMap);
    //删除缓存对应文件
    boolean removeCacheFile(String attachmentId);

    void clearCache(String fileToken);
}
