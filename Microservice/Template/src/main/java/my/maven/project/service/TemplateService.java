package my.maven.project.service;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import my.maven.project.entity.Users;
import my.maven.project.repository.TemplateRepository;

@Service
public class TemplateService {
	
	private Logger log = LoggerFactory.getLogger(TemplateService.class);
	
	@Autowired
	TemplateRepository templateRepository;

    @Transactional( propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class } )
	public Iterable<Users> getAllUsers() {
    	
    	log.debug("INIZIO getAllUsers");
		
		Iterable<Users> users = templateRepository.findAll();
		
		log.debug("FINE getAllUsers - usersList: {}", users);
		
		return users;
		
	}
	
    @Transactional( propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = { Exception.class } )
	public Users getUserById(Long userId) {
    	
    	log.debug("INIZIO getUserById - userId: {}", userId);
		
    	Users users = templateRepository.findById( userId )
//				.get() // java.util.NoSuchElementException: No value present
//				.map( user2 -> modelMapper.map(user2, UsersDto.class) )
			    .orElseThrow( () -> new NoSuchElementException("Nessun elemento trovato!") ) // java.lang.IllegalArgumentException: at java.util.Optional.orElseThrow(Optional.java:290) ~[na:1.8.0_131]
			;
    	
    	log.debug("FINE getUserById - users: {}", users);
		
		return users;
		
	}
    
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class } )
	public Users createUser(Users users) {
    	
    	log.debug("INIZIO createUser - users: {}", users);
		
		Users userSaved = templateRepository.save(users);
		
		log.debug("FINE createUser - userSaved: {}", userSaved);
		
		return userSaved;
		
	}
    
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class } )
	public Users updateUser(Users users) {
    	
    	log.debug("INIZIO updateUser - users: {}", users);
		
    	templateRepository.findById(users.getUserId()).orElseThrow(() -> new EntityNotFoundException(users.toString()));
    	
		Users userSaved = templateRepository.save(users);
		
		log.debug("FINE updateUser - userSaved: {}", userSaved);
		
		return userSaved;
		
	}
    
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class } )
	public void deleteUser(Long userId) {
    	
    	log.debug("INIZIO deleteUser - userId: {}", userId);
		
    	if (templateRepository.existsById( userId )) {
    		templateRepository.deleteById( userId );
		} else {
            throw new EntityNotFoundException(userId.toString());
        }
    	
    	log.debug("FINE deleteUser");
		
	}

}
