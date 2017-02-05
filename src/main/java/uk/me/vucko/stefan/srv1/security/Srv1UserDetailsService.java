package uk.me.vucko.stefan.srv1.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uk.me.vucko.stefan.srv1.cassdb.DbContext;
import uk.me.vucko.stefan.srv1.cassdb.tables.Role;

@Service("Srv1UserDetailsService")
public class Srv1UserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(Srv1UserDetailsService.class);

	@Autowired
	DbContext dbContext;
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("UserDetailsService: loading user for username: '{}'", username);
    	uk.me.vucko.stefan.srv1.cassdb.tables.User user = dbContext.getUserRepo().fetchByUserId(username);
    	if (user == null) {
			logger.warn("No user configured with username: '{}'", username);
    		throw new UsernameNotFoundException("No user with that username");
    	}
    	if (user.isBlocked()) {
			logger.warn("User with username '{}' has been blocked", username);
    		throw new UsernameNotFoundException("User is not allowed access");
    	}

    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	if (user.getRoleId() != null) {
    		Role role = dbContext.getRoleRepo().fetchByRoleId(user.getRoleId());
    		if (role != null) {
    			if (role.getPermissions() != null) {
    				for (String perm : role.getPermissions()) {
    			    	authorities.add(new SimpleGrantedAuthority(perm));
    				}
    			}
    		} else {
        		throw new UsernameNotFoundException(String.format("Role %s not found for user %s", user.getRoleId().toString(), user.getUserId()));
    		}
    	} else {
    		throw new UsernameNotFoundException(String.format("Role not assigned to user %s", user.getUserId()));
    	}
		logger.info("User {} assigned the following authorities: '{}'", username, authorities);
        UserDetails userDetails = new User(username, user.getPasswordHash(), true, true, true, !user.isBlocked(), authorities);
		return userDetails;
	}
}
