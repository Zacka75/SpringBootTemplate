package my.maven.project.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.maven.project.bean.dto.UserParamsDto;
import my.maven.project.bean.dto.UsersDto;
import my.maven.project.facade.TemplateFacade;

/**
 * http://localhost:8080/Template/template/
 * 
 * @author andrea
 *
 * IN CASO DI AUTH SERVER: curl -X POST --user 'fooClientIdPassword:secret' -d "grant_type=password&username=user1&password=user1" http://localhost:8081/spring-security-oauth-server/oauth/token
 *
 */
@RestController
@RequestMapping("/template")
//@Validated
public class TemplateController {
	
	private Logger log = LoggerFactory.getLogger(TemplateController.class);
	
	@Autowired
	TemplateFacade templateFacade;
	
	public TemplateController() {
		log.debug("TemplateController - Costruttore");
	}
	
	@PostConstruct
	public void init() {
		log.debug("TemplateController - @PostConstruct - init");
	}
	
	@Before(value = "")
    protected void setUp() {
		log.debug("TemplateController - @Before - init");
    }
	
	// curl -v -X GET -H "Content-type:application/json" http://localhost:8082/Template/template/findAllUsers
	// curl -v -X GET -H "Content-type:application/json" http://localhost:8080/TemplateAPI/Template/template/findAllUsers
	// curl -v -X GET -H "Content-type:application/json" -H "Authorization: Bearer xxxx" http://localhost:8080/TemplateAPI/Template/template/findAllUsers
	//@PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
	@GetMapping(value="/findAllUsers"
		, headers="Accept=application/json"
		, produces = MediaType.APPLICATION_JSON_VALUE
	    , consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<List<UsersDto>> getAllUsers() {
		
		log.info("INIZIO getAllUsers");
		
		List<UsersDto> response = templateFacade.getAllUsers();
		
		log.info("FINE getAllUsers - response: {}", response);
		
		return ResponseEntity.ok(response);
		
	}
	
	// curl -v -X GET -H "Content-type:application/json" --data '{"userId":"1"}' http://localhost:8082/Template/template/findUserById
	// curl -v -X GET -H "Content-type:application/json" -H "Authorization: Bearer xxxx" --data '{"userId":"1"}' http://localhost:8080/Template/template/findUserById
	@GetMapping(value="/findUserById"
		, headers="Accept=application/json"
		, produces = MediaType.APPLICATION_JSON_VALUE
	    , consumes = MediaType.APPLICATION_JSON_VALUE  )
	public ResponseEntity<UsersDto> getUserById(@Valid @RequestBody UserParamsDto userParamsDto) {
		
		log.info("INIZIO getUserById - userParamsDto: {}", userParamsDto);
		
		UsersDto response = templateFacade.getUserById(userParamsDto);
		
		log.info("FINE getUserById - response: {}", response);
		
		if ( null != response )	return new ResponseEntity<>(response, HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
	// curl -v -X POST -H "Content-type:application/json" -H "Authorization: Bearer xxxx" --data '{"username":"Pippo","password":"Pluto"}' http://localhost:8080/Template/template/createUser
	@PostMapping(value="/createUser"
		, headers="Accept=application/json"
		, produces = MediaType.APPLICATION_JSON_VALUE
	/* , consumes = MediaType.APPLICATION_JSON_VALUE */)
	public ResponseEntity<UsersDto> createUser(@Valid @RequestBody UsersDto usersDto) {
		
		log.info("INIZIO createUser - usersDto: {}", usersDto);
		
		UsersDto response = templateFacade.createUser(usersDto);
		
		log.info("FINE createUser - response: {}", response);
		
		return ResponseEntity.ok(response);
		
	}
	
	// curl -v -X PUT -H "Content-type:application/json" -H "Authorization: Bearer xxxx" --data '{"userId":"1","username":"Pippo1","password":"Pluto1"}' http://localhost:8080/Template/template/updateUser
	@PutMapping(value="/updateUser"
		, headers="Accept=application/json"
		, produces = MediaType.APPLICATION_JSON_VALUE
	    , consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<UsersDto> updateUser(@Valid @RequestBody UsersDto usersDto) {
		
		log.info("INIZIO updateUser - usersDto: {}", usersDto);
		
		UsersDto response = templateFacade.updateUser(usersDto);
		
		log.info("FINE updateUser - response: {}", response);
		
		return ResponseEntity.ok(response);
		
	}
	
	// curl -v -X DELETE -H "Content-type:application/json" -H "Authorization: Bearer xxxx" --data '{"userId":"1"}' http://localhost:8080/Template/template/deleteUser
	@DeleteMapping(value="/deleteUser"
		, headers="Accept=application/json"
		, produces = MediaType.APPLICATION_JSON_VALUE
	    , consumes = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<UsersDto> deleteUser(@Valid @RequestBody UserParamsDto userParamsDto) {
		
		log.info("INIZIO deleteUser - userParamsDto: {}", userParamsDto);
		
		templateFacade.deleteUser(userParamsDto);
		
		log.info("FINE deleteUser");
		
		return ResponseEntity.noContent().build();
		
	}
	
	// curl -v -X GET -H "Content-type:application/json" -H "Authorization: Bearer xxxx" --data '{"userId":"1"}' http://localhost:8080/Template/template/findUserByIdFallback
	@GetMapping(value="/findUserByIdFallback"
		, headers="Accept=application/json"
		, produces = MediaType.APPLICATION_JSON_VALUE
	    , consumes = MediaType.APPLICATION_JSON_VALUE  )
	public ResponseEntity<UsersDto> getUserByIdFallback(@Valid @RequestBody UserParamsDto userParamsDto) {
		
		log.info("INIZIO getUserByIdFallback - userParamsDto: {}", userParamsDto);
		
		UsersDto response = templateFacade.getUserByIdFallback(userParamsDto);
		
		log.info("FINE getUserByIdFallback - response: {}", response);
		
		if ( null != response )	return new ResponseEntity<>(response, HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
}
