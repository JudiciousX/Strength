package Tool;

import java.util.List;

public class User1 {
    private String msg;
    private String code;
    private List<ArticleUser> data;

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public List<ArticleUser> getData() {
        return data;
    }

    public static class ArticleUser {
        private String uid;//用户uid
        private String username;//用户名
        private String headSculpture;//用户头像

        public ArticleUser(String uid, String username, String headSculpture) {
            this.uid = uid;
            this.username = username;
            this.headSculpture = headSculpture;
        }


        public String getHeadSculpture() {
            return headSculpture;
        }

        public String getUid() {
            return uid;
        }

        public String getUsername() {
            return username;
        }
    }

}
