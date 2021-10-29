package my.maven.project.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author andrea
 *
 */
@RestController
public class OpenIdConnectClientController {
	
	@GetMapping("/api/user/me")
	public Principal user(Principal principal) {
		return principal;
	}

}
