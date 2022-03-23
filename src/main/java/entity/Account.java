package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {

	private static final long serialVersionUID = 8709211717537305867L;
    
    @Id
    @Column(name = "User_Name", length = 20, nullable = false)
    private String userName;
    
    @Column(name = "Encryted_Password", length = 128, nullable = false)
    private String encrytedPassword;
    
    @Column(name = "Active", length = 1, nullable = false)
    private boolean active;
    
    @Column(name = "User_Role", length = 20, nullable = false)
    private String userRole;
}
