package com.zsq.myblog.showVo;

public class BlogSearch {

    private String title;
    private Long typeId;

    public BlogSearch() {
    }

    public BlogSearch(String title, Long typeId) {
        this.title = title;
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "BlogSearch{" +
                "title='" + title + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}
