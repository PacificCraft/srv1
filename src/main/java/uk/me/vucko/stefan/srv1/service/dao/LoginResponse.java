package uk.me.vucko.stefan.srv1.service.dao;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginResponse implements Serializable  {

	private String displayName;
	private List<String> permissions;

    @JsonIgnore
	private static final long serialVersionUID = 5817017383478953560L;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	@JsonIgnore
    public static LoginResponse readJSON(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        LoginResponse msg = mapper.readValue(jsonString, LoginResponse.class);
        return msg;
    }

    @JsonIgnore
    public String getAsJSON()
            throws JsonProcessingException, UnsupportedEncodingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
