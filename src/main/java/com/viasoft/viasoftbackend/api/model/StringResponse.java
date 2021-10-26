package com.viasoft.viasoftbackend.api.model;

import lombok.Data;

@Data
public class StringResponse {
    private String response;

    public StringResponse(String s){
        this.response = s;
    }
}
