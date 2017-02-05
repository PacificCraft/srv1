package uk.me.vucko.stefan.srv1.service.web;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.me.vucko.stefan.srv1.cassdb.tables.User;
import uk.me.vucko.stefan.srv1.security.CustomAuthenticationProvider;
import uk.me.vucko.stefan.srv1.service.dao.LoginRequest;
import uk.me.vucko.stefan.srv1.service.dao.LoginResponse;
import uk.me.vucko.stefan.srv1.service.dao.Srv1Exceptions.AuthenticationException;
import uk.me.vucko.stefan.srv1.service.local.UserAPIController;

@Controller
@RestController
public class UserAPIControllerWeb {

	private static final Logger logger = LoggerFactory.getLogger(UserAPIControllerWeb.class);

    @Autowired
    ConfigurableApplicationContext context;

    @Autowired
    UserAPIController localController;
    
	@Autowired
	CustomAuthenticationProvider authenticationProvider;

	@Autowired
	 private ErrorAttributes errorAttributes;

	 @Bean
	 public AppErrorController appErrorController()
	 {
		 return new AppErrorController(errorAttributes);
	 }
	 
	private String readPostBuffer(HttpServletRequest request) {
    	try {
			return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException e) {
			return null; //TODO
		}
    }

    private void wrapJsonResponse(String jsonResponse, HttpServletResponse response) throws IOException {
		response.setContentType("text/x-json;charset=UTF-8");           
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(jsonResponse);
    }


	public void processLogin(
		HttpServletRequest request, 
		HttpServletResponse response
	) throws IOException, AuthenticationException		
	{
		String rawData = readPostBuffer(request);
		LoginRequest loginRequest = LoginRequest.readJSON(rawData); 
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authentication = this.authenticationProvider.authenticate(token);
		logger.debug("Logging in with [{}]", authentication.getPrincipal());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// get user details for the authenticated user
		User user = localController.getUser4Username(SecurityContextHolder.getContext().getAuthentication().getName()); // session not yet set 
		logger.info("Login request received from the user '{}' (name='{}')", user.getUserId(), user.getDisplayName());
		// process the request
		LoginResponse loginResponse = localController.processLogin(user);
		// wrap the response
		if (loginResponse != null) {
			String jsonResponse = loginResponse.getAsJSON();
			wrapJsonResponse(jsonResponse, response);
			logger.info("Login request completed - user '{}' (name='{}')", user.getUserId(), user.getDisplayName());
		} else {
			logger.warn("Login request from the user '{}' had empty response", user.getUserId());
		}
	}

	@RequestMapping(value = "/api/test", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('Permission1')")
	public void processTest(
		HttpServletRequest request, 
		HttpServletResponse response
	) throws IOException, AuthenticationException		
	{
			wrapJsonResponse("{'response':'test'}", response);
	}

}
