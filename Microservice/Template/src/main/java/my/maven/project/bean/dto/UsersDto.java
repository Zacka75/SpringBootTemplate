package my.maven.project.bean.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDto implements Serializable {
	
	private static final long serialVersionUID = -926775325027208664L;

//	@NotNull(message = "error.msg.518")
//	@NotNull(message = "Il campo userId è obbligatorio.")
	private Long userId;
	private String username;
	private String password;

}