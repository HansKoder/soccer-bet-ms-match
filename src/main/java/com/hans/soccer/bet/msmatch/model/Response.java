package com.hans.soccer.bet.msmatch.model;

public class Response {

    private Boolean isError;

    private String errorMessage;

    private Object data;

    public Response (ResponseBuilder builder) {
        this.isError = builder.isError;
        this.data = builder.data;
        this.errorMessage = builder.errorMessage;
    }

    public Boolean getError() {
        return isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Object getData() {
        return data;
    }

    public static class ResponseBuilder {

        private Boolean isError;

        private String errorMessage;

        private Object data;

        public ResponseBuilder () {
            this.isError = Boolean.FALSE;
        }

        public ResponseBuilder addIsError (Boolean isError) {
            this.isError  = isError;
            return this;
        }

        public ResponseBuilder addErrorMessage (String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public ResponseBuilder addData (Object data) {
            this.data = data;
            return this;
        }

        public Response build () {
            return new Response(this);
        }

    }

}
