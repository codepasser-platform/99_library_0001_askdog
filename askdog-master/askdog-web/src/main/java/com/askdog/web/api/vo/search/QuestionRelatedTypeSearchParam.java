package com.askdog.web.api.vo.search;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class QuestionRelatedTypeSearchParam extends TypeSearchParam {

    @NotNull
    private String questionId;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> params = super.getParams();
        params.put("question_id", getQuestionId());
        return params;
    }
}
