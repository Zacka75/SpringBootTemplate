package my.maven.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * curl -X POST --user 'fooClientIdPassword:secret' -d 'grant_type=password&username=user1&password=user1' http://localhost:8081/spring-security-oauth-server/oauth/token
 * {"access_token":"726f0fe6-ca16-43a0-8eb8-f4c7932d6b76","token_type":"bearer","refresh_token":"f827c72f-3414-4292-a517-7b410a48f70b","expires_in":3599,"scope":"foo read write","organization":"johnXBoY"}
 * 
 * curl -i -H "Accept: application/json" -H "Authorization: Bearer 5b620ebf-b995-4f6e-85ac-a5c0c3bc43b5" -X GET http://localhost:8081/spring-security-oauth-server/user/me
 * 
 * 
 * curl -X POST --user 'fooClientIdPassword:secret' -d 'grant_type=password&username=user1&password=user1' http://localhost:8081/spring-security-oauth-server/oauth/token
 * - curl -i -H "Accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyMSIsInNjb3BlIjpbImZvbyIsInJlYWQiLCJ3cml0ZSJdLCJvcmdhbml6YXRpb24iOiJ1c2VyMW9KQ2YiLCJleHAiOjE1ODk1NDkxOTEsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI3MzEwZjUzZi05M2YwLTQxNGItOTMxZS00Zjc0NzgwM2YxM2QiLCJjbGllbnRfaWQiOiJmb29DbGllbnRJZFBhc3N3b3JkIn0.cwpxYDzNwvL3Dy-jyhavigbjRBA6GDpwHQJrZ_18szw" -X GET http://localhost:8081/spring-security-oauth-server/user/me
 * X-Frame-Options: DENY
Location: http://localhost:8081/spring-security-oauth-server/login
 * curl -X POST --user 'fooClientIdPassword:secret' -d 'grant_type=authorization_code&username=user1&password=user1' http://localhost:8081/spring-security-oauth-server/oauth/token -> Resolved [error="invalid_request", error_description="An authorization code must be supplied."]
 * curl -X POST --user 'fooClientIdPassword:secret' -d 'grant_type=refresh_token&username=user1&password=user1' http://localhost:8081/spring-security-oauth-server/oauth/token -> Resolved [error="invalid_token", error_description="Cannot convert access token to JSON"]
 * curl -X POST --user 'fooClientIdPassword:secret' -d 'grant_type=client_credentials&username=user1&password=user1' http://localhost:8081/spring-security-oauth-server/oauth/token
 * - curl -i -H "Accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJmb28iLCJyZWFkIiwid3JpdGUiXSwib3JnYW5pemF0aW9uIjoiZm9vQ2xpZW50SWRQYXNzd29yZEthaWQiLCJleHAiOjE1ODk1NDkzMTEsImp0aSI6ImYyNjYwMGEyLTM1NzgtNDQ3NS04NWM1LTI4MTIxZjE1NTBhNiIsImNsaWVudF9pZCI6ImZvb0NsaWVudElkUGFzc3dvcmQifQ.ou5ph35NhJ046rYQ2GUyM-W-ZsEwWKfzJSSWg85T2YA" -X GET http://localhost:8081/spring-security-oauth-server/user/me
 * X-Frame-Options: DENY
Location: http://localhost:8081/spring-security-oauth-server/login
 *
 *
 * curl -X POST --user 'fooClientIdPassword:secret' -d "grant_type=password&username=user1&password=user1" http://localhost:8081/spring-security-oauth-server/oauth/token
 * 
 * curl -i -H "Accept: application/json" -H "Authorization: Bearer " -X GET http://localhost:8081/spring-security-oauth-server/api/user/me
 * WWW-Authenticate: Bearer realm="oauth2-resource", error="unauthorized", error_description="Full authentication is required to access this resource"
 * curl -i -H "Accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6InVzZXIxIiwic2NvcGUiOlsiZm9vIiwicmVhZCIsIndyaXRlIl0sIm9yZ2FuaXphdGlvbiI6InVzZXIxbk9oQiIsImV4cCI6MTU4OTc5NjI2NywiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImVlM2JhZmExLTYyZjgtNDZlYy1iNjQzLTk5YzNlY2JjMWYwYyIsImNsaWVudF9pZCI6ImZvb0NsaWVudElkUGFzc3dvcmQifQ.UgpdPsFtzGPUBNhRmZGIq-FaMP06xg-qjsK2E_B0xKo" -X GET http://localhost:8081/spring-security-oauth-server/api/user/me
 * {"authorities":[{"authority":"ROLE_USER"}],"details":{"remoteAddress":"0:0:0:0:0:0:0:1","sessionId":null,"tokenValue":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6InVzZXIxIiwic2NvcGUiOlsiZm9vIiwicmVhZCIsIndyaXRlIl0sIm9yZ2FuaXphdGlvbiI6InVzZXIxbk9oQiIsImV4cCI6MTU4OTc5NjI2NywiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImVlM2JhZmExLTYyZjgtNDZlYy1iNjQzLTk5YzNlY2JjMWYwYyIsImNsaWVudF9pZCI6ImZvb0NsaWVudElkUGFzc3dvcmQifQ.UgpdPsFtzGPUBNhRmZGIq-FaMP06xg-qjsK2E_B0xKo","tokenType":"Bearer","decodedDetails":null},"authenticated":true,"userAuthentication":{"authorities":[{"authority":"ROLE_USER"}],"details":null,"authenticated":true,"principal":"user1","credentials":"N/A","name":"user1"},"principal":"user1","clientOnly":false,"oauth2Request":{"clientId":"fooClientIdPassword","scope":["foo","read","write"],"requestParameters":{"client_id":"fooClientIdPassword"},"resourceIds":["oauth2-resource"],"authorities":[],"approved":true,"refresh":false,"redirectUri":null,"responseTypes":[],"extensions":{},"grantType":null,"refreshTokenRequest":null},"credentials":"","name":"user1"}
 *
 * curl -v -X GET -H "Content-type:application/json" http://localhost:8080/DME_Template/template/findAllUsers
 * curl -v -X GET -H "Content-type:application/json" http://localhost:8080/DME_Template/template/findAllUsers
 * curl -i -H "Accept: application/json" -X GET http://localhost:8080/DME_Template/template/findAllUsers
 * curl -X POST --user 'fooClientIdPassword:secret' -d 'grant_type=password&username=user1&password=user1' http://localhost:8081/spring-security-oauth-server/oauth/token
 * curl -i -H "Accept: application/json" -H "Authorization: Bearer " -X GET http://localhost:8080/DME_Template/template/findAllUsers
 * curl -i -H "Accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6InVzZXIxIiwic2NvcGUiOlsiZm9vIiwicmVhZCIsIndyaXRlIl0sIm9yZ2FuaXphdGlvbiI6InVzZXIxVUlrdCIsImV4cCI6MTU4OTgwOTMyNCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6IjJkYmExMmI0LTdlMTAtNDZjMC05ZjgyLTNmZDAxYjJlMGRjMSIsImNsaWVudF9pZCI6ImZvb0NsaWVudElkUGFzc3dvcmQifQ.dQZK1GIUoksVDHDws_3tklD5b1IZ2lys13QFzbLut6s" -X GET http://localhost:8080/DME_Template/template/findAllUsers
 * 
 * 
 * @author andrea
 * 
 * https://howtodoinjava.com/spring-boot2/oauth2-auth-server/
 * 
 * DA WEB AL LINK http://localhost:8081/spring-security-oauth-server/user/me SI INSERISCE NEL FORM user1:user1 .
 * {"authorities":[{"authority":"ROLE_USER"}],"details":{"remoteAddress":"0:0:0:0:0:0:0:1","sessionId":"F27D3C3E1A2CF74C20F2FC88BBA55F25"},"authenticated":true,
 *  "principal":{"password":null,"username":"user1","authorities":[{"authority":"ROLE_USER"}],"accountNonExpired":true,"accountNonLocked":true,"credentialsNonExpired":true,
 *  "enabled":true},"credentials":null,"name":"user1"}
 *  
 *  PER I REST:
 *  http://localhost:8081/spring-security-oauth-server/oauth/authorize?client_id=fooClientIdPassword&response_type=code&scope=read
 *   - Resolved [error="invalid_request", error_description="At least one redirect_uri must be registered with the client."] INSERITA DIRETTIVA .redirectUris("http://localhost:8081/login")
 *  SI ATTERRA AD UNA WEBPAGE CHE DICE: Do you authorize "fooClientIdPassword" to access your protected resources? CLICCARE SU AUTHORIZE
 *  
 *  (It will redirect to a URL like : http://localhost:8081/login?code=EAR76A. Here 'EAR76A' is authorization code for the third party application.)
 *  IL BROWSER REDIREZIONA AL LINK http://localhost:8081/login?code=SXMSkD. SXMSkD E' IL CODICE DI AUTORIZZAZIONE PER APPLICAZIONI DI TERZE PARTY
 *  
 *  ORA DA BROWSER AL LINK http://localhost:8081/spring-security-oauth-server/oauth/token APPARE UN POPUP DI AUTENTICAZIONE DOVE SE INSERIAMO fooClientIdPassword:secret TORNA LA RISPOSTA 
 *  {"error":"method_not_allowed","error_description":"Request method 'GET' not supported"}
 *  SE ESEGUIAMO DA TERMINALE curl -X POST --user 'fooClientIdPassword:secret' -d 'grant_type=password&username=user1&password=user1' http://localhost:8081/spring-security-oauth-server/oauth/token
 *  {"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyMSIsInNjb3BlIjpbImZvbyIsInJlYWQiLCJ3cml0ZSJdLCJvcmdhbml6YXRpb24iOiJ1c2VyMVpPbmciLCJleHAiOjE1ODk1NTMyNjksImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI1MjZkMDVmZS01ODliLTQ3ZjktOGFjYy05MjZmNDUzNDAxNzUiLCJjbGllbnRfaWQiOiJmb29DbGllbnRJZFBhc3N3b3JkIn0.WWbHqOWG1yybk43EZxRdE0YUwhNeq71S-yNu64H7grE",
 *  "token_type":"bearer",
 *  "refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyMSIsInNjb3BlIjpbImZvbyIsInJlYWQiLCJ3cml0ZSJdLCJvcmdhbml6YXRpb24iOiJ1c2VyMVpPbmciLCJhdGkiOiI1MjZkMDVmZS01ODliLTQ3ZjktOGFjYy05MjZmNDUzNDAxNzUiLCJleHAiOjE1OTIxNDE2NjksImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI2MDAwMWEwMi0yZTg0LTQxZGYtOTgwMy01NGI1NDg0YTU0ZGQiLCJjbGllbnRfaWQiOiJmb29DbGllbnRJZFBhc3N3b3JkIn0.fE2zOEigqiRVlMS7AscLq5bN6rnz7GSCd233Dm2NqjE",
 *  "expires_in":3599,"scope":"foo read write","organization":"user1ZOng","jti":"526d05fe-589b-47f9-8acc-926f45340175"}
 *  OPPURE
 *  curl -X POST --user 'fooClientIdPassword:secret' -d "code=SXMSkD&grant_type=authorization_code&redirect_uri=http://localhost:8081/login&scope=read" -H "content-type: application/x-www-form-urlencoded" http://localhost:8081/spring-security-oauth-server/oauth/token     
 *  {"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyMSIsInNjb3BlIjpbInJlYWQiXSwib3JnYW5pemF0aW9uIjoidXNlcjFMWGxvIiwiZXhwIjoxNTg5NTU0MTExLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYjI4YmQ1YTMtYzU2YS00NzE2LWIyNDMtNjg2MTBhN2I3MzBjIiwiY2xpZW50X2lkIjoiZm9vQ2xpZW50SWRQYXNzd29yZCJ9.jpF8vkUlCVWzTXv8PdxuXCKKxEm5tfxiCpJdObYlboc",
 *  "token_type":"bearer",
 *  "refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyMSIsInNjb3BlIjpbInJlYWQiXSwib3JnYW5pemF0aW9uIjoidXNlcjFMWGxvIiwiYXRpIjoiYjI4YmQ1YTMtYzU2YS00NzE2LWIyNDMtNjg2MTBhN2I3MzBjIiwiZXhwIjoxNTkyMTQyNTExLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMzVmNmQ2NzktM2YzYy00MWRiLWIwNzEtNzYyZjVmNDFkODQ2IiwiY2xpZW50X2lkIjoiZm9vQ2xpZW50SWRQYXNzd29yZCJ9.KvdgGMu7p4dFnmdR6Afs491JMM79-mh-kQ_c1eZudac",
 *  "expires_in":3599,"scope":"read","organization":"user1LXlo","jti":"b28bd5a3-c56a-4716-b243-68610a7b730c"}
 *  
 *  curl -i -H "Accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VyMSIsInNjb3BlIjpbInJlYWQiXSwib3JnYW5pemF0aW9uIjoidXNlcjFMWGxvIiwiZXhwIjoxNTg5NTU0MTExLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYjI4YmQ1YTMtYzU2YS00NzE2LWIyNDMtNjg2MTBhN2I3MzBjIiwiY2xpZW50X2lkIjoiZm9vQ2xpZW50SWRQYXNzd29yZCJ9.jpF8vkUlCVWzTXv8PdxuXCKKxEm5tfxiCpJdObYlboc" -X GET http://localhost:8081/spring-security-oauth-server/user/me
 *  
 *  WEB: http://localhost:8081/spring-security-oauth-server/oauth/authorize?client_id=fooClientIdPassword&response_type=code&scope=read
 *  curl -X POST --user 'fooClientIdPassword:secret' -d "code=BlONxI&grant_type=authorization_code&redirect_uri=http://localhost:8081/login&scope=read" -H "content-type: application/x-www-form-urlencoded" http://localhost:8081/spring-security-oauth-server/oauth/token
 *  curl -i -H "Accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6InVzZXIxIiwic2NvcGUiOlsicmVhZCJdLCJvcmdhbml6YXRpb24iOiJ1c2VyMXllZGciLCJleHAiOjE1ODk1NTcwOTEsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI3Nzc0Mjg3OC1kMmViLTRiYTctYmQyOS04ZTY3MGI3MzhkMzgiLCJjbGllbnRfaWQiOiJmb29DbGllbnRJZFBhc3N3b3JkIn0.-mous229PmWjek9Rd-vl2aHHzJt47zXHOI7hv903VIA" -X GET http://localhost:8081/spring-security-oauth-server/api/user/me
 *  
 *  {"authorities":[{"authority":"ROLE_USER"}],"details":{"remoteAddress":"0:0:0:0:0:0:0:1","sessionId":null,"tokenValue":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXJlc291cmNlIl0sInVzZXJfbmFtZSI6InVzZXIxIiwic2NvcGUiOlsicmVhZCJdLCJvcmdhbml6YXRpb24iOiJ1c2VyMXllZGciLCJleHAiOjE1ODk1NTcwOTEsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI3Nzc0Mjg3OC1kMmViLTRiYTctYmQyOS04ZTY3MGI3MzhkMzgiLCJjbGllbnRfaWQiOiJmb29DbGllbnRJZFBhc3N3b3JkIn0.-mous229PmWjek9Rd-vl2aHHzJt47zXHOI7hv903VIA","tokenType":"Bearer","decodedDetails":null},"authenticated":true,"userAuthentication":{"authorities":[{"authority":"ROLE_USER"}],"details":null,"authenticated":true,"principal":"user1","credentials":"N/A","name":"user1"},"principal":"user1","clientOnly":false,"oauth2Request":{"clientId":"fooClientIdPassword","scope":["read"],"requestParameters":{"client_id":"fooClientIdPassword"},"resourceIds":["oauth2-resource"],"authorities":[],"approved":true,"refre
 */
@SpringBootApplication
public class OAuth2OpenIdServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2OpenIdServerApplication.class, args);
	}

}
