package com.askdog.web.api.client;

import com.askdog.web.dsl.Chain;

public interface ApiClient extends Chain {
    Me.Api meApi();
    CreateUser.Api createUserApi();
    UserAuthenticate.Api authenticateUserApi();
    RecoverPassword.Api recoverPasswordApi();
    CreateQuestion.Api createQuestionApi();
    CreateAnswer.Api createAnswerApi();
}
