package fr.alma.ejb.ecommerce.frontal;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class CommandEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Le client de la commande
	 */
	protected ClientEntity client;
	
	/**
	 * L'identifiant de la commande
	 */
	protected Long id;
	
	/**
	 * Le panier representant la commande du client
	 */
	protected Basket basket;
	
	public CommandEntity(){
	}
	
	@OneToOne
	public ClientEntity getClient(){return client;}
	public void setClient(ClientEntity client){this.client = client;}

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId(){return id;}
	public void setId(Long id){this.id = id;}
	
	public Basket getBasket(){return basket;}
	public void setBasket(Basket basket){this.basket = basket;}
	
	
}
