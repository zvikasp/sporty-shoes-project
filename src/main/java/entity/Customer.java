package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 5158552494067149851L;

	@Id
	@Column(nullable = false, unique = true, length = 25)
	private String userId;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 50)
    private String address;
	
	@Column(nullable = false, unique = true, length = 45)
    private String email;
	
	@Column(nullable = false, length = 20)
    private String phone;
}
