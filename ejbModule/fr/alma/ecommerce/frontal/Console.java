package fr.alma.ecommerce.frontal;

import java.util.List;
import java.util.Scanner;

import fr.alma.dto.catalogue.Categorie;
import fr.alma.dto.central.AProduit;
import fr.alma.dto.central.CProduit;

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
//		frontal.newClient(getName(), getFirstName());
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
		}while(choice!=0);
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
	
	private void printListAProducts(List<AProduit> list){
		System.out.println("Liste des produits trouves : ");
		for(AProduit p : list){
			System.out.println("[marque]"+p.getMarque());
			System.out.print("[model]"+p.getModele());
			List<CProduit> cp = p.getProduitFournis();
			for(CProduit c : cp){
				System.out.print("[fournisseur]"+c.getFournisseur());
				System.out.print("[prix]"+c.getPrix());
				System.out.print("[quantite]"+c.getQuantite());
			}
			System.out.println();
		}
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
			System.out.println("["+i+1+"]"+categories.get(i).getName());
		}
		
		//lecture du numero de la categorie choisie
		int result = scanner.nextInt();
		
		//recuperation de la categorie
		Categorie category = categories.get(result-1);
		List<AProduit> products = frontal.getProductsFromCategory(category.getName());
		
		result = 0;
		boolean price=false
			,brand = false;
		do{
			System.out.println("Filtrer les resultats par : ");
			System.out.println("[1]prix");
			System.out.println("[2]marque");
			System.out.print("[3]prix et marque : ");
			if(price || brand){
				System.out.print("[4]selectionner un produit dans la liste precedente : ");
			}
			System.out.println("[0]fin du filtre");
			result = scanner.nextInt();
			
			switch(result){
			case 1 :
				System.out.println("Prix minimum : ");
				int low = scanner.nextInt();
				System.out.println("Prix maximum : ");
				int high = scanner.nextInt();
				products = frontal.getProductsFromCategoryAndPriceRange(category.getName()
																			,new Double(low)
																			,new Double(high)
				);
				price = true;
				System.out.println("Produits trouves pour la categorie '"+category.getName()+"' : ");
				printListAProducts(products);
				break;
			case 2 :
				break;
			case 3 :
				System.out.println(basket);
				break;
			}
		}while(result!=0);
	}
	
	private void validateBasket(){
		if(!frontal.order(basket.getProducts(), nameClient, addressClient)){
			System.out.println("Impossible de valider la commande: stock insuffisant");
		}
		else{
			frontal.newCommand(basket, nameClient, firstNameClient, addressClient);
			System.out.println("Commande enregistree !");
		}
	}
	
	

}
