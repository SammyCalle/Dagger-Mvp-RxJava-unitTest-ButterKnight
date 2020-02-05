package com.example.archcourse.http.twitch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Streams2 {

    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    private List<String> tag_ids;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }


    public List<String> getTag_ids() {
        return tag_ids;
    }

    public void setTag_ids(List<String> tag_ids) {
        this.tag_ids = tag_ids;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
