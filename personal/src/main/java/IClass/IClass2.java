package IClass;

import java.util.Date;
import java.util.List;

public class IClass2 {
    private List<ArticleContent> articleContent;
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<ArticleContent> getArticleContent() {
        return articleContent;
    }

    public class ArticleContent {
        private String article_id;//文章id
        private String uid;//发布者id
        private String username;//发布者用户名
        private String head_sculpture;//发布者头像
        private String article_name;//文章标题
        private String content;//文章内容
        private String address;//发布地点
        private Date gmt_create;//发布时间
        private Integer is_delete;//文章状态：1 删除,0 未删除
        private List<ArticleComment> comment;

        public class ArticleComment {

            private Integer comment_id;//评论id
            private String uid;//评论者id
            private String username;//评论者用户名
            private String head_sculpture;//评论者头像
            private String article_id;//被评论文章id
            private String comment;//评论内容
            private Date gmt_create;//评论时间

        }

        public String getArticle_name() {
            return article_name;
        }

        public Integer getIs_delete() {
            return is_delete;
        }

        public Date getGmt_create() {
            return gmt_create;
        }
    }


}
