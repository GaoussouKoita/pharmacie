package ml.workanet.app.pharmacie.utils;

public interface Endpoint {
    //Endpoint sur les controllers
    String PHARMACIE = "/pharmacie";
    String UTILISATEUR = "/utilisateur";
    String STOCK = "/stock";
    String MEDICAMENT = "/medicament";
    String APPROVISION = "/approvision";
    String VENTE = "/vente";
    String TYPE = "/type";


    String COMMANDE_ENDPOINT = "/commande";
    String DEPENSE_ENDPOINT = "/depense";
    String DETTE_CLIENT_ENDPOINT = "/dette-client";
    String CLIENT_FOURNISSEUR_ENDPOINT = "/client-fournisseur";
    String PRODUIT_ENDPOINT = "/produit";
    String RETOUR_PRODUIT_ENDPOINT = "/retour-produit";





    //Endpoint sur les methodes dans les controllers
    String NOUVEAU = "/nouveau";
    String MODIFICATION = "/modifier";
    String SUPPRESSION = "/supprimer";

    String INFO = "/info";
    String DETAILS ="/details" ;
    String DESACTIVER = "/desactiver";
    String ID = "/{id}";
    String AUDIT = "/audit";

    //Endpoint sur les fonctionnalites utiles
    String PASSWORD = "/password";
    String ERROR = "/error";
    String ACCEUIL = "/";
    String LOGIN = "/login";

    String ALL_RESSOURCE="/**/**";

    String CSS="/css/**";
    String JS="/js/**";
    String WEBFONTS="/webfonts/**";


    //Ressources par role
    String[] WHITE_LIST={LOGIN, ERROR, CSS, JS, WEBFONTS};
    String[] UTILISATEUR_ROLE_ACCESS={ALL_RESSOURCE, UTILISATEUR + PASSWORD};
    String[] ADMINSTRATEUR_ROLE_ACCESS={ UTILISATEUR +ALL_RESSOURCE};
    String[] ROOT_ROLE_ACCESS={ PHARMACIE +ALL_RESSOURCE};

}
