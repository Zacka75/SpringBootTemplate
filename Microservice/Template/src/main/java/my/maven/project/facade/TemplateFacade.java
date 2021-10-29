package my.maven.project.facade;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import my.maven.project.bean.dto.UserParamsDto;
import my.maven.project.bean.dto.UsersDto;
import my.maven.project.entity.Users;
import my.maven.project.service.TemplateService;

@Component
public class TemplateFacade {
	
	private Logger log = LoggerFactory.getLogger(TemplateFacade.class);
	
	@Autowired
	TemplateService templateService;
	
	@Autowired
	RestTemplate restTemplate;
	
    @Autowired
    private ModelMapper modelMapper;

	public List<UsersDto> getAllUsers() {
		
		log.debug("INIZIO getAllUsers");
		
		List<UsersDto> usersList = new ArrayList<UsersDto>();
				
		Iterable<Users> users = templateService.getAllUsers();
		
		users.forEach(elem -> {
        	 UsersDto usersDto = modelMapper.map(elem, UsersDto.class);
        	 log.debug("usersDto: {}", usersDto);
        	 usersList.add(usersDto);
         });
		
		log.debug("FINE getAllUsers - usersList: {}", usersList);
		
		return usersList;
		
	}
	
	public UsersDto getUserById(UserParamsDto userParamsDto) {
		
		log.debug("INIZIO getUserById - userParamsDto: {}", userParamsDto);
		
		Users users = templateService.getUserById( userParamsDto.getUserId() )
//			.get() // java.util.NoSuchElementException: No value present
//			.map( user2 -> modelMapper.map(user2, UsersDto.class) )
//		    .orElseThrow( () -> new NoSuchElementException("Nessun elemento trovato!") ) // java.lang.IllegalArgumentException: at java.util.Optional.orElseThrow(Optional.java:290) ~[na:1.8.0_131]
		;
		
		UsersDto usersDto = modelMapper.map(users, UsersDto.class);
		
		log.debug("FINE getUserById - usersDto: {}", usersDto);
		
		return usersDto;
		
	}
    
	public UsersDto createUser(UsersDto usersDto) {
		
		log.debug("INIZIO createUser - usersDto: {}", usersDto);
		
		Users user = modelMapper.map(usersDto, Users.class);
		
		Users userSaved = templateService.createUser(user);
		
		UsersDto usersCreatedDto = modelMapper.map(userSaved, UsersDto.class);
		
		log.debug("FINE createUser - usersCreatedDto: {}", usersCreatedDto);
		
		return usersCreatedDto;
		
	}
    
	public UsersDto updateUser(UsersDto usersDto) {
		
		log.debug("INIZIO updateUser - usersDto: {}", usersDto);
		
		Users user = templateService.getUserById( usersDto.getUserId() );
		
//		Users user = modelMapper.map(templateRequest.getUserDto(), Users.class);
		user.setUsername(usersDto.getUsername());
		user.setPassword(usersDto.getPassword());
		
		Users userSaved = templateService.updateUser(user);
		
		UsersDto usersSavedDto = modelMapper.map(userSaved, UsersDto.class);
		
		log.debug("FINE updateUser - usersSavedDto: {}", usersSavedDto);
		
		return usersSavedDto;
		
	}
    
	public void deleteUser(UserParamsDto userParamsDto) {
		
		log.debug("INIZIO deleteUser - userParamsDto: {}", userParamsDto);
		
    	templateService.deleteUser( userParamsDto.getUserId() );
    	
    	log.debug("FINE deleteUser");
		
	}
	
	@HystrixCommand( fallbackMethod = "getDefaultResponse" )
	public UsersDto getUserByIdFallback(UserParamsDto userParamsDto) {
		
		log.debug("INIZIO getUserById - userParamsDto: {}", userParamsDto);
		
		Users users = templateService.getUserById( userParamsDto.getUserId() );
		
		UsersDto usersDto = modelMapper.map(users, UsersDto.class);
		
		restTemplate.getForEntity("/", String.class);

		log.debug("FINE getUserById - usersDto: {}", usersDto);
		
		return usersDto;
		
	}	
	
	public UsersDto getDefaultResponse(UserParamsDto userParamsDto) {
		
		log.debug("DEFAULT RESPONSE - userParamsDto: {}", userParamsDto);
		
		return UsersDto.builder().build();
		
	}

}
