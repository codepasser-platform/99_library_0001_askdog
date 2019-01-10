package com.askdog.web;

import com.askdog.Application;
import com.askdog.web.dataset.RegisteredUser;
import com.askdog.web.test.Tester;
import com.askdog.web.test.UsingDataSet;
import com.askdog.web.test.cleanup.CleanupTestDataListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.askdog.web.dataset.RegisteredUser.anyRegisteredUser;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@TestExecutionListeners(listeners = CleanupTestDataListener.class, mergeMode = MERGE_WITH_DEFAULTS)
public class PasswordApiTest {

    @Autowired
    private Tester tester;

    @Test
    @UsingDataSet(RegisteredUser.class)
    public void shouldSendRecoverMail() {
        tester.password()
                .recover(anyRegisteredUser().getMail())
                .expectStatus(OK);
    }

    @Test
    public void shouldFailedWhenMailNotValid() {
        tester.password()
                .recover("invalid")
                .expectStatus(BAD_REQUEST);
    }

}
