package uk.me.vucko.stefan.srv1.service.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import uk.me.vucko.stefan.srv1.service.dao.Srv1Exceptions.AuthenticationException;

import uk.me.vucko.stefan.srv1.cassdb.DbContext;
import uk.me.vucko.stefan.srv1.cassdb.tables.Role;
import uk.me.vucko.stefan.srv1.cassdb.tables.User;
import uk.me.vucko.stefan.srv1.service.dao.LoginResponse;

@Configuration
public class UserAPIController {

	private static final Logger logger = LoggerFactory.getLogger(UserAPIController.class);

	@Autowired
	DbContext dbContext;

	public UserAPIController() {
		super();
	}

    private boolean hasPrivilege(User user, String privilege) {
		Role userRole = dbContext.getRoleRepo().fetchByRoleId(user.getRoleId());
		if (userRole == null) {
			return false;
		}
		if (userRole.getPermissions() == null || !userRole.getPermissions().contains(privilege)) {
			return false;
		}
		return true;
    }

    private void validateMandatoryField(User user, Object field, String errCode, String errMessage) throws AuthenticationException {
		if (field == null) {
			// log here
			throw new AuthenticationException(errMessage);
		}
    }

	public User getUser4Username(String username) throws AuthenticationException {
    	User user = dbContext.getUserRepo().fetchByUserId(username);
    	if (user == null) {
			logger.warn("No user configured with username: '{}'", username);
    		throw new AuthenticationException("No user with that username");	    		
    	}
    	if (user.isBlocked()) {
			logger.warn("User with UUID '{}' has been blocked", username);
    		throw new AuthenticationException("User is not allowed access");
    	}
    	return user;
    }
	
    /*
     *  process login
     */
    public LoginResponse processLogin(User user) 
    {
    	LoginResponse response = new LoginResponse();
    	response.setDisplayName(user.getDisplayName());
    	Role role = dbContext.getRoleRepo().fetchByRoleId(user.getRoleId());
    	if (role == null) {
			// TODO: database inconsistency
    		return null;
    	}
    	response.setPermissions(role.getPermissions());
    	return response;
    }
}
