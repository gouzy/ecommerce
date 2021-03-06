package fr.alma.ejb.ecommerce.frontal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import fr.alma.dto.catalogue.Categorie;
import fr.alma.dto.central.AProduit;
import fr.alma.dto.central.CProduit;
import fr.alma.dto.central.Item;

public class Console {
	
	private FrontalServer frontal;
	private Basket basket;
	private String nameClient;
	private String firstNameClient;
	private String addressClient;
	private Scanner scanner = new Scanner(System.in);
	
	public void setServer(FrontalServer frontal){
		this.frontal = frontal;
	}
	
	public void run(){
		
		System.out.println(">>>>Nouveau Client<<<<");
		nameClient = getName();
		firstNameClient = getFirstName();
		addressClient = getAddress();
		
		basket = new Basket();
		
		int choice = 0;
		do{
			printMenu();
			choice = scanner.nextInt();
		    switch(choice){
		    case 1 :
		    	printAllCategories();
		    	break;
		    case 2:
		    	findProductFromCategory();
		    	break;
		    case 3 :
		    	printBasket();
		    	break;
		    case 4 :
		    	validateBasket();
		    	break;
		    case 5 :
		    	clearBasket();
		    	break;
		    default : System.out.println("cannot do anything with '"+choice+"'");
		    }
		}while(choice!=4);
	}
	
	private String getFirstName(){
		System.out.print("Votre prenom : ");
		return scanner.nextLine();
	}
	
	private String getName(){
		System.out.print("Votre nom : ");
		return scanner.nextLine();
	}

	private String getAddress(){
		System.out.print("Votre adresse : ");
		return scanner.nextLine();
	}

	private void printMenu(){
		System.out.println("\n\n\t******************************");
		System.out.println("\t[1]lister toutes les categories existantes");
		System.out.println("\t[2]choisir un produit d'une categorie");
		System.out.println("\t[3]voir le panier");
		System.out.println("\t[4]valider le panier");
		System.out.println("\t[5]vider le panier");
		System.out.print("\t[0]se deconnecter : ");
	}
	
	private void printAllCategories(){
		List<Categorie> categories = frontal.getAllCategories();
		for(Categorie category : categories){
			System.out.println(category.getName());
		}
	}
	
	private Map<Integer, Integer> printListAProducts(List<AProduit> list){
		System.out.println("Liste des produits trouves : ");
		int i=3, k=3;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(AProduit p : list){
			int j=-1;
			System.out.println("[marque]"+p.getMarque());
			System.out.print("[model]"+p.getModele());
			List<CProduit> cp = p.getProduitFournis();
			for(CProduit c : cp){
				map.put(++i, ++j);
				System.out.print(++k+" : [fournisseur]"+c.getFournisseur());
				System.out.print("[prix]"+c.getPrix());
				System.out.print("[quantite]"+c.getQuantite());
			}
			System.out.println();
		}
		return map;
	}
	
	private void printBasket(){
		System.out.println(basket);
	}
	
	private void clearBasket(){
		basket = basket.clear();
	}
	
	private void findProductFromCategory(){
		
		//recuperation de toutes les categories
		List<Categorie> categories = frontal.getAllCategories();
		System.out.println("Liste de toutes les categories : ");
		for(int i=0 ; i<categories.size() ; ++i){
			System.out.println("["+i+5+"]"+categories.get(i).getName());
		}
		
		//lecture du numero de la categorie choisie
		int result = scanner.nextInt();
		
		//recuperation de la categorie
		Categorie category = categories.get(result-5);
		List<AProduit> products = frontal.getProductsFromCategory(category.getName());
		
		result = 1;
		String brand="";
		Double low=0.,high=0.;
		Map<Integer, Integer> map = null;
		do{
			//affichage de tous les produits existants
			map = printListAProducts(products);
			System.out.println("Filtrer les produits par : ");
			System.out.println("[1]prix");
			System.out.println("[2]marque");
			System.out.println("[3]prix et marque : ");
			System.out.print("selectionner un produit de la liste precedente : ");
			result = scanner.nextInt();
			
			switch(result){
			case 1 :
				System.out.print("Prix minimum : ");
				low = scanner.nextDouble();
				System.out.print("Prix maximum : ");
				high = scanner.nextDouble();
				products = frontal.getProductsFromCategoryAndPriceRange(
														category.getName()
														,new Double(low)
														,new Double(high)
				);
				System.out.println("Produits de la categorie '"+category.getName()+"' entre "+low+" et "+high+" : ");
				break;
			case 2 :
				System.out.print("Marque : ");
				brand = scanner.nextLine();
				products = frontal.getProductsFromBrand(brand);
				System.out.println("Produits de la categorie '"+category.getName()+"' et de marque '"+brand+"'");
				break;
			case 3 :
				System.out.print("Prix minimum : ");
				low = scanner.nextDouble();
				System.out.print("Prix maximum : ");
				high = scanner.nextDouble();
				System.out.print("Marque : ");
				brand = scanner.nextLine();
				products = frontal.getProductsFromCategoryAndBrandAndPriceRange(
														category.getName()
														,brand
														,low
														,high
				);
				System.out.println("Produits de la categorie '"+category.getName()+"' et de marque '"+brand+"'");				System.out.println("Produits de la categorie '"+category.getName()+"' entre "+low+" et "+high+" : ");
				break;
			default:
				AProduit ap = products.get(map.get(result));
				CProduit cp = ap.getProduitFournis().get(result);
				System.out.print("Combien en voulez vous? ("+cp.getQuantite()+" maximum)");
				int quantity = scanner.nextInt();
				Item item = new Item();
				item.setFournisseur(cp.getFournisseur());
				item.setMarque(ap.getMarque());
				item.setModel(ap.getModele());
				System.out.println("Produit pret a etre enregistre : ");
				System.out.println("Categorie : "+category.getName());
				System.out.println("Marque : "+ap.getMarque());
				System.out.println("Modele : "+ap.getModele());
				System.out.println("Prix : "+cp.getPrix());
				System.out.println("(Fournisseur : "+item.getFournisseur()+")");
				System.out.print("Etes-vous sur? [o/n] : ");
				String response = scanner.nextLine();
				if(response.equals("o")){
					basket.addProduct(item, quantity);
					result = -1;
				}
				else{
					System.out.println("Commande annulee");
				}
			}
		}while(result!=-1);
	}
	
	private void validateBasket(){
		if(!frontal.order(basket.getProducts(), nameClient, addressClient)){
			System.out.println("Desole, impossible de valider la commande: stock insuffisant");
		}
		else{
			frontal.newCommand(basket, nameClient, firstNameClient, addressClient);
			System.out.println("Commande enregistree, merci d'avoir utilise notre service!");
		}
	}
	
	

}
