package top.hxyac.chatbot.utils;

/**
 * The type Json data.
 */
public class JsonData {

    /**
     * 业务状态码
     * 0：成功
     * 1：处理中
     * -1：失败
     */
    private Integer code;

    /**
     * 业务数据
     */
    private Object data;

    /**
     * 描述信息
     */
    private String msg;

    private JsonData(){}

    private JsonData(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }


    /**
     * Build success json data.
     *
     * @return the json data
     */
    public static JsonData buildSuccess(){
        return new JsonData(ResultEnum.SUCCESS.getCode(),null,ResultEnum.SUCCESS.getMsg());
    }

    /**
     * Build success json data.
     *
     * @param data the data
     * @return the json data
     */
    public static JsonData buildSuccess(Object data){
        return new JsonData(ResultEnum.SUCCESS.getCode(),data,ResultEnum.SUCCESS.getMsg());
    }

    /**
     * Build success json data.
     *
     * @param msg the msg
     * @return the json data
     */
    public static JsonData buildSuccess(String msg){
        return new JsonData(ResultEnum.SUCCESS.getCode(),null,msg);
    }

    /**
     * Build success json data.
     *
     * @param data the data
     * @param msg  the msg
     * @return the json data
     */
    public static JsonData buildSuccess(Object data,String msg){
        return new JsonData(ResultEnum.SUCCESS.getCode(),data,msg);
    }

    /**
     * Build error json data.
     *
     * @return the json data
     */
    public static JsonData buildError(){
        return new JsonData(ResultEnum.UNKNOWN_ERROR.getCode(), null,ResultEnum.UNKNOWN_ERROR.getMsg());
    }

    /**
     * Build error json data.
     *
     * @param data the data
     * @return the json data
     */
    public static JsonData buildError(Object data){
        return new JsonData(ResultEnum.UNKNOWN_ERROR.getCode(),data,ResultEnum.UNKNOWN_ERROR.getMsg());
    }

    /**
     * Build error json data.
     *
     * @param msg the msg
     * @return the json data
     */
    public static JsonData buildError(String msg){
        return new JsonData(ResultEnum.UNKNOWN_ERROR.getCode(),null,msg);
    }

    /**
     * Build error json data.
     *
     * @param code the data
     * @param msg  the msg
     * @return the json data
     */
    public static JsonData buildError(Integer code,String msg){
        return new JsonData(code,null,msg);
    }

    /**
     * Build error json data.
     *
     * @param data the data
     * @param msg  the msg
     * @return the json data
     */
    public static JsonData buildError(Object data,String msg){
        return new JsonData(ResultEnum.UNKNOWN_ERROR.getCode(),data,msg);
    }

    /**
     * Build customize json data.
     *
     * @param code the code
     * @param data the data
     * @param msg  the msg
     * @return the json data
     */
    public static JsonData buildCustomize(Integer code,Object data,String msg){
        return new JsonData(code,data,msg);
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
