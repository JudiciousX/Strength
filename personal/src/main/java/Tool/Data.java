package Tool;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private String code;
    private String msg;
    private List<ArticleContent> data = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<ArticleContent> getData() {
        return data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<ArticleContent> data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
