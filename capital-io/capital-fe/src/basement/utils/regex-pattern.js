"use strict";

/*TODO validation pattern*/
export const REGEX_USER_NAME = /^[a-zA-Z0-9_-]{4,16}$/; // length is 4 ~ 16

export const REGEX_GENERAL_NAME = /^[^!\s*$%^&*()+|~=`{}\[\]:/;<>?,.@#'"\\]{1,30}$/;

export const REGEX_USER_PASSWORD = /^[\s\S]{6,20}$/;

export const REGEX_PHONE = /^(1[3578][0-9]{9}|(\d{3,4}-)\d{7,8}(-\d{1,4})?)$/;

export const REGEX_MAIL = /^[^@\s]+@(?:[^@\s.]+)(?:\.[^@\s.]+)+$/;

export const REGEX_IDENTIFYING_CODE = /^\d{6}$/;

export const REGEX_FAX = /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;

export const REGEX_POST_CODE = /^[1-9][0-9]{5}$/;

export const REGEX_ID_CARD =
    /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;

export const REGEX_FILE_EXCEL = /(.xls|.xlsx)$/;

export const REGEX_FILE_WORD = /(.doc|.docx)$/;

export const REGEX_FILE_OFFICE = /(.xls|.xlsx|.doc|.docx)$/;

export const REGEX_DATE = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;

export const REGEX_DATETIME = /^[1-9]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\s+(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/;

export const REGEX_TIME = /^(20|21|22|23|[0-1]\d):[0-5]\d:[0-5]\d$/;

