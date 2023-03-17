package com.hcxinan.core.inte.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author liudk
 * @Description: 文件管理器，统一管理系统的文件上传、下载、持久化等操作。
 *
 * @date 21-9-10 下午3:03
 */
public interface IAttachmentManager {
    /**
     * 增加（保存）文件。
     * @Author liudk by 2022/1/17 下午3:19
     *
     * @Param files: 需要保存的附件文件
     *
     * @Return String[]。
     *  数组第一个元素代表文件令牌环，从第二个元素开始，返回附件集合id，每个文件对应一个id,所以数组长度是files长度+1
     *  注：文件令牌环：用来绑定当前所有上传的文件，后面可以通过这个参数调用getAttachmentsByToken方法直接获取到当前上传的所有文件
     */
    default String[] addAttachments(File... files) throws IOException{
        InputStream[] inputStreams=new InputStream[files.length];
        String[] flieNames=new String[files.length];
        for(int i=0;i<files.length;i++){
            flieNames[i]=files[i].getName();
        }
        return addAttachments(flieNames,inputStreams);
    }
    /*
    * 增加（保存）文件，返回的是附件的主键
    * */
    default String addAttachmentReturnId(String fileName,InputStream inputStream) throws IOException{
        return addAttachments(new String[]{fileName},new InputStream[]{inputStream})[1];
    }
    /**
     * 增加（保存）文件
     * @Author liudk by 2022/1/18 下午3:12
     *
     * @Param fileName: 需要保存的文件名
     * @Param inputStream: 需要保存的文件对应的流
     *
     * @Throws
     *
     * @Return
     */
    default String addAttachment(String fileName,InputStream inputStream) throws IOException{
        return addAttachments(new String[]{fileName},new InputStream[]{inputStream})[0];
    }
    /**
     * 批量增加（保存）文件。
     * @Author liudk by 2022/1/18 下午2:04
     *
     * @Param flieNames: 需要保存的附件名称
     * @Param inputStreams: 需要保存的附件文件流，数组长度必须和flieNames保持一致
     *
     * @Throws IOException
     *
     * @Return String[]。
     * 数组第一个元素代表文件令牌环，从第二个元素开始，返回附件集合id，每个文件对应一个id,所以数组长度是files长度+1
     * 注：文件令牌环：用来绑定当前所有上传的文件，后面可以通过这个参数调用getAttachmentsByToken方法直接获取到当前上传的所有文件
     */
    String[] addAttachments(String[] flieNames,InputStream[] inputStreams) throws IOException;
    /**
     * @Author liudk by 2022/7/29 上午10:32
     * @description：保存附件,并把附件中的byte数组转换成文件保存到系统附件目录
     *
     * @Param 
     *
     * @Throws
     *
     * @Return 
     */
    String[]  addAttachments(IAttachment... attachment);
    /**
     * 批量将文件追加到指定文件令牌环下
     * @Author liudk by 2022/1/17 上午11:26
     *
     * @Param fileToken:附件令牌环。
     * @Param files: 需要保存的附件文件
     *
     * @Return 返回附件集合id，每个文件对应一个id,所以数组长度和files保存一致
     */
    default String[] addAttachmentsToFileToken(String fileToken, File... files) throws IOException{
        InputStream[] inputStreams=new InputStream[files.length];
        String[] flieNames=new String[files.length];
        for(int i=0;i<files.length;i++){
            inputStreams[i]=new FileInputStream(files[i]);
            flieNames[i]=files[i].getName();
        }
        return addAttachmentsToFileToken(fileToken,flieNames,inputStreams);
    }

    /**
     * 批量将文件流追加到指定文件令牌环下
     * @Author liudk by 2022/1/18 下午3:14
     *
     * @Param fileToken:附件令牌环。
     * @Param flieNames:需要追加保存的附件名称。
     * @Param inputStreams:需要追加保存的附件文件流。数组长度和flieNames保存一致
     *
     * @Throws
     *
     * @Return 返回附件集合id，每个文件对应一个id,所以数组长度和files保存一致
     */
    String[] addAttachmentsToFileToken(String fileToken,String[] flieNames, InputStream... inputStreams) throws IOException;

    /**
     * 根据文件令牌删除其绑定的文件列表
     * @Author liudk by 2022/1/17 下午12:27
     *
     * @Param fileToken:附件令牌环。
     *
     * @Throws
     *
     * @Return boolean：是否删除成功
     */
    boolean removeAttachmentsByToken(String... fileToken);

    /**
     * 根据文件id删除文件
     * @Author liudk by 2022/1/17 下午12:29
     *
     * @Param attachmentId。一个或多个文件id
     *
     * @Return boolean：是否删除成功
     */
    boolean removeAttachmentsById(String... attachmentId);

    /**
     * 根据文件令牌环获取文件
     * @Author liudk by 2022/1/17 下午12:29
     *
     * @Param fileToken:文件令牌环
     *
     * @Return List<IAttachment>：当前文件令牌环绑定的所有附件
     */
    List<IAttachment> getAttachmentsByToken(String fileToken);
    /**
     * @Author liudk by 2022/8/11 上午8:00
     * @description：根据多个令牌环获取附件
     *
     * @Param 
     *
     * @Throws
     *
     * @Return 
     */
    List<IAttachment> getAttachmentsByTokens(String... fileToken);

    /**
     * 根据文件id获取文件
     * @Author liudk by 2022/1/17 下午12:30
     *
     * @Param attachmentId：文件id
     *
     * @Return IAttachment: 附件接口
     */
    IAttachment getAttachmentById(String attachmentId);
    /**
     * @Author liudk by 2022/7/28 下午9:26
     * @description：合并令牌到指定令牌
     *
     * @Param toFileToken :需要最终合并成的令牌
     * @Param mergedFileTokens :需要被合并的令牌
     *
     * @Throws
     *
     * @Return
     */
    void mergeFileToken(String toFileToken,String... mergedFileTokens);
}
