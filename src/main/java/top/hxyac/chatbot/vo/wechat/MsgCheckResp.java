package top.hxyac.chatbot.vo.wechat;

import java.io.Serializable;
import java.util.Arrays;

public class MsgCheckResp {

    private Integer errcode;

    private String errmsg;

    private String trace_id;

    private Result result;

    private Detail[] detail;

    @Override
    public String toString() {
        String toString = "MsgCheckResp{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                ", trace_id='" + trace_id + '\'';
        if(result != null){
            toString += ", result=" + result.toString();
        }

        if(detail != null){
            toString += ", detail=" + Arrays.toString(detail) + '}';
        }
        return toString;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getTrace_id() {
        return trace_id;
    }

    public void setTrace_id(String trace_id) {
        this.trace_id = trace_id;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Detail[] getDetail() {
        return detail;
    }

    public void setDetail(Detail[] detail) {
        this.detail = detail;
    }

    public String getResultSuggest(){
        return this.getResult().getSuggest();
    }


    static class Result implements Serializable {
        private String suggest;

        private Integer label;

        public String getSuggest() {
            return suggest;
        }

        public void setSuggest(String suggest) {
            this.suggest = suggest;
        }

        public Integer getLabel() {
            return label;
        }

        public void setLabel(Integer label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "suggest='" + suggest + '\'' +
                    ", label=" + label +
                    '}';
        }
    }

    static class Detail implements Serializable{
        private Integer errcode;

        private String suggest;

        private Integer label;

        private Integer prob;

        private String  keyword;

        private String  strategy;

        public Integer getErrcode() {
            return errcode;
        }

        public void setErrcode(Integer errcode) {
            this.errcode = errcode;
        }

        public String getSuggest() {
            return suggest;
        }

        public void setSuggest(String suggest) {
            this.suggest = suggest;
        }

        public Integer getLabel() {
            return label;
        }

        public void setLabel(Integer label) {
            this.label = label;
        }

        public Integer getProb() {
            return prob;
        }

        public void setProb(Integer prob) {
            this.prob = prob;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getStrategy() {
            return strategy;
        }

        public void setStrategy(String strategy) {
            this.strategy = strategy;
        }

        @Override
        public String toString() {
            return "Detail{" +
                    "errcode=" + errcode +
                    ", suggest='" + suggest + '\'' +
                    ", label=" + label +
                    ", prob=" + prob +
                    ", keyword='" + keyword + '\'' +
                    ", strategy='" + strategy + '\'' +
                    '}';
        }
    }

}
