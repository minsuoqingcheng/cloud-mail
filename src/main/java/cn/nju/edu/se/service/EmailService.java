package cn.nju.edu.se.service;

import cn.nju.edu.se.dto.ExamCodeEmailForm;
import cn.nju.edu.se.entity.CodeInfo;
import cn.nju.edu.se.properties.EmailProperties;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailService {

    private Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final String templateName = "login-exam-code-email-tmpl.html";
    private ExecutorService executorService;
    @Autowired
    private EmailProperties emailProperties;
    @Autowired
    private EmailTemplateService emailTemplateService;

    public EmailService() {
        executorService = Executors.newCachedThreadPool();
    }

    public boolean sendEmail(ExamCodeEmailForm form) {
        String examName = form.getExamName();
        String subject = form.getSubject();
        List<CodeInfo> codeInfoList = form.getCodes();
        for (CodeInfo codeInfo : codeInfoList) {
            String to = codeInfo.getTo();
            String code = codeInfo.getCode();
            Map<String, String> params = new HashMap<String, String>();
            params.put("examName", examName);
            params.put("verifyCode", code);
            String content = emailTemplateService.process(templateName, params);
            try {
                final Email email = commonPropertyEmail();
                email.addTo(to);
                email.setMsg(content);
                email.setSubject(subject);
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            email.send();
                        } catch (EmailException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (EmailException e) {
                LOGGER.error("发送邮件失败!!!");
                e.printStackTrace();
            }
            LOGGER.info(content);
        }
        executorService.shutdown();
        return true;
    }

    public boolean synSendEmail(ExamCodeEmailForm form) {
        final CountDownLatch latch = new CountDownLatch(form.getCodes().size());
        String examName = form.getExamName();
        String subject = form.getSubject();
        List<CodeInfo> codeInfoList = form.getCodes();
        for (CodeInfo codeInfo : codeInfoList) {
            String to = codeInfo.getTo();
            String code = codeInfo.getCode();
            Map<String, String> params = new HashMap<String, String>();
            params.put("examName", examName);
            params.put("verifyCode", code);
            String content = emailTemplateService.process(templateName, params);
            try {
                final Email email = commonPropertyEmail();
                email.addTo(to);
                email.setMsg(content);
                email.setSubject(subject);
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            email.send();
                        } catch (EmailException e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    }
                });
            } catch (EmailException e) {
                LOGGER.error("发送邮件失败!!!");
                e.printStackTrace();
            }
            LOGGER.info(content);
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Email commonPropertyEmail() throws EmailException {
        Email email = new HtmlEmail();
        email.setHostName(emailProperties.getHostName());
        email.setSmtpPort(emailProperties.getPort());
        email.setAuthentication(emailProperties.getFrom(), emailProperties.getPassword());
        email.setSSLOnConnect(emailProperties.isSSL());
        email.setFrom(emailProperties.getFrom(), emailProperties.getName(), emailProperties.getCharSet());
        email.setCharset(emailProperties.getCharSet());
        return email;
    }

}
