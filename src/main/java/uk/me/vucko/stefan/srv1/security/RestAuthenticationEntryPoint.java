package uk.me.vucko.stefan.srv1.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import uk.me.vucko.stefan.srv1.service.web.UserAPIControllerWeb;

@Component( "restAuthenticationEntryPoint" )
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Autowired
    UserAPIControllerWeb remoteController;

    @Override
    public void commence(
    	      HttpServletRequest request,
    	      HttpServletResponse response, 
    	      AuthenticationException authException
    		)  throws IOException
    {
    	try {
			remoteController.processLogin(request, response);
			return;
		} catch (uk.me.vucko.stefan.srv1.service.dao.Srv1Exceptions.AuthenticationException e) {
	    	response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
		}
    }
}