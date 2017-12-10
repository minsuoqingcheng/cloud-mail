package cn.nju.edu.se.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

@Service
public class EmailTemplateService {

    private Logger LOGGER = LoggerFactory.getLogger(EmailTemplateService.class);

    private static final String EMAIL_TEMPLATE_BASE_URL = "/template/email";

    private static Configuration config = new Configuration(Configuration.VERSION_2_3_23);

    public EmailTemplateService() {
        config.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), EMAIL_TEMPLATE_BASE_URL);
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public String process(String templateName, Map map) {
        try {
            Template template = config.getTemplate(templateName);
            Writer writer = new StringWriter();
            try {
                template.process(map, writer);
                return writer.toString();
            } catch (TemplateException e) {
                LOGGER.error("模板中的参数定义错误");
                return null;
            }
        } catch (IOException e) {
            LOGGER.error("配置文件中未找到: "+templateName);
            e.printStackTrace();
            return null;
        }
    }

}
