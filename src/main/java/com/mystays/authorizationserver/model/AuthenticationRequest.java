package com.mystays.authorizationserver.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {

    private String username;
    private String password;
}
