package cn.nju.edu.se.rest;

import cn.nju.edu.se.dto.ExamCodeEmailForm;
import cn.nju.edu.se.response.SimpleResponse;
import cn.nju.edu.se.service.EmailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/email")
@Api("EmailResource")
public class EmailResource {

    @Autowired
    private EmailService emailService;

    @Path("/send")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendEmails(ExamCodeEmailForm form) {
        emailService.sendEmail(form);
        return Response.ok().build();
    }

    @Path("/synSend")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response synSendMails(ExamCodeEmailForm form) {
        boolean result = emailService.synSendEmail(form);
        return Response.ok(result).build();
    }
}
