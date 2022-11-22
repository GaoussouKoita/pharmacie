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
    String SALAIRE = "/salaire";
    String DEPENSE = "/depense";
    String STATISTIQUE = "/statistique";

    //Endpoint sur les methodes dans les controllers
    String NOUVEAU = "/nouveau";
    String MODIFICATION = "/modifier";
    String SUPPRESSION = "/supprimer";
    String INFO = "/info";
    String DETAILS ="/details" ;
    String DESACTIVER = "/desactiver";
    String ID = "/{id}";

    //Endpoint sur les fonctionnalites utiles
    String CHART_VENTE = "/chart-vente";
    String CHART_VENTE_DATES = "/chart-ventes";
    String CHART_VENTE_UTILISATEUR = "/chart-vente-utilisateur";
    String CHART_VENTE_UTILISATEURS = "/chart-vente-utilisateurs";
    String PASSWORD = "/password";
    String ERROR = "/error";
    String ACCEUIL = "/";
    String LOGIN = "/login";


    String ALL_RESSOURCE="**/**";
    String CSS="/css/**";
    String JS="/js/**";
    String WEBFONTS="/webfonts/**";
    String IMAGE="/image/**";


    //Ressources par role
    String[] WHITE_LIST={LOGIN, ERROR, CSS, JS, WEBFONTS, IMAGE};

    String[] EMPLOYE_ROLE_ACCESS ={STOCK+ALL_RESSOURCE, MEDICAMENT+ALL_RESSOURCE,
            APPROVISION,APPROVISION+NOUVEAU,APPROVISION+DETAILS+ALL_RESSOURCE,
            VENTE, VENTE+NOUVEAU, VENTE+DETAILS+ALL_RESSOURCE,
            TYPE+ALL_RESSOURCE,
            DEPENSE+NOUVEAU,UTILISATEUR + PASSWORD, STATISTIQUE+CHART_VENTE_UTILISATEUR};

    String[] ADMINSTRATEUR_ROLE_ACCESS={ SALAIRE+ALL_RESSOURCE, UTILISATEUR +ALL_RESSOURCE,
                    DEPENSE, DEPENSE+DETAILS+ALL_RESSOURCE,VENTE+INFO+ALL_RESSOURCE,
                    VENTE+SUPPRESSION+ALL_RESSOURCE,APPROVISION+SUPPRESSION+ALL_RESSOURCE,
                    STATISTIQUE+ALL_RESSOURCE};

    String[] ROOT_ROLE_ACCESS={ PHARMACIE +ALL_RESSOURCE};

}
