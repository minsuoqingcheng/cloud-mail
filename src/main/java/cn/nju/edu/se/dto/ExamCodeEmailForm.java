package cn.nju.edu.se.dto;

import cn.nju.edu.se.entity.CodeInfo;

import java.util.List;

public class ExamCodeEmailForm {

    private String subject;
    private String examName;
    private List<CodeInfo> codes;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public List<CodeInfo> getCodes() {
        return codes;
    }

    public void setCodes(List<CodeInfo> codes) {
        this.codes = codes;
    }
}
