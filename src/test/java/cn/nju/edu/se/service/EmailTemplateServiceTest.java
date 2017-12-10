package cn.nju.edu.se.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.HashMap;
import java.util.Map;

/** 
* EmailTemplateService Tester. 
* 
* @author <Authors name> 
* @since <pre>十一月 5, 2017</pre> 
* @version 1.0 
*/ 
public class EmailTemplateServiceTest { 

    @Before
    public void before() throws Exception {

    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: process(String templateName, Map map)
    *
    */
    @Test
    public void testProcess() throws Exception {
    //TODO: Test goes here...
        EmailTemplateService service = new EmailTemplateService();
        Map<String, String> params = new HashMap<String, String>();
        params.put("examName", "Test");
        params.put("verifyCode", "123456");
        String content = service.process("login-exam-code-email-tmpl.html", params);
        Assert.assertNotNull(content);
    }


} 
