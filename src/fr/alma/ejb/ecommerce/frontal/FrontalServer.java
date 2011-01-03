package fr.alma.ejb.ecommerce.frontal;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.alma.dto.catalogue.Categorie;
import fr.alma.dto.central.AProduit;
import fr.alma.dto.central.Item;
import fr.alma.interfaces.CentralRemote;

@Stateless
public class FrontalServer implements FrontalRemote {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Le serveur central
	 */
	private CentralRemote central;
	
	/**
	 * La console pour communiquer avec le client
	 */
	private Console console = new Console();
	
	/**
	 * Constructeur qui va chercher le serveur central
	 * @throws NamingException
	 */
	public FrontalServer() throws NamingException{
//		Context context = new InitialContext();
//		central = (CentralRemote) context.lookup("CentralService/remote");
	}
	
	@Override
	public void runConsole(){
		console.setServer(this);
		console.run();
	}
	
	@Override
	public Long newCommand(Basket basket, String name, String firstName, String address){
		CommandEntity command = new CommandEntity();
		ClientEntity client = new ClientEntity();
		client.setName(name);
		client.setFirstName(firstName);
		client.setAddress(address);
		command.setBasket(basket);
		command.setClient(client);
		client.setCommand(command);
		command.setClient(client);
		em.persist(command);
		return client.getId();
	}
	
	@Override
	public Boolean order(Map<Item, Integer> panier, String nomClient, String adresseClient){
		return central.order(panier, nomClient, adresseClient);
	}

	@Override
	public List<Categorie> getAllCategories() {
		return (List<Categorie>) central.findAllCategories();
	}

	@Override
	public List<AProduit> getProductsFromBrand(String brand) {
		return (List<AProduit>) central.findByMarque(brand);
	}

	@Override
	public List<AProduit> getProductsFromCategory(String name) {
		return (List<AProduit>) central.findProduitsByCategorie(name);
	}

	@Override
	public List<AProduit> getProductsFromCategoryAndBrand(String category,
			String brand) {
		return (List<AProduit>) central.findByCategorieAndMarque(category, brand);
	}

	@Override
	public List<AProduit> getProductsFromCategoryAndPriceRange(String category,
			Double low, Double high) {
		return (List<AProduit>) central.findByCategorieAndPriceRange(category, low, high);
	}

	@Override
	public List<AProduit> getProductsFromCategoryAndBrandAndPriceRange(
			String category, String brand, Double low, Double high) {
		return (List<AProduit>) central.findByCategorieAndMarqueAndPriceRange(category, brand, low, high);
	}

	
	
}
