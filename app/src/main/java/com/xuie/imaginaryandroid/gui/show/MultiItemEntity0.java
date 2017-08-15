package com.xuie.imaginaryandroid.gui.show;

import java.util.List;

/**
 * Created by xuie on 17-8-15.
 */

public class MultiItemEntity0 {
    private String type;
    private List<Child> content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Child> getContent() {
        return content;
    }

    public void setContent(List<Child> content) {
        this.content = content;
    }

    public static class Child {
        private String articleName;
        private String articleUrl;
        private String author;
        private String imageUrl;

        public String getArticleName() {
            return articleName;
        }

        public void setArticleName(String articleName) {
            this.articleName = articleName;
        }

        public String getArticleUrl() {
            return articleUrl;
        }

        public void setArticleUrl(String articleUrl) {
            this.articleUrl = articleUrl;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        public String toString() {
            return "Child{" +
                    "articleName='" + articleName + '\'' +
                    ", articleUrl='" + articleUrl + '\'' +
                    ", author='" + author + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MultiItemEntity0{" +
                "type='" + type + '\'' +
                ", content=" + content +
                '}';
    }
}
