package com.modak.infrastructure.controller;

import com.modak.RateLimiterApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = RateLimiterApplication.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class EmailSenderResourceTest {

    @Autowired
    MockMvc mockMvc;

    private MockHttpServletResponse getEmailSenderResponse(String body) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/email-sender")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)).andReturn().getResponse();
    }

    @Test
    void sendEmailOK() throws Exception {

        var body = "{\n" +
                "    \"receiverUserId\":\"user11\",\n" +
                "    \"message\":\"Message2\",\n" +
                "    \"emailType\":\"STATUS\"\n" +
                "}";

        var result = getEmailSenderResponse(body);

        assertEquals(200, result.getStatus());
    }

    @Test
    void sendEmailFAILInvalidEmailType() throws Exception {

        var body = "{\n" +
                "    \"receiverUserId\":\"user12\",\n" +
                "    \"message\":\"Message2\",\n" +
                "    \"emailType\":\"STATUS2\"\n" +
                "}";

        var result = getEmailSenderResponse(body);
        var response = result.getContentAsString(StandardCharsets.UTF_8);

        assertEquals(422, result.getStatus());
        assertEquals("Invalid email type", response);
    }

    @Test
    void sendEmailActivateLimitRater() throws Exception {

        var body = "{\n" +
                "    \"receiverUserId\":\"user13\",\n" +
                "    \"message\":\"Message1\",\n" +
                "    \"emailType\":\"NEWS\"\n" +
                "}";

        var result = getEmailSenderResponse(body);
        var response = result.getContentAsString(StandardCharsets.UTF_8);

        assertEquals(200, result.getStatus());

        result = getEmailSenderResponse(body);
        response = result.getContentAsString(StandardCharsets.UTF_8);

        assertEquals(429, result.getStatus());
        assertEquals("Reached the notification limit: Reached notification limit type: NEWS for user: user13", response);

        result = getEmailSenderResponse(body);
        response = result.getContentAsString(StandardCharsets.UTF_8);

        assertEquals(429, result.getStatus());
        assertEquals("Reached the notification limit: Reached notification limit type: NEWS for user: user13", response);
    }

    @Test
    void sendEmailActivateLimitRaterWitTwo2Users() throws Exception {

        var bodyUser1 = "{\n" +
                "    \"receiverUserId\":\"user1\",\n" +
                "    \"message\":\"Message1\",\n" +
                "    \"emailType\":\"NEWS\"\n" +
                "}";

        var bodyUser2 = "{\n" +
                "    \"receiverUserId\":\"user2\",\n" +
                "    \"message\":\"Message1\",\n" +
                "    \"emailType\":\"NEWS\"\n" +
                "}";

        var result = getEmailSenderResponse(bodyUser1);
        var response = result.getContentAsString(StandardCharsets.UTF_8);

        assertEquals(200, result.getStatus());

        result = getEmailSenderResponse(bodyUser2);
        response = result.getContentAsString(StandardCharsets.UTF_8);

        assertEquals(200, result.getStatus());

        result = getEmailSenderResponse(bodyUser1);
        response = result.getContentAsString(StandardCharsets.UTF_8);

        assertEquals(429, result.getStatus());
        assertEquals("Reached the notification limit: Reached notification limit type: NEWS for user: user1", response);

        result = getEmailSenderResponse(bodyUser2);
        response = result.getContentAsString(StandardCharsets.UTF_8);

        assertEquals(429, result.getStatus());
        assertEquals("Reached the notification limit: Reached notification limit type: NEWS for user: user2", response);
    }
}
