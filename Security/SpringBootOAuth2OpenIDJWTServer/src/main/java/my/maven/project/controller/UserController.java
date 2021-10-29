package my.maven.project.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * curl -X POST --user 'fooClientIdPassword:secret' -d 'grant_type=password&username=john&password=123' http://localhost:8081/SpringSecurityOAuth2Server/oauth/token
 * {"access_token":"f201c135-5158-4116-88e1-35b3505db138","token_type":"bearer","refresh_token":"f827c72f-3414-4292-a517-7b410a48f70b","expires_in":35907,"scope":"foo read write","organization":"johnWbox"}
 * 
 * curl -i -H "Accept: application/json" -X GET http://localhost:8088/SpringWebLoginBootSecurityOAuth2Resource/users/extra
 * HTTP/1.1 401 
 * Access-Control-Allow-Origin: *
 * Access-Control-Allow-Methods: POST, PUT, GET, OPTIONS, DELETE
 * Access-Control-Allow-Headers: Authorization, Content-Type
 * Access-Control-Max-Age: 3600
 * Pragma: no-cache
 * WWW-Authenticate: Bearer realm="oauth2-resource", error="unauthorized", error_description="Full authentication is required to access this resource"
 * Cache-Control: no-store
 * X-Content-Type-Options: nosniff
 * X-XSS-Protection: 1; mode=block
 * X-Frame-Options: DENY
 * Content-Type: application/json;charset=UTF-8
 * Transfer-Encoding: chunked
 * Date: Wed, 05 Feb 2020 21:42:22 GMT
 * {"error":"unauthorized","error_description":"Full authentication is required to access this resource"}
 * 
 * curl -i -H "Accept: application/json" -H "Authorization: Bearer f201c135-5158-4116-88e1-35b3505db138" -X GET http://localhost:8088/SpringWebLoginBootSecurityOAuth2Resource/users/extra
 * HTTP/1.1 500 
 * Access-Control-Allow-Origin: *
 * Access-Control-Allow-Methods: POST, PUT, GET, OPTIONS, DELETE
 * Access-Control-Allow-Headers: Authorization, Content-Type
 * Access-Control-Max-Age: 3600
 * Set-Cookie: JSESSIONID=2502AF100944EF25E2CD5349E3B960F2; Path=/spring-security-oauth-resource; HttpOnly
 * X-Content-Type-Options: nosniff
 * X-XSS-Protection: 1; mode=block
 * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
 * Pragma: no-cache
 * Expires: 0
 * X-Frame-Options: DENY
 * Content-Type: application/json;charset=UTF-8
 * Transfer-Encoding: chunked
 * Date: Wed, 05 Feb 2020 21:44:49 GMT
 * Connection: close
 * {"timestamp":1580939089262,"status":500,"error":"Internal Server Error","message":"No message available","trace":"java.lang.NullPointerException\n\tat com.baeldung.web.controller.UserController.getExtraInfo(UserController.java:22)
 * 
 * curl -i -H "Accept: application/json" -H "Authorization: Bearer f201c135-5158-4116-88e1-35b3505db139" -X GET http://localhost:8088/SpringWebLoginBootSecurityOAuth2Resource/users/extra
 * HTTP/1.1 401 
 * Access-Control-Allow-Origin: *
 * Access-Control-Allow-Methods: POST, PUT, GET, OPTIONS, DELETE
 * Access-Control-Allow-Headers: Authorization, Content-Type
 * Access-Control-Max-Age: 3600
 * Pragma: no-cache
 * WWW-Authenticate: Bearer realm="oauth2-resource", error="invalid_token", error_description="Invalid access token: f201c135-5158-4116-88e1-35b3505db139"
 * Cache-Control: no-store
 * X-Content-Type-Options: nosniff
 * X-XSS-Protection: 1; mode=block
 * X-Frame-Options: DENY
 * Content-Type: application/json;charset=UTF-8
 * Transfer-Encoding: chunked
 * Date: Wed, 05 Feb 2020 21:45:04 GMT
 * {"error":"invalid_token","error_description":"Invalid access token: f201c135-5158-4116-88e1-35b3505db139"}
 * 
 * @author andrea
 *
 */
/**
 * curl -X POST --user 'fooClientIdPassword:secret' -d 'grant_type=password&username=john&password=123' http://localhost:8081/SpringSecurityOAuth2Server/oauth/token
 * {"access_token":"f201c135-5158-4116-88e1-35b3505db138","token_type":"bearer","refresh_token":"f827c72f-3414-4292-a517-7b410a48f70b","expires_in":35173,"scope":"foo read write","organization":"johnWbox"}
 * 
 * curl -i -H "Accept: application/json" -H "Authorization: Bearer f201c135-5158-4116-88e1-35b3505db138" -X GET http://localhost:8088/SpringWebLoginBootSecurityOAuth2Resource/foos/1/
 * HTTP/1.1 200 
 * Access-Control-Allow-Origin: *
 * Access-Control-Allow-Methods: POST, PUT, GET, OPTIONS, DELETE
 * Access-Control-Allow-Headers: Authorization, Content-Type
 * Access-Control-Max-Age: 3600
 * Set-Cookie: JSESSIONID=57217BAC902332B637488032F1C63B21; Path=/spring-security-oauth-resource; HttpOnly
 * X-Content-Type-Options: nosniff
 * X-XSS-Protection: 1; mode=block
 * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
 * Pragma: no-cache
 * Expires: 0
 * X-Frame-Options: DENY
 * Content-Type: application/json;charset=UTF-8
 * Transfer-Encoding: chunked
 * Date: Wed, 05 Feb 2020 22:14:08 GMT
 * {"id":84,"name":"takL"}
 * 
 * 
 * @author andrea
 *
 */
/**
 * curl -X POST --user 'fooClientIdPassword:secret' -d 'grant_type=password&username=john&password=123' http://localhost:8088/SpringWebLoginBootSecurityOAuth2Resource/oauth/token
 * {"access_token":"f201c135-5158-4116-88e1-35b3505db138","token_type":"bearer","refresh_token":"f827c72f-3414-4292-a517-7b410a48f70b","expires_in":35173,"scope":"foo read write","organization":"johnWbox"} 
 * 
 * curl -i -H "Accept: application/json" -H "Authorization: Bearer f201c135-5158-4116-88e1-35b3505db138" -X GET http://localhost:8088/SpringWebLoginBootSecurityOAuth2Resource/bars/1/
 * HTTP/1.1 403 
 * Access-Control-Allow-Origin: *
 * Access-Control-Allow-Methods: POST, PUT, GET, OPTIONS, DELETE
 * Access-Control-Allow-Headers: Authorization, Content-Type
 * Access-Control-Max-Age: 3600
 * Set-Cookie: JSESSIONID=1D2CD639FD3C81AF68A010955799DA5B; Path=/spring-security-oauth-resource; HttpOnly
 * Pragma: no-cache
 * WWW-Authenticate: Bearer error="insufficient_scope", error_description="Insufficient scope for this resource", scope="bar"
 * Cache-Control: no-store
 * X-Content-Type-Options: nosniff
 * X-XSS-Protection: 1; mode=block
 * X-Frame-Options: DENY
 * Content-Type: application/json;charset=UTF-8
 * Transfer-Encoding: chunked
 * Date: Wed, 05 Feb 2020 21:52:58 GMT
 * {"error":"insufficient_scope","error_description":"Insufficient scope for this resource","scope":"bar"}
 * 
 * curl -X POST --user 'sampleClientId:secret' -d 'grant_type=password&username=john&password=123' http://localhost:8081/SpringSecurityOAuth2Server/oauth/token
 * {"error":"invalid_client","error_description":"Unauthorized grant type: password"}
 * 
 * curl -X POST --user 'sampleClientId:secret' -d 'grant_type=password&username=john&password=123' http://localhost:8081/SpringSecurityOAuth2Server/oauth/token
 * {"error":"invalid_grant","error_description":"Implicit grant type not supported
 * 
 * curl -X POST --user 'barClientIdPassword:secret' -d 'grant_type=password&username=john&password=123' http://localhost:8081/SpringSecurityOAuth2Server/oauth/token
 * {"access_token":"3d270f8e-6c1a-40b7-bbc6-7e986cb063db","token_type":"bearer","refresh_token":"f12506ef-1cfa-4afa-b08e-4058a52880a6","expires_in":35999,"scope":"bar read write","organization":"johntFux"}
 * 
 * curl -i -H "Accept: application/json" -H "Authorization: Bearer 3d270f8e-6c1a-40b7-bbc6-7e986cb063db" -X GET http://localhost:8088/SpringWebLoginBootSecurityOAuth2Resource/bars/1/
 * HTTP/1.1 200 
 * Access-Control-Allow-Origin: *
 * Access-Control-Allow-Methods: POST, PUT, GET, OPTIONS, DELETE
 * Access-Control-Allow-Headers: Authorization, Content-Type
 * Access-Control-Max-Age: 3600
 * Set-Cookie: JSESSIONID=728D3406F95B702D490D257201EDB732; Path=/spring-security-oauth-resource; HttpOnly
 * X-Content-Type-Options: nosniff
 * X-XSS-Protection: 1; mode=block
 * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
 * Pragma: no-cache
 * Expires: 0
 * X-Frame-Options: DENY
 * Content-Type: application/json;charset=UTF-8
 * Transfer-Encoding: chunked
 * Date: Wed, 05 Feb 2020 22:10:05 GMT
 * {"id":29,"name":"wtyM"}
 * 
 * @author andrea
 *
 */
@Controller
public class UserController {

    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(method = RequestMethod.GET, value = "/users/extra")
    @ResponseBody
    public Map<String, Object> getExtraInfo(Authentication auth) {
        OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
        Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
        System.out.println("User organization is " + details.get("organization"));
        return details;
    }
    
//    // API - read
//    // SENZA AUTH: curl -i -H "Accept: application/json" -X GET http:ost:8088/SpringWebLoginBootSecurityOAuth2Resource/foos/1/ -> {"id":56,"name":"ysLQ"}
//    // CON AUTH: -> {"error":"unauthorized","error_description":"Full authentication is required to access this resource"} ->
//    // CON TOKEN: curl -i -H "Accept: application/json" -H "Authorization: Bearer 3b380e25-2f82-4996-951d-369583a18b8b" -X GET http://localhost:8088/SpringWebLoginBootSecurityOAuth2Resource/foos/1/ -> {"id":58,"name":"FpzX"}
////    @PreAuthorize("#oauth2.hasScope('foo') and #oauth2.hasScope('read') and #oauth2.hasScope('WEBAPP')")
//    @PreAuthorize("#oauth2.hasScope('WEBAPP')")
//    @RequestMapping(method = RequestMethod.GET, value = "/foos/{id}")
//    @ResponseBody
//    public Foo findFooById(@PathVariable final long id) {
//        return new Foo(Long.parseLong(randomNumeric(2)), randomAlphabetic(4));
//    }
//
//    // API - write
//    @PreAuthorize("#oauth2.hasScope('foo') and #oauth2.hasScope('write')")
//    @RequestMapping(method = RequestMethod.POST, value = "/foos")
//    @ResponseStatus(HttpStatus.CREATED)
//    @ResponseBody
//    public Foo create(@RequestBody final Foo foo) {
//        foo.setId(Long.parseLong(randomNumeric(2)));
//        return foo;
//    }
//    
//    // API - read
////    @PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('read') and #oauth2.hasScope('WEBAPP')")
//    @PreAuthorize("#oauth2.hasScope('bar') or #oauth2.hasScope('read') or #oauth2.hasScope('WEBAPP')")
//    @RequestMapping(method = RequestMethod.GET, value = "/bars/{id}")
//    @ResponseBody
//    public Bar findBarById(@PathVariable final long id) {
//        return new Bar(Long.parseLong(randomNumeric(2)), randomAlphabetic(4));
//    }
//
//    // API - write
//    @PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
//    @RequestMapping(method = RequestMethod.POST, value = "/bars")
//    @ResponseStatus(HttpStatus.CREATED)
//    @ResponseBody
//    public Bar create(@RequestBody final Bar bar) {
//        bar.setId(Long.parseLong(randomNumeric(2)));
//        return bar;
//    }
    
}
