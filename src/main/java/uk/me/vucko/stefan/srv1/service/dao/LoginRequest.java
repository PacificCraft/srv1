package uk.me.vucko.stefan.srv1.service.dao;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginRequest implements Serializable  {

	private String username;
	private String password;

    @JsonIgnore
	private static final long serialVersionUID = 3864640636211046156L;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
    public static LoginRequest readJSON(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        LoginRequest msg = mapper.readValue(jsonString, LoginRequest.class);
        return msg;
    }

    @JsonIgnore
    public String getAsJSON()
            throws JsonProcessingException, UnsupportedEncodingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
