package fr.alma.ecommerce.frontal;

import java.util.*;

import javax.ejb.Remote;

import fr.alma.dto.catalogue.Categorie;
import fr.alma.dto.central.AProduit;
import fr.alma.dto.central.Item;

@Remote
public interface FrontalRemote {

	public Long newCommand(Basket basket, String name, String firstName, String address);
	
	public List<Categorie> getAllCategories();
	
	public List<AProduit> getProductsFromBrand(String brand);
	
	public List<AProduit> getProductsFromCategory(String name);
	
	public List<AProduit> getProductsFromCategoryAndBrand(String category, String brand);

	public List<AProduit> getProductsFromCategoryAndPriceRange(String category, Double low, Double high);
	
	public List<AProduit> getProductsFromCategoryAndBrandAndPriceRange(String category, String brand, Double low, Double high);
	
	public Boolean order(Map<Item, Integer> panier, String nomClient, String adresseClient);
	
	public void runConsole();
}
