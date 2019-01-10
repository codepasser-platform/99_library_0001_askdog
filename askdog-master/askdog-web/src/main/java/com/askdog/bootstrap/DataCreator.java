package com.askdog.bootstrap;

import com.askdog.model.entity.IncentivePolicy;
import com.askdog.model.entity.Question;
import com.askdog.model.entity.Template;
import com.askdog.model.entity.User;

import java.util.Set;

public interface DataCreator {

    DataCreator user(User user);

    DataCreator question(Question question);

    DataCreator label(Set<String> labelNames);

    DataCreator template(Template template);

    DataCreator incentivePolicy(IncentivePolicy incentivePolicy);

}
