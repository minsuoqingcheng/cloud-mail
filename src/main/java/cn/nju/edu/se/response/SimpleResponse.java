package cn.nju.edu.se.response;

public class SimpleResponse {

    private int code;
    private Object data;

    public SimpleResponse(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public static SimpleResponse ok(Object data) {
        return new SimpleResponse(ResponseCode.OK, data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
