package com.askdog.web.api.vo;

import java.util.List;
import java.util.Map;

public class QuestionDetailRelates extends QuestionDetail {

    private List<Map<String, Object>> relates;


    public List<Map<String, Object>> getRelates() {
        return relates;
    }

    public void setRelates(List<Map<String, Object>> relates) {
        this.relates = relates;
    }

    @Override
    public QuestionDetailRelates from(com.askdog.service.bo.QuestionDetail question) {
        super.from(question);
        return this;
    }

}
