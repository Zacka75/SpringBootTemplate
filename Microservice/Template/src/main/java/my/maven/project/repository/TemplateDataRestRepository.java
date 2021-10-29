package my.maven.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import my.maven.project.entity.Users;

/**
 * https://www.baeldung.com/spring-data-rest-intro
 * 
 * @author andrea
 *
 */
//@RepositoryRestResource(excerptProjection = NoAddresses.class)
//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
@RepositoryRestResource
//@RepositoryRestResource(collectionResourceRel = "users", path = "users") // NON OBBLIGATORIA SERVE PER RIDEFINIRE LO USERS IN UN ALTRO NOME NEL JSON DI OUTPUT
public interface TemplateDataRestRepository extends PagingAndSortingRepository<Users, Long> {
	
//	curl http://localhost:8080/Template/
//	{
//	  "_links" : {
//	    "userses" : {
//	      "href" : "http://localhost:8080/Template/userses{?page,size,sort}",
//	      "templated" : true
//	    },
//	    "profile" : {
//	      "href" : "http://localhost:8080/Template/profile"
//	    }
//	  }
//	}
	
//	curl http://localhost:8080/Template/userses
//	curl http://localhost:8080/Template/userses/?page=1&size=5
//	curl http://localhost:8080/Template/userses/search/nameStartsWith?name=K&sort=name,desc
//	{
//	  "_embedded" : {
//	    "userses" : [ {
//	      "username" : "Pippo",
//	      "password" : "Pluto",
//	      "_links" : {
//	        "self" : {
//	          "href" : "http://localhost:8080/Template/userses/1"
//	        },
//	        "users" : {
//	          "href" : "http://localhost:8080/Template/userses/1"
//	        }
//	      }
//	    } ]
//	  },
//	  "_links" : {
//	    "self" : {
//	      "href" : "http://localhost:8080/Template/userses{?page,size,sort}",
//	      "templated" : true
//	    },
//	    "profile" : {
//	      "href" : "http://localhost:8080/Template/profile/userses"
//	    },
//	    "search" : {
//	      "href" : "http://localhost:8080/Template/userses/search"
//	    }
//	  },
//	  "page" : {
//	    "size" : 20,
//	    "totalElements" : 1,
//	    "totalPages" : 1,
//	    "number" : 0
//	  }
//	}
	
// curl http://localhost:8080/DME_Template/userses/1/
//	{
//		  "username" : "Pippo",
//		  "password" : "Pluto",
//		  "_links" : {
//		    "self" : {
//		      "href" : "http://localhost:8080/Template/userses/1"
//		    },
//		    "users" : {
//		      "href" : "http://localhost:8080/Template/userses/1"
//		    }
//		  }
//		}	

//	curl http://localhost:8080/Template/userses/2/
//	curl http://localhost:8080/Template/userses/name/Pippo/ : m.m.m.a.ExceptionHandlerExceptionResolver Resolved [org.springframework.data.rest.webmvc.ResourceNotFoundException: EntityRepresentationModel not found!]

//	curl http://localhost:8080/Template/userses/findByUsernameStartsWith/
//	curl http://localhost:8080/Template/userses/findByUsernameStartsWith/name/Pippo/
	// curl -i -H "Content-Type:application/json" -d '{"firstName": "Frodo", "lastName": "Baggins"}' http://localhost:8080/people
	// curl -i -H "Content-Type:application/json" -d '{"name": "Frodo"}' http://localhost:8080/people
//	@RestResource(path = "nameStartsWith", rel = "nameStartsWith")
//	@RestResource(path = "names", rel = "demo1")
//	@Override
//	@RestResource(exported = true)
	@RestResource(path = "usernameStartsWith", rel = "usernameStartsWith")
	public Page<Users> findByUsernameStartsWith(@Param("name") String name, Pageable p);
	
//	  "_links" : {
//	    "self" : {
//	      "href" : "http://localhost:8080/users/search/findByName?name=test"
//	    }
//	  }
//	List<Users> findByName(@Param("name") String name);

}
