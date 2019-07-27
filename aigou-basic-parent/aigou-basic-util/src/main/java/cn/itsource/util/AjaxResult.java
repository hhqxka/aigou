package cn.itsource.util;

public class AjaxResult {
    private boolean success;
    private String message ;
    private Integer easyCode;
    private Object resultObject ;

    private AjaxResult() {
    }
    public static AjaxResult me(){
        return new AjaxResult();
    }
    public boolean isSuccess() {
        return success;
    }

    public AjaxResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AjaxResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getEasyCode() {
        return easyCode;
    }

    public AjaxResult setEasyCode(Integer easyCode) {
        this.easyCode = easyCode;
        return this;
    }

    public Object getResultObject() {
        return resultObject;
    }

    public AjaxResult setResultObject(Object resultObject) {
        this.resultObject = resultObject;
        return this;
    }
}
