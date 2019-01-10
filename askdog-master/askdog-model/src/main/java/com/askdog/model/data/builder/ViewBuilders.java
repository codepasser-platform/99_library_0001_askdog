package com.askdog.model.data.builder;

import static com.askdog.model.data.Actions.QuestionView;

public class ViewBuilders {

    public static QuestionViewBuilder questionViewBuilder() {
        return new QuestionViewBuilder();
    }

    public static class QuestionViewBuilder extends ActionBuilder<QuestionViewBuilder, QuestionView> {

        public QuestionView build() {
            return build(new QuestionView());
        }

    }
}
