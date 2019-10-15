package ie.gov.agriculture.fisheries.la.capacityservice.user;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import lombok.Getter;
import lombok.ToString;

/**
 * Utility class for UserPrincipal details
 * 
 * @author garret.okeeffe
 *
 */
@Getter
@ToString
public class UserPrincipal {
	private static final String UNKNOWN = "[Unknown]";
	
	private String username = UNKNOWN;
	private String name = UNKNOWN;
	private String email = UNKNOWN;
	private Collection<GrantedAuthority> roles;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserPrincipal.class);

	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	/**
	 * Retrieve user info from KeyCloak session token
	 * 
	 * @param request
	 */
	private UserPrincipal(HttpServletRequest request) {
		try {
			KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
			
			if (token==null) {
				LOGGER.info("Failed reading user informtion from request: token is null!");
			}
			else {
				AccessToken accessToken = ((SimpleKeycloakAccount) token.getDetails()).getKeycloakSecurityContext()
						.getToken();

				username = accessToken.getPreferredUsername();
				name = accessToken.getName();
				email = accessToken.getEmail();
				roles = token.getAuthorities();
			}
		} catch (Exception e) {
			LOGGER.error("Failed reading user informtion from request", e);
		}
	}

	/**
	 * Indicates if there is authority
	 * 
	 * @param authority
	 * @return true if the authority is valid
	 */
	public boolean hasAuthority(String authority) {
		return (this.roles.stream().filter(s -> s.getAuthority().equals(authority)).count() == 1);
	}

	/**
	 * Retrieve user principal object
	 * 
	 * @param request
	 * @return UserPrincipal object
	 */
	public static UserPrincipal getUserPrincipal(HttpServletRequest request) {
		return new UserPrincipal(request);
	}

	/**
	 * log some user related details
	 */
	public void logDetails() {
		LOGGER.info("Username:{}, Name:{}, eMail:{}, Roles:{}", username, name, email, roles);
		LOGGER.info("ROLE_USER:{}, ROLE_ADMIN:{}", hasAuthority(UserPrincipal.ROLE_USER), hasAuthority(UserPrincipal.ROLE_ADMIN));
	}
}