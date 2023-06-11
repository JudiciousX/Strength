package Tool;

public class ArticleContent {
    private String articleId;//文章id
    private String uid;//发布者id
    private String username;//发布者用户名
    private String headSculpture;//发布者头像
    private String content;//文章内容
    private String address;//发布地点
    private String time;//约球时间
    private String gmtCreate;//发布时间
    private Integer state;//是否结束
    private String comment;

    public ArticleContent(String articleId, String uid, String username, String headSculpture, String content, String address, String time, String gmtCreate, Integer state, String comment) {
        this.articleId = articleId;
        this.uid = uid;
        this.username = username;
        this.headSculpture = headSculpture;
        this.content = content;
        this.address = address;
        this.time = time;
        this.gmtCreate = gmtCreate;
        this.state = state;
        this.comment = comment;
    }
    public String getUsername() {
        return username;
    }

    public String getUid() {
        return uid;
    }

    public String getHeadSculpture() {
        return headSculpture;
    }

    public Integer getState() {
        return state;
    }

    public String getAddress() {
        return address;
    }

    public String getArticleId() {
        return articleId;
    }

    public String getContent() {
        return content;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "ArticleContent{" +
                "articleId='" + articleId + '\'' +
                ", uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", headSculpture='" + headSculpture + '\'' +
                ", content='" + content + '\'' +
                ", address='" + address + '\'' +
                ", time='" + time + '\'' +
                ", gmtCreate='" + gmtCreate + '\'' +
                ", state=" + state +
                ", comment='" + comment + '\'' +
                '}';
    }
}
