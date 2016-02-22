package core ;

/**
 *   Classe representant un graphe.
 *   A vous de completer selon vos choix de conception.
 */

import java.io.* ;
import java.util.ArrayList;
import java.util.FormatterClosedException;

import base.* ;

public class Graphe {

    // Nom de la carte utilisee pour construire ce graphe
    private final String nomCarte ;

    // Fenetre graphique
    private final Dessin dessin ;

    // Version du format MAP utilise'.
    private static final int version_map = 4 ;
    private static final int magic_number_map = 0xbacaff ;

    // Version du format PATH.
    private static final int version_path = 1 ;
    private static final int magic_number_path = 0xdecafe ;

    // Identifiant de la carte
    private int idcarte ;

    // Numero de zone de la carte
    private int numzone ;

    /*
     * Ces attributs constituent une structure ad-hoc pour stocker les informations du graphe.
     * Vous devez modifier et ameliorer ce choix de conception simpliste.
     */
    private float[] longitudes ;
    private float[] latitudes ;
    private Descripteur[] descripteurs ;
   
	
	// code perso : Tableau qui va contenir tous les sommets et donc tous les successeurs de chaque sommet	
	private Noeud[] tabNoeud;
	 private Noeud[] tabArcEntrants;
	//code perso , on recupere le chemin entre deux noeuds 
	private Chemin leChemin ; 
	
	private float distance_chemin ; 
	private float temps_chemin ;
    
    // Deux malheureux getters.
    public Dessin getDessin() { return dessin ; }
    public int getZone() { return numzone ; }

    // Le constructeur cree le graphe en lisant les donnees depuis le DataInputStream
    public Graphe (String nomCarte, DataInputStream dis, Dessin dessin) {

	this.nomCarte = nomCarte ;
	this.dessin = dessin ;
	Utils.calibrer(nomCarte, dessin) ;
	
	// Lecture du fichier MAP. 
	// Voir le fichier "FORMAT" pour le detail du format binaire.
	try {

	    // Nombre d'aretes
	    int edges = 0 ; 
	    

	    // Verification du magic number et de la version du format du fichier .map
	    int magic = dis.readInt () ;
	    int version = dis.readInt () ;
	    Utils.checkVersion(magic, magic_number_map, version, version_map, nomCarte, ".map") ;

	    // Lecture de l'identifiant de carte et du numero de zone, 
	    this.idcarte = dis.readInt () ;
	    this.numzone = dis.readInt () ;

	    // Lecture du nombre de descripteurs, nombre de noeuds.
	    int nb_descripteurs = dis.readInt () ;
	    int nb_nodes = dis.readInt () ;

		// initialisation du tableau avec le bon nombre de sommets		
		tabNoeud=new Noeud[nb_nodes];
		tabArcEntrants=new Noeud[nb_nodes];
	    // Nombre de successeurs enregistrÃ©s dans le fichier.
	    int[] nsuccesseurs_a_lire = new int[nb_nodes] ;
	    
	    // En fonction de vos choix de conception, vous devrez certainement adapter la suite.
	    this.longitudes = new float[nb_nodes] ;
	    this.latitudes = new float[nb_nodes] ;
	    this.descripteurs = new Descripteur[nb_descripteurs] ;

	    // Lecture des noeuds
	    for (int num_node = 0 ; num_node < nb_nodes ; num_node++) {
		// Lecture du noeud numero num_node
		longitudes[num_node] = ((float)dis.readInt ()) / 1E6f ;
		latitudes[num_node] = ((float)dis.readInt ()) / 1E6f ;
		nsuccesseurs_a_lire[num_node] = dis.readUnsignedByte() ;
		
		//code perso , on affecte les longitudes et les latitudes de chaque noeud 
		tabNoeud[num_node]=new Noeud(longitudes[num_node] , latitudes[num_node] ) ;
		tabArcEntrants[num_node]=new Noeud(longitudes[num_node] , latitudes[num_node] ) ;
	    }
	    
	    Utils.checkByte(255, dis) ;
	    
	    // Lecture des descripteurs
	    for (int num_descr = 0 ; num_descr < nb_descripteurs ; num_descr++) {
		// Lecture du descripteur numero num_descr
		descripteurs[num_descr] = new Descripteur(dis) ;

		// On affiche quelques descripteurs parmi tous.
		if (0 == num_descr % (1 + nb_descripteurs / 400))
		    System.out.println("Descripteur " + num_descr + " = " + descripteurs[num_descr]) ;
	    }
	    
	    Utils.checkByte(254, dis) ;
	    
	    //code perso 
	    
	    
	    // Lecture des successeurs
	    for (int num_node = 0 ; num_node < nb_nodes ; num_node++) {
		// Lecture de tous les successeurs du noeud num_node
		for (int num_succ = 0 ; num_succ < nsuccesseurs_a_lire[num_node] ; num_succ++) {
		    // zone du successeur
		    int succ_zone = dis.readUnsignedByte() ;

		    // numero de noeud du successeur
		    int dest_node = Utils.read24bits(dis) ;

		    // descripteur de l'arete
		    int descr_num = Utils.read24bits(dis) ;

		    // longueur de l'arete en metres
		    int longueur  = dis.readUnsignedShort() ;

		    // Nombre de segments constituant l'arete
		    int nb_segm   = dis.readUnsignedShort() ;

		    edges++ ;
		    
		    Couleur.set(dessin, descripteurs[descr_num].getType()) ;

		    float current_long = longitudes[num_node] ;
		    float current_lat  = latitudes[num_node] ;
		    
		    
		    
		    //code perso , On ajoute les arcs et donc les succesuers au Noeud courant
		    Arc a1 =new Arc(num_node,dest_node,succ_zone,longueur,descripteurs[descr_num]);
		    
		    //arc du double sens 
		    Arc a = new Arc(dest_node, num_node,succ_zone,longueur,descripteurs[descr_num]);
		    
		   
		    tabNoeud[num_node].ajouterArc(a1);
		    tabArcEntrants[dest_node].ajouterArc(a1);
		   
		    //dessin.setColor(java.awt.Color.blue) ; //code perso , fixe la couleur 
		    //coode perso 	
		    
		    // Chaque segment est dessine'
		    for (int i = 0 ; i < nb_segm ; i++) {
			float delta_lon = (dis.readShort()) / 2.0E5f ;
			float delta_lat = (dis.readShort()) / 2.0E5f ;
			
			//on ajoute les segments
			
				a.ajouterSegment(current_long+delta_lon,current_lat+delta_lat, current_long  , current_lat );		
		    	a1.ajouterSegment(current_long, current_lat, current_long + delta_lon  , current_lat+ delta_lat );					
			
			//tabNoeud[num_node].ajouterSegment(tabNoeud[num_node].numDernierElement(),current_long ,current_lat ,(current_long + delta_lon),( current_lat+ delta_lat) );
			dessin.drawLine(current_long, current_lat, (current_long + delta_lon), (current_lat + delta_lat)) ;
			current_long += delta_lon ;
			current_lat  += delta_lat ;
		    }
		    
		    
		    // code pero , Si double sens on rajoute l'arc à la liste des successeurs du successeur du noeud courant
		    if(descripteurs[descr_num].isSensUnique()==false)
		    {
		    	//Noeud N= tabNoeud[num_node] ;
		    	//int indice =0 ;
		    	
		    	tabNoeud[dest_node].ajouterArc(a);
		    	tabArcEntrants[num_node].ajouterArc(a);
		    	
		    		/*for(int i=0;i<a1.getNombreDeSegment() ; i++)
		    		{    			
		    			tabNoeud[dest_node].ajouterSegment(tabNoeud[dest_node].numDernierElement(), a1.recupSegment(i,2),a1.recupSegment(i,3),a1.recupSegment(i,0),a1.recupSegment(i,1));
		    		}*/
		    }
		    
		  
		    
		    // Le dernier trait rejoint le sommet destination.
		    // On le dessine si le noeud destination est dans la zone du graphe courant.
		    if (succ_zone == numzone ) {
			dessin.drawLine(current_long, current_lat, tabNoeud[dest_node].getLongitude(),tabNoeud[dest_node].getLatitude()) ;
			//on ajoute les segments droits entre 2 noeuds
			tabNoeud[num_node].ajouterSegment(tabNoeud[num_node].numDernierElement(), current_long,current_lat,tabNoeud[dest_node].getLongitude(), tabNoeud[dest_node].getLatitude());
			a.ajouterSegment(tabNoeud[num_node].getLongitude(), tabNoeud[num_node].getLatitude(),current_long,current_lat);
		    
		    //tabArcEntrants[num_node].ajouterSegment(tabArcEntrants[num_node].numDernierElement(), current_long,current_lat,tabArcEntrants[dest_node].getLongitude(), tabArcEntrants[dest_node].getLatitude());
		    //a1.ajouterSegment(tabArcEntrants[num_node].getLongitude(), tabArcEntrants[num_node].getLatitude(),current_long,current_lat);
		    }
		}
	    }
	    
	    Utils.checkByte(253, dis) ;
 
	    //debug 
		/*System.out.println("***************************************************" + tabNoeud[248].numDernierElement() ) ;
		for (int i =0;i< tabNoeud[456].numDernierElement()+1;i++ )
		{
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++ dest : " + tabNoeud[456].accederAElement(i).getNumDest()+ "     nb segments   "+tabNoeud[456].accederAElement(i).getNombreDeSegment() );
		}//fin debug*/

	    System.out.println("Fichier lu : " + nb_nodes + " sommets, " + edges + " aretes, " 
			       + nb_descripteurs + " descripteurs.") ;

	} catch (IOException e) {
	    e.printStackTrace() ;
	    System.exit(1) ;
	}

    }

    // Rayon de la terre en metres
    private static final double rayon_terre = 6378137.0 ;

    /**
     *  Calcule de la distance orthodromique - plus court chemin entre deux points à la surface d'une sphère
     *  @param long1 longitude du premier point.
     *  @param lat1 latitude du premier point.
     *  @param long2 longitude du second point.
     *  @param lat2 latitude du second point.
     *  @return la distance entre les deux points en metres.
     *  Methode Ã©crite par Thomas Thiebaud, mai 2013
     */
    public static double distance(double long1, double lat1, double long2, double lat2) {
        double sinLat = Math.sin(Math.toRadians(lat1))*Math.sin(Math.toRadians(lat2));
        double cosLat = Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2));
        double cosLong = Math.cos(Math.toRadians(long2-long1));
        return rayon_terre*Math.acos(sinLat+cosLat*cosLong);
    }

    /**
     *  Attend un clic sur la carte et affiche le numero de sommet le plus proche du clic.
     *  A n'utiliser que pour faire du debug ou des tests ponctuels.
     *  Ne pas utiliser automatiquement a chaque invocation des algorithmes.
     */
    public void situerClick() {

	System.out.println("Allez-y, cliquez donc.") ;
	
	if (dessin.waitClick()) {
	    float lon = dessin.getClickLon() ;
	    float lat = dessin.getClickLat() ;
	    
	    System.out.println("Clic aux coordonnees lon = " + lon + "  lat = " + lat) ;

	    // On cherche le noeud le plus proche. O(n)
	    float minDist = Float.MAX_VALUE ;
	    int   noeud   = 0 ;
	    
	    for (int num_node = 0 ; num_node < longitudes.length ; num_node++) {
		float londiff = (longitudes[num_node] - lon) ;
		float latdiff = (latitudes[num_node] - lat) ;
		float dist = londiff*londiff + latdiff*latdiff ;
		if (dist < minDist) {
		    noeud = num_node ;
		    minDist = dist ;
		}
	    }

	    System.out.println("Noeud le plus proche : " + noeud) ;
	    System.out.println() ;
	    dessin.setColor(java.awt.Color.red) ;
	    dessin.drawPoint(longitudes[noeud], latitudes[noeud], 5) ;
	}
    }

    /**
     *  Charge un chemin depuis un fichier .path (voir le fichier FORMAT_PATH qui decrit le format)
     *  Verifie que le chemin est empruntable et calcule le temps de trajet.
     */
    public void verifierChemin(DataInputStream dis, String nom_chemin) {
	
	try {
		//notre chemin 
		leChemin =new Chemin() ;
	    
	    // Verification du magic number et de la version du format du fichier .path
	    int magic = dis.readInt () ;
	    int version = dis.readInt () ;
	    Utils.checkVersion(magic, magic_number_path, version, version_path, nom_chemin, ".path") ;

	    // Lecture de l'identifiant de carte
	    int path_carte = dis.readInt () ;

	    if (path_carte != this.idcarte) {
		System.out.println("Le chemin du fichier " + nom_chemin + " n'appartient pas a la carte actuellement chargee." ) ;
		System.exit(1) ;
	    }

	    int nb_noeuds = dis.readInt () ;

	    // Origine du chemin
	    int first_zone = dis.readUnsignedByte() ;
	    int first_node = Utils.read24bits(dis) ;

	    // Destination du chemin
	    int last_zone  = dis.readUnsignedByte() ;
	    int last_node = Utils.read24bits(dis) ;

	    System.out.println("Chemin de " + first_zone + ":" + first_node + " vers " + last_zone + ":" + last_node) ;
	    int current_zone = 0 ;
	    int current_node = 0 ;

	    // Tous les noeuds du chemin
	    for (int i = 0 ; i < nb_noeuds ; i++) {
		current_zone = dis.readUnsignedByte() ;
		current_node = Utils.read24bits(dis) ;
		leChemin.FormerListeSuccesseur(current_node) ; //code perso ,  on forme la liste des sommets du chemin 
		System.out.println(" --> " + current_zone + ":" + current_node) ;
	    }

	    if ((current_zone != last_zone) || (current_node != last_node)) {
		    System.out.println("Le chemin " + nom_chemin + " ne termine pas sur le bon noeud.") ;
		    System.exit(1) ;
		}  
	    
	   /* System.out.println("liste des element du chemin " ) ;
	    for( int i=0 ; i<leChemin.getNombreSommet() ;i++)
	    {
	    	 System.out.println("-> -> " +leChemin.accederElement(i) );
	    }*/
	    

	} catch (IOException e) {
	    e.printStackTrace() ;
	    System.exit(1) ;
	}

    }
    
    
    public float calculerDistance()
    {
    	distance_chemin = 0 ;
    	
    	/*for(int i = 0 ; i <leChemin.getNombreSommet()-1; i++)
    	{
    		System.out.println(leChemin.accederElement(i));
    	}*/
    	
    	for(int i = 0 ; i <leChemin.getNombreSommet()-1; i++)
    	{
    		distance_chemin+= tabNoeud[leChemin.accederElement(i)].recupererlongueur(leChemin.accederElement(i),leChemin.accederElement(i+1));
    		//resultat = tabNoeud[leChemin.accederElement(i)].recupererVitesse(i,i+1);
    	}
    	return distance_chemin ;
    }
    
    public float calculertemps()
    {
    	temps_chemin= 0 ;
    	int numerateur ;
    	int denominateur ;
    	
    	ArrayList<Segment>tabDeSegment = new ArrayList <Segment>() ; // tableau de segment pour dessiner le chemin 
    	
    	float debut_longittude;
    	float debut_latitude;
    	float fin_longitude;
    	float fin_latitude ;
    	
    	for(int i = 0 ; i <leChemin.getNombreSommet()-1; i++)
    	{
    		
    		//on recuper les segments entre deux sommets
    		//tabDeSegment = tabNoeud[leChemin.accederElement(i)].recupererLeSegment(leChemin.accederElement(i), leChemin.accederElement(i+1));
    		
    		//on dessine nos segments entre deux noeuds 
    		//on fixe la couleur et la taille du crayon 
    		dessin.setWidth(3) ;
    		dessin.setColor(java.awt.Color.green) ; 
    		//for (int jt =0 ; jt< tabDeSegment.size() ; jt++)
    		//{
    			debut_longittude = tabNoeud[leChemin.accederElement(i)].getLongitude();//tabDeSegment.get(jt).getdebut_longitude() ;
    			debut_latitude =tabNoeud[leChemin.accederElement(i)].getLatitude();//tabDeSegment.get(jt).getdebut_latitude() ;
    			fin_longitude =tabNoeud[leChemin.accederElement(i+1)].getLongitude();
    			fin_latitude =tabNoeud[leChemin.accederElement(i+1)].getLatitude();
    			dessin.drawLine(debut_longittude ,debut_latitude ,fin_longitude ,fin_latitude) ;
    			
    		//} 
    			
    			
    		//numerateur = tabNoeud[leChemin.accederElement(i)].recupererlongueur(leChemin.accederElement(i),leChemin.accederElement(i+1));
    		//denominateur = tabNoeud[leChemin.accederElement(i)].recupererVitesse(leChemin.accederElement(i),leChemin.accederElement(i+1));
    		float temps_courant= tabNoeud[leChemin.accederElement(i)].temps_min(leChemin.accederElement(i),leChemin.accederElement(i+1));
    		temps_chemin+=temps_courant ;
    	//	System.out.println("----------------------------------------------------------temps_courant " + temps_courant);
    		//temps_chemin+= ((float)numerateur/(float)denominateur)*60/1000;
    		//System.out.println("temps:" +temps_chemin);
    		
    		//on déssine un point sur le sommet courant
    		dessin.setColor(java.awt.Color.blue) ; 
    		dessin.drawPoint(tabNoeud[leChemin.accederElement(i)].getLongitude(), tabNoeud[leChemin.accederElement(i)].getLatitude(), 5) ;
    	}
    	
    	//on dession un point sur le dernier sommet 
    	dessin.drawPoint(tabNoeud[leChemin.accederElement(leChemin.getNombreSommet()-1)].getLongitude(), tabNoeud[leChemin.accederElement(leChemin.getNombreSommet()-1)].getLatitude(), 5) ;
    	
    	return temps_chemin ;
    }
    
    
    
    public float calculerNombreMoyen()
    {
    	float res=0;
    	
    	for (int num_node = 0 ; num_node < tabNoeud.length ; num_node++) {
    		if(num_node==248)
    		{
    			System.out.println("++++++++++++++++------------------"+tabNoeud[num_node].nb_Successeurs() );
    		}
    		res += tabNoeud[num_node].nb_Successeurs();
    	}
    	
    	return res/tabNoeud.length;
    }
    
    public int getNbNoeud()
    {
    	return tabNoeud.length;
    }
    
    public Noeud[] RenvoyertabNoeud( )
    {
    	return tabNoeud ;
    }
    
    public Noeud[] getTabArcEntrants()
    {
    	return tabArcEntrants;
    }
    
    public int[] renvoyerNombre_succeseur_parNoeud()
    {
    	int letab[]=new int[tabNoeud.length];
    	for (int num_node = 0 ; num_node < tabNoeud.length ; num_node++) 
    	{
    		letab[num_node] = tabNoeud[num_node].numDernierElement()+1 ;
    	}
    	return letab ;
    }
    
    public int getNumZone()
    {
    	return this.numzone;
    }
    
    /*public Noeud[] ArcEntrants()
	{
		
		for(int i=0; i<tabNoeud.length; i++)
		{
			for(int j=0; j<tabNoeud[i].numDernierElement()+1; j++)
			{
				int num_node=tabNoeud[i].accederAElement(j).getNumDest();
				int dest_node=tabNoeud[i].accederAElement(j).getNumSource();
				int succ_zone=tabNoeud[i].accederAElement(j).getNumzone();
				Descripteur descr=tabNoeud[i].accederAElement(j).getDescripteur();
				int longueur=tabNoeud[i].accederAElement(j).getLongueur();
				
				System.out.println( "NUMERO DU NOEUD SOURCE : " + num_node );
				System.out.println( "NUMERO DU NOEUD DESTINATION : " + dest_node );
				System.out.println( "NUMERO ZONE : " + succ_zone );
				System.out.println( "LONGUEUR: " + longueur );
				System.out.println("Descripteur : " + descr);
				
				Arc a2=new Arc(num_node,dest_node,succ_zone,longueur, descr);
				tabArcEntrants[0/*tabNoeud[i].accederAElement(j).getNumDest()].ajouterArc(a2);
				System.out.println("INDICE DU TABLEAU ARCS ENTRANTS : " +  tabNoeud[i].accederAElement(j).getNumDest());
			}
		}
		return tabArcEntrants;
	}*/
}