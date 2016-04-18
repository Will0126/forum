package com.kaishengit.entity;

import java.io.Serializable;

public class Topic implements Serializable {

    private static final long serialVersionUID = -8609054220588681486L;
    //实现了序列化接口
    private Integer id;
    private String title;
    private String context;
    private Integer nodeid;
    private String createtime;
    private Integer userid;
    private Integer clicknum;
    private Integer favnum;
    private Integer thanknum;
    private Integer replynum;
    private String lastreplytime;

    private User user;
    private Node node;


    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getNodeid() {
        return nodeid;
    }

    public void setNodeid(Integer nodeid) {
        this.nodeid = nodeid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getClicknum() {
        return clicknum;
    }

    public void setClicknum(Integer clicknum) {
        this.clicknum = clicknum;
    }

    public Integer getFavnum() {
        return favnum;
    }

    public void setFavnum(Integer favnum) {
        this.favnum = favnum;
    }

    public Integer getThanknum() {
        return thanknum;
    }

    public void setThanknum(Integer thanknum) {
        this.thanknum = thanknum;
    }

    public Integer getReplynum() {
        return replynum;
    }

    public void setReplynum(Integer replynum) {
        this.replynum = replynum;
    }

    public String getLastreplytime() {
        return lastreplytime;
    }

    public void setLastreplytime(String lastreplytime) {
        this.lastreplytime = lastreplytime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
