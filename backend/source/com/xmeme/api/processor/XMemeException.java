package com.xmeme.api.processor;

import com.xmeme.utils.APIErrorMessage;

import java.text.MessageFormat;

public abstract class XMemeException extends Exception {
    private APIErrorMessage.ERRORCODE error_code;

    public APIErrorMessage.ERRORCODE getErrorCode(){
        return this.error_code;
    }
    public XMemeException(APIErrorMessage.ERRORCODE code) {
        this.error_code = code;
    }

    public static class MandatoryKeyNotFound extends XMemeException {
        public MandatoryKeyNotFound(APIErrorMessage.ERRORCODE errorcode, String keyName) {
            super(errorcode);
            System.out.println(MessageFormat.format(APIErrorMessage.MISSING_KEY, keyName));
        }
    }

    public static class ImproperValueSet extends XMemeException {
        public ImproperValueSet(APIErrorMessage.ERRORCODE errorcode, String keyName) {
            super(errorcode);
            System.out.println(MessageFormat.format(APIErrorMessage.IMPROPER_KEY, keyName));
        }
    }

    public static class MemeAlreadyExist extends XMemeException{
        public MemeAlreadyExist() {
            super(APIErrorMessage.ERRORCODE.MEME_ALREADY_EXIST);
        }
    }
}
