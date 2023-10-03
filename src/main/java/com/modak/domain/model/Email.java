package com.modak.domain.model;

import lombok.Data;

@Data
public class Email {

    String receiverUserId;
    String message;
    String emailType;
}
