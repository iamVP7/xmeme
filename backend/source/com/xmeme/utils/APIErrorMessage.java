package com.xmeme.utils;

public class APIErrorMessage {

    public static final String MISSING_KEY = "This Missing Key is {0}";
    public static final String IMPROPER_KEY = "This Improper Key sent from client is {0}";
    public enum ERRORCODE {
        URL_NOT_PRESENT(404, "URL is Mandatory"),
        IMPROPER_URL(404, "URL should not be more than 1000 chars and should end with jpeg or jpg or png"),
        AUTHOR_NOT_PRESENT(404, "Author is Mandatory"),
        CAPTION_NOT_PRESENT(404, "Caption is Mandatory"),
        IMPROPER_CAPTION(404, "Caption should be less than 95 chars"),
        IMPROPER_NAME(404, "Name should be less than 45 char"),
        MEME_ALREADY_EXIST(409, "Meme already exist; Kindly upload different Meme"),
        INTERNAL_ERROR(500, "Internal Error. will be back in few mins"),
        ;
        private final String responseMessage;
        private final int responseCode;


        ERRORCODE(int errorCode, String errorMessage) {
            this.responseMessage = errorMessage;
            this.responseCode = errorCode;
        }

        public int getResponseCode(){
            return this.responseCode;
        }

        public String getResponseMessage(){
            return this.responseMessage;
        }
    }
}
