package com.example.userservicemwf.dto;

import com.example.userservicemwf.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequestDto {
    private String token;
}
