package com.askdog.service.exception;


import com.askdog.common.exception.Message;

public class ForbiddenException extends ServiceRuntimeException {

    private static final long serialVersionUID = 2399610140036369185L;

    public enum Error {
        DELETE_COUPON,
        UPDATE_PRODUCT,
        DELETE_PRODUCT,
        ACHIEVE_COUPON,
        HAS_USED,
        UPDATE_STORE,
        DELETE_STORE,
        UPDATE_AGENT,
        DELETE_AGENT,
        COUPON_lIST,
        CREATE_COUPONS,
        COUPON_DETAIL,
        CONSUME_COUPON
    }

    public ForbiddenException(Message message) {
        super(message);
        setCode(ForbiddenException.Error.valueOf(message.getPartial()));
    }

    public ForbiddenException(ForbiddenException.Error error) {
        super(error);
    }

    @Override
    protected String reasonCode() {
        return "FORBIDDEN";
    }
}
