package com.askdog.web.test.impl.inspector;

import com.askdog.common.utils.Json;
import com.askdog.web.test.Inspector;
import com.askdog.web.test.json.Message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public interface ErrorInspector<N extends Inspector> extends Inspector.ErrorInspector<N>  {

    @Override
    default N expectErrorCode(String code) {
        Message message = Json.readValue(getResponse(), Message.class);
        assertNotNull("expect the error response not null", message);
        assertEquals("expect the error code equal", code, message.getCode());
        return afterErrorInspector();
    }

    String getResponse();

    N afterErrorInspector();
}
