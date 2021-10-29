package my.maven.project.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
//@SequenceGenerator(name = "USERS_SEQUENCE", sequenceName = "S_USERS", allocationSize = 1)
public class Users implements Serializable {
	
	private static final long serialVersionUID = -926775325027208664L;

	@Id
	@Column(name="USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQUENCE")
	private Long userId;

	@Column(name="USERNAME")
	private String username;

	@Column(name="PASSWORD")
	private String password;

}