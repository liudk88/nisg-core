package com.hcxinan.core.inte.message;

import java.io.File;
import java.util.List;

/**邮件发送接口
 * @author liudk
 * @Description:
 * @date 21-9-8 下午5:31
 */
public interface IMailSender {
    /**
     * 发送邮件.
     * @param emails  收件人邮箱列表.
     * @param subject 邮件主题.
     * @param content    邮件内容.
     * @return true : 发邮件成功，false : 发邮件失败.
     */
    boolean send(final List<String> emails, final String subject, final String content);

    /**
     * 发送邮件.
     * @param emails  收件人邮箱列表.
     * @param ccEmails  抄送人邮箱列表.
     * @param bccEmails 密送人邮箱列表.
     * @param subject 邮件主题.
     * @param content    邮件内容.
     * @return true : 发邮件成功，false : 发邮件失败.
     */
    boolean send(final List<String>emails,final List<String> ccEmails,
                 final List<String> bccEmails,final String subject,final String content);

    /**
     * 发送邮件.
     * @param emails  收件人邮箱列表.
     * @param ccEmails  抄送人邮箱列表.
     * @param bccEmails 密送人邮箱列表.
     * @param subject 邮件主题.
     * @param content    邮件内容.
     * @param attachmentFileList 附件列表.
     * @return true : 发邮件成功，false : 发邮件失败.
     */
    boolean send(final List<String>emails,final List<String> ccEmails,
                 final List<String> bccEmails,final String subject,final String content,final List<File> attachmentFileList);
}
