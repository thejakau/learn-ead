package com.sltc.dait.library.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class Mail {
    private String from;
    private String to;
    private String subject;
    private String body;
}