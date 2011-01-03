package fr.alma.ecommerce.frontal;

import java.util.*;
import java.util.Map.Entry;

import fr.alma.dto.central.Item;

public class Basket {
	
	/**
	 * Les elements du panier
	 */
	private Map<Item, Integer> products = new HashMap<Item, Integer>();

	public Basket(Item product, Integer quantity){
		this.products.put(product, quantity);
	}
	
	public Basket(Map<Item, Integer> products){
		this.products = products;
	}

	public Basket() {
	}
	
	public Map<Item, Integer> getProducts(){
		return products;
	}

	/**
	 * Ajouter un produit dans le panier
	 * @param produit Le produit
	 * @param quantity La quantite
	 * @return Le panier
	 */
	public Basket addProduct(Item product, Integer quantity){
//		IItem old = null;
//		for(Entry<IItem, Long> entry : products.entrySet()){
//			IItem item = entry.getKey();
//			if(item.getFournisseur().equals(product.getFournisseur())
//					&& item.getMarque().equals(product.getMarque())
//					&& item.getModel().equals(product.getMarque())){
//				old = item;
//				break;
//			}
//		}
//		if(old!=null){
//			Long oldQuantity = products.remove(old);
//			products.put(product, oldQuantity + quantity);
//		}
//		else{
//			products.put(product, quantity);
//		}
		products.put(product, quantity);
		return this;
	}
	
	/**
	 * Supprimer un produit du panier
	 * @param produit Le produit a supprimer
	 * @return Le panier
	 */
	public Basket removeProduct(Item product){
		products.remove(product);
		return this;
	}
	
	public Basket setQuantityForProduct(Integer quantity, Item product){
		products.remove(product);
		products.put(product, quantity);
		return this;
	}
	
	public boolean isEmpty(){
		return products.isEmpty();
	}
	
	public Basket clear(){
		products.clear();
		return this;
	}
	
	private String print(){
		if(products.isEmpty()){
			return "\nLe panier est vide";
		}
		String result = "";
		int i = 1;
		for(Entry<Item, Integer> entry : products.entrySet()){
			Item product = entry.getKey();
			result += "\n\nProduit "+i+" ("+entry.getValue()+" enregistres) :";
			result += "\nMarque : "+product.getMarque();
			result += "\nModele : "+product.getModel();
			result += "\nFournisseur : "+product.getFournisseur();
			++i;
		}
		return result;
	}
	
	@Override
	public String toString(){
		return print();
	}

}
