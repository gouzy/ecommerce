package fr.alma.ejb.ecommerce.frontal;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ClientEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Les commandes du client
	 */
	protected CommandEntity command;

	/**
	 * L'identifiant du client
	 */
	protected Long id;
	
	/**
	 * Le nom du client
	 */
	protected String name;
	
	/**
	 * Le prenom du client
	 */
	private String firstName;
	
	/**
	 * L'adresse du client
	 */
	private String address;
	
	public ClientEntity(){
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId(){return this.id;}
	public void setId(Long id){this.id = id;}
	
	@Basic
	public String getName(){return this.name;}
	public void setName(String name){this.name = name;}
	
	@Basic
	public String getFirstName(){return this.firstName;}
	public void setFirstName(String firstName){this.firstName = firstName;}
	
	@Basic
	public String getAddress(){return this.address;}
	public void setAddress(String address){this.address = address;}

	@OneToOne(mappedBy="client", targetEntity=CommandEntity.class)
	public CommandEntity getCommand(){return this.command;}
	
	public void setCommand(CommandEntity command) {
		this.command = command;
	}

	@Override
	public String toString(){
		return "Id:"+id+"; Name:"+name+"; FirstName:"+firstName;
	}

}
