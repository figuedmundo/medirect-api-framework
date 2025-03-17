package com.medirect.api.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthDto {
    public String username;
    public String password;
}
