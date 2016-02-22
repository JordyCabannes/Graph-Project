package core;
import java.util.* ;

import java.io.PrintStream;

import base.Readarg;

public class CovoiturageSimple extends PccStar
{
	private Label[] tabDeLabel_Principal;
	private Label[] tabDeLabel_Origine1;
	private Label[] tabDeLabel_Origine2;
	private Label[] tabDeLabel_Principal_Copie;
	protected BinaryHeap<Label> Tas_Origine1 ; 
	protected BinaryHeap<Label> Tas_Origine2 ; 
	protected BinaryHeap<Label> Tas_Destination ; 
	private ArrayList<Label> NoeudsRencontre;
	private Noeud[] tabInverse;
	private Label[] tabDeLabel_Destination;
	private int origine2;
	
	private ArrayList<Integer> ListeZPieton;
	
	public CovoiturageSimple(Graphe gr, PrintStream sortie, Readarg readarg) 
	{
		super(gr, sortie, readarg) ;
		//tabDeLabel_Principal = new Label[gr.getNbNoeud()];
		tabDeLabel_Origine1 = new Label[gr.getNbNoeud()];
		tabDeLabel_Origine2 = new Label[gr.getNbNoeud()];
		//tabDeLabel_Principal_Copie = new Label[gr.getNbNoeud()];
		tabDeLabel_Destination = new Label[gr.getNbNoeud()];
		NoeudsRencontre = new ArrayList<Label>();
		tabInverse= gr.getTabArcEntrants();
		this.origine2 = readarg.lireInt ("Numero du sommet d'origine 2 ? ") ;
		Tas_Origine1= new BinaryHeap<Label>() ;
		Tas_Origine2= new BinaryHeap<Label>() ;
		Tas_Destination= new BinaryHeap<Label>() ;
	
	
		ArrayList<Integer> ListeZPieton=new ArrayList<Integer>();

	
	}
	
	public void run() throws Pas_de_Synchro
	{

		System.out.println("Run PCC-Star de " + zoneOrigine + " origine 1 :" + origine + " et de origine 2 : " + origine2 + "vers " + zoneDestination + ":" + destination) ;
		//System.out.println(" ////////////////////// TAILLE : " + tabDeLabel_Origine1.length );
		for (int i =0 ; i< tabDeLabel_Origine1.length; i++)
		{
			//tabDeLabel_Principal_Copie[i]= new Label(false, Float.POSITIVE_INFINITY, -1,tabNoeud[i], -1 ) ; //-1 pour l'origine car il n'y en a pas vraiment
			tabDeLabel_Origine1[i]= new Label(false, Float.POSITIVE_INFINITY, -1,tabNoeud[i], origine ) ; 
			tabDeLabel_Origine2[i]= new Label(false, Float.POSITIVE_INFINITY, -1,tabNoeud[i], origine2 ) ;
		}	
		tabDeLabel_Origine1[origine].setCout(0);
		Tas_Origine1.insert(tabDeLabel_Origine1[origine]);
		tabDeLabel_Origine2[origine2].setCout(0);
		Tas_Origine2.insert(tabDeLabel_Origine2[origine2]);
		//tabDeLabel_Principal_Copie[origine].setCout(0);
		//tabDeLabel_Principal_Copie[origine2].setCout(0);
	
	Label x;
	Noeud leNoeudCourant ;
	Chemin leChemin = new Chemin() ;
	Chemin leChemin1 = new Chemin();
	Chemin leChemin2 = new Chemin();
	int nombre_max_d_element_dans_le_tas =1;
	int nombre_de_sommets_marques=0;
	int num_sommet_x;
	float temps_courant;
	
	while (!Tas_Origine1.isEmpty()) 
	{
		x = Tas_Origine1.deleteMin() ;		
		x.setMarquage(true) ;
		
		D.setWidth(3) ;
		//D.setColor(java.awt.Color.blue) ; 
		
		leNoeudCourant= x.getNoeudCourant();
		
		
		for (int j =0 ; j < leNoeudCourant.numDernierElement()+1 ; j++)
		{
			//numero du successeur ;  
			int numer_du_successeur = leNoeudCourant.accederAElement(j).getNumDest() ;
			
			//si le successeur n'est pas marqué 
			if(tabDeLabel_Origine1[numer_du_successeur].getMarquage()==false)
			{
				//num sommet source 
				num_sommet_x = leNoeudCourant.accederAElement(j).getNumSource(); 
				
				//temps de parcour entre source et son successeur 
				temps_courant = leNoeudCourant.temps_min(num_sommet_x, numer_du_successeur) ;	
				if(tabDeLabel_Origine1[numer_du_successeur].getCout()> tabDeLabel_Origine1[num_sommet_x].getCout() + temps_courant)
				{
					//on change le cout du successeur 
					tabDeLabel_Origine1[numer_du_successeur].setCout( tabDeLabel_Origine1[num_sommet_x].getCout() + temps_courant ); 

					//on met à jour le pere du successeur
					tabDeLabel_Origine1[numer_du_successeur].setPere(num_sommet_x) ;
					
					//on met à jour le tas 
					if (Tas_Origine1.array.contains(tabDeLabel_Origine1[numer_du_successeur]))
					{
						Tas_Origine1.update(tabDeLabel_Origine1[numer_du_successeur]) ;
					}
					else 
					{
						Tas_Origine1.insert(tabDeLabel_Origine1[numer_du_successeur]);
						//nombre_max_d_element_dans_le_tas++;
					}
				}
			}
		}	
	}
		
		while (!Tas_Origine2.isEmpty()) 
		{
					
			x = Tas_Origine2.deleteMin() ;		
			x.setMarquage(true) ;
			
			//D.setWidth(3) ;
			//D.setColor(java.awt.Color.blue) ;
			
			leNoeudCourant= x.getNoeudCourant();
			D.setColor(java.awt.Color.red);
			
			
			for (int j =0 ; j < leNoeudCourant.numDernierElement()+1 ; j++)
			{
				//numero du successeur ;  
				int numer_du_successeur = leNoeudCourant.accederAElement(j).getNumDest() ;
				
				//si le successeur n'est pas marqué 
				if(tabDeLabel_Origine2[numer_du_successeur].getMarquage()==false)
				{
					//num sommet source 
					num_sommet_x = leNoeudCourant.accederAElement(j).getNumSource(); 
					//D.drawPoint(tabNoeud[num_sommet_x].getLongitude(), tabNoeud[num_sommet_x].getLatitude(),5);
					//D.drawPoint(leNoeudCourant.getLongitude(), leNoeudCourant.getLatitude(),5);

					//temps de parcour entre source et son successeur 
					

					if(leNoeudCourant.accederAElement(j).getDescripteur().vitesseMax()<130)
					{
						temps_courant = leNoeudCourant.temps_min_pieton(num_sommet_x, numer_du_successeur) ;
						//D.drawPoint(leNoeudCourant.getLongitude(), leNoeudCourant.getLatitude(),5);

						if(tabDeLabel_Origine2[numer_du_successeur].getCout()> tabDeLabel_Origine2[num_sommet_x].getCout() + temps_courant)
						{
							//on change le cout du successeur 
							tabDeLabel_Origine2[numer_du_successeur].setCout( tabDeLabel_Origine2[num_sommet_x].getCout() + temps_courant ); 
							//on met à jour le pere du successeur
							tabDeLabel_Origine2[numer_du_successeur].setPere(num_sommet_x) ;
							//on met à jour le tas 
							if (Tas_Origine2.array.contains(tabDeLabel_Origine2[numer_du_successeur]))
							{
								Tas_Origine2.update(tabDeLabel_Origine2[numer_du_successeur]) ;
							}
							else 
							{
								Tas_Origine2.insert(tabDeLabel_Origine2[numer_du_successeur]);
								//nombre_max_d_element_dans_le_tas++;
							}
						}
					}
				}
				
			
			}
			
		}	
		
		
		
	   //à faire : 	
		//augmenter le champ nb sortie + regarder sommet en commun pour calculer le cout min en sommant couto1->comm +couto2->comm+coutcom->ddest et prendrre le plus petit 
		//verifier que pieton n'empreinte pas des chemins ou il peut mourir  
	
	//fin du while 
	
	
	
	
	/*******************************************************************************************************/
	
	
	
	
	//on fait maintenant un Dijkstra entre la destination et tous les sommets
		
			for (int i =0 ; i< tabDeLabel_Destination.length; i++)
			{
				tabDeLabel_Destination[i]= new Label(false, Float.POSITIVE_INFINITY,-1,tabInverse[i], -1 ) ; //-1 pour l'origine car il n'y en a pas vraiment		
			}
			
			tabDeLabel_Destination[destination].setCout(0);
			Tas_Destination.insert(tabDeLabel_Destination[destination]);
			
			
			float  cout_min=Float.POSITIVE_INFINITY;
			while (!Tas_Destination.isEmpty())
			{
				if (Tas_Destination.size()> nombre_max_d_element_dans_le_tas )
				{
					nombre_max_d_element_dans_le_tas =Tas_Destination.size();
				}
				
				cout_min=Float.POSITIVE_INFINITY;
				x = Tas_Destination.deleteMin() ;		
				x.setMarquage(true) ;
				
				//affichage des sommets marques
				//D.setWidth(3) ;
	    		//D.setColor(java.awt.Color.blue) ; 
	    		
				
				
				nombre_de_sommets_marques++; // on incr�mente le nombre de sommets marqu�s
				
				leNoeudCourant= x.getNoeudCourant();
				
				
				//D.drawPoint(leNoeudCourant.getLongitude(), leNoeudCourant.getLatitude(),5);
				
				//D.setColor(java.awt.Color.black) ; 
				
				
				for (int j =0 ; j < leNoeudCourant.numDernierElement()+1 ; j++)
				{
					//numero du successeur ;  
					int numer_du_successeur= leNoeudCourant.accederAElement(j).getNumSource() ;
					
						//si le successeur n'est pas marqué 
						if(tabDeLabel_Destination[numer_du_successeur].getMarquage()==false )
						{
							//num sommet source 
							num_sommet_x = leNoeudCourant.accederAElement(j).getNumDest(); 
							//D.drawPoint(tabNoeud[num_sommet_x].getLongitude(), tabNoeud[num_sommet_x].getLatitude(),5);
						
							//temps de parcour entre source et son successeur 
							temps_courant = leNoeudCourant.temps_min(numer_du_successeur, num_sommet_x) ;
						
						
							//si le nouveau cout du successeur calculé est plus petit 
							if(tabDeLabel_Destination[numer_du_successeur].getCout()> tabDeLabel_Destination[num_sommet_x].getCout() + temps_courant)
							{
								//on change le cout du successeur 
								tabDeLabel_Destination[numer_du_successeur].setCout(tabDeLabel_Destination[num_sommet_x].getCout() + temps_courant ); 
							
								//on met à jour le pere du successeur
								
									tabDeLabel_Destination[numer_du_successeur].setPere(num_sommet_x) ;
								
							
								//on met à jour le tas 
								if (Tas_Destination.array.contains(tabDeLabel_Destination[numer_du_successeur]))
								{
									Tas_Destination.update(tabDeLabel_Destination[numer_du_successeur]) ;
								}else 
								{
									Tas_Destination.insert(tabDeLabel_Destination[numer_du_successeur]);
									//nombre_max_d_element_dans_le_tas++;
								}
							}
						}
					}
					
				}
			
			
			
			
			
			/******************************************recherche noeud synchro *************************************************************************/
		float min = Float.POSITIVE_INFINITY ;
		int numero_de_sommet_syncro=-1 ;
		System.out.println(" **************** TAILLE 2 : " + NoeudsRencontre.size());
		float cout_syncro_dest=-1f;
		float cout_origine1_syncro=-1f;
		float cout_origine2_syncro=-1f;
		float cout_total=Float.POSITIVE_INFINITY;
		float max=Float.POSITIVE_INFINITY;

		for(int k=0; k<tabDeLabel_Origine1.length; k++ )
		{
			
			
			cout_syncro_dest= tabDeLabel_Destination[k].getCout() ;
			cout_origine1_syncro = tabDeLabel_Origine1[k].getCout();
			cout_origine2_syncro = tabDeLabel_Origine2[k].getCout();
						
			if(Float.compare(cout_origine1_syncro, Float.POSITIVE_INFINITY) != 0 && Float.compare(cout_origine2_syncro, Float.POSITIVE_INFINITY) != 0)
			{
				max=getMax(cout_origine1_syncro, cout_origine2_syncro);
				cout_total = max + cout_syncro_dest;
			}
			
			
			
			if (cout_total < min)
			{
				min = cout_total ;
				numero_de_sommet_syncro=k ;
				
				System.out.println(" NUMERO DE K : " + numero_de_sommet_syncro);
				
			}
			
		}
		if(numero_de_sommet_syncro==-1)
		{
			throw (new Pas_de_Synchro("message")) ;
		}
		float somme_cout = min+tabDeLabel_Origine2[numero_de_sommet_syncro].getCout();
		System.out.println(" NUMERO DE SYNCHRO : " +  numero_de_sommet_syncro);
		System.out.println(" COUT TOTAL: " + somme_cout );
		System.out.println(" COUT SYNCHRO -> DEST : " +  tabDeLabel_Destination[numero_de_sommet_syncro].getCout());
		System.out.println(" COUT ORIGINE1 -> SYNCHRO : " +  tabDeLabel_Origine1[numero_de_sommet_syncro].getCout());
		System.out.println(" COUT ORIGINE2 -> SYNCHRO : " +  tabDeLabel_Origine2[numero_de_sommet_syncro].getCout());
	

		
		/*******************************************************************************************************************/

	
	
		
		/******************************************recherche et affichage des chemins *************************************************************************/
		
		D.setWidth(3) ;
		D.setColor(java.awt.Color.black) ; 
		//Dessin de Origine 1 vers point de Synchro 
		int fin=numero_de_sommet_syncro; 
		while (fin != origine)
		{
			leChemin.FormerListeSuccesseur(fin) ;
			fin = tabDeLabel_Origine1[fin].getPere() ;

		}
		if (fin==origine)
		{
			leChemin.FormerListeSuccesseur(fin);
		}
	    //pour dessiner */
		float debut_longittude ;
	    float debut_latitude ;
	    float fin_longitude ;
 		float fin_latitude ;
 		for(int i = leChemin.getNombreSommet()-1 ; i >0; i--)
		{
			 	debut_longittude = tabNoeud[leChemin.accederElement(i)].getLongitude();
	 			debut_latitude =tabNoeud[leChemin.accederElement(i)].getLatitude();
	 			fin_longitude =tabNoeud[leChemin.accederElement(i-1)].getLongitude();
	 			fin_latitude =tabNoeud[leChemin.accederElement(i-1)].getLatitude();
	 			D.drawLine(debut_longittude ,debut_latitude ,fin_longitude ,fin_latitude) ;
			
		}
 		
 		D.setColor(java.awt.Color.green) ; 
 		//Dessin de Origine 2 vers point de Synchro 
 		fin=numero_de_sommet_syncro; 
 		while (fin != origine2)
 		{
 			leChemin1.FormerListeSuccesseur(fin) ;
 			fin = tabDeLabel_Origine2[fin].getPere() ;

 		}
 		if (fin==origine2)
 		{
 			leChemin1.FormerListeSuccesseur(fin);
 		}	
 		 //pour dessiner 
 		for(int i = leChemin1.getNombreSommet()-1 ; i >0; i--)
 		{
 			
 			debut_longittude = tabNoeud[leChemin1.accederElement(i)].getLongitude();
 			 debut_latitude =tabNoeud[leChemin1.accederElement(i)].getLatitude();
 			 fin_longitude =tabNoeud[leChemin1.accederElement(i-1)].getLongitude();
 			 fin_latitude =tabNoeud[leChemin1.accederElement(i-1)].getLatitude();
 			 D.drawLine(debut_longittude ,debut_latitude ,fin_longitude ,fin_latitude) ;
 		}
 		D.setColor(java.awt.Color.blue) ;	
 		//Dessin du point de Synchro vers destination
 		fin=numero_de_sommet_syncro; 
 		while (fin != destination)
 		{
 			
 			leChemin2.FormerListeSuccesseur(fin) ;
 			fin = tabDeLabel_Destination[fin].getPere() ;
 			
 		}
 		if (fin==numero_de_sommet_syncro)
 		{
 			leChemin2.FormerListeSuccesseur(fin);
 		}	
 		//pour dessiner 
 		for(int i = leChemin2.getNombreSommet()-1 ; i >0; i--)
 		{
 			debut_longittude = tabNoeud[leChemin2.accederElement(i)].getLongitude();
 			 debut_latitude =tabNoeud[leChemin2.accederElement(i)].getLatitude();
 			 fin_longitude =tabNoeud[leChemin2.accederElement(i-1)].getLongitude();
 			 fin_latitude =tabNoeud[leChemin2.accederElement(i-1)].getLatitude();
 			 D.drawLine(debut_longittude ,debut_latitude ,fin_longitude ,fin_latitude) ;
 		}
 		D.setColor(java.awt.Color.red);
		D.drawPoint(tabNoeud[numero_de_sommet_syncro].getLongitude(), tabNoeud[numero_de_sommet_syncro].getLatitude(),10);

	}//accolade fin run 
	
	
	public void ZPieton()
	{
		Label x;
		Noeud leNoeudCourant ;
		Chemin leChemin = new Chemin() ;
		int num_sommet_x;
		boolean arrete_de_marcher=false;
		float temps_courant;
		
		for (int i =0 ; i< tabDeLabel_Origine1.length; i++)
		{
			tabDeLabel_Origine2[i]= new Label(false, Float.POSITIVE_INFINITY, -1,tabNoeud[i], origine2 ) ; 
		}
		
		tabDeLabel_Origine2[origine2].setCout(0);
		Tas_Origine2.insert(tabDeLabel_Origine2[origine2]);
		
		while (!Tas_Origine2.isEmpty() && !arrete_de_marcher) 
		{
			x = Tas_Origine2.deleteMin() ;		
			x.setMarquage(true);
			leNoeudCourant= x.getNoeudCourant();
			num_sommet_x = leNoeudCourant.accederAElement(0).getNumSource(); 

			if(tabDeLabel_Origine2[num_sommet_x].getCout()<=60)
			{
				ListeZPieton.add(num_sommet_x);
				for (int j =0 ; j < leNoeudCourant.numDernierElement()+1 ; j++)
				{
					//numero du successeur ;  
					int numer_du_successeur = leNoeudCourant.accederAElement(j).getNumDest() ;
					
					//si le successeur n'est pas marqué 
					if(tabDeLabel_Origine2[numer_du_successeur].getMarquage()==false)
					{
						//num sommet source 
						
						//D.drawPoint(leNoeudCourant.getLongitude(), leNoeudCourant.getLatitude(),5);
	
						//temps de parcour entre source et son successeur 
						
	
						if(leNoeudCourant.accederAElement(j).getDescripteur().vitesseMax()<130)
						{
							temps_courant = leNoeudCourant.temps_min_pieton(num_sommet_x, numer_du_successeur) ;
							//D.drawPoint(leNoeudCourant.getLongitude(), leNoeudCourant.getLatitude(),5);
	
							if(tabDeLabel_Origine2[numer_du_successeur].getCout()> tabDeLabel_Origine2[num_sommet_x].getCout() + temps_courant)
							{
								//on change le cout du successeur 
								tabDeLabel_Origine2[numer_du_successeur].setCout( tabDeLabel_Origine2[num_sommet_x].getCout() + temps_courant ); 
								//on met à jour le pere du successeur
								tabDeLabel_Origine2[numer_du_successeur].setPere(num_sommet_x) ;
								//on met à jour le tas 
								if (Tas_Origine2.array.contains(tabDeLabel_Origine2[numer_du_successeur]))
								{
									Tas_Origine2.update(tabDeLabel_Origine2[numer_du_successeur]) ;
								}
								else 
								{
									Tas_Origine2.insert(tabDeLabel_Origine2[numer_du_successeur]);
									//nombre_max_d_element_dans_le_tas++;
								}
							}
						}
					}
				}
			}
			else
			{
				arrete_de_marcher=true;
			}
		}
	
	}
	
	public void ParcoursZPieton()
	{
		Label x;
		Noeud leNoeudCourant ;
		Chemin leChemin1 = new Chemin() ;
		int num_sommet_x;
		float temps_courant;
		
		while(!ListeZPieton.isEmpty())
		{
			x = Tas_Origine1.deleteMin() ;		
			x.setMarquage(true) ;
			
			//D.setWidth(3) ;
			//D.setColor(java.awt.Color.blue) ; 
			
			leNoeudCourant= x.getNoeudCourant();
			num_sommet_x = leNoeudCourant.accederAElement(0).getNumSource(); 

			if(ListeZPieton.contains(num_sommet_x) && !ListeZPieton.isEmpty())
			{
				ListeZPieton.remove(num_sommet_x);
			
			
			for (int j =0 ; j < leNoeudCourant.numDernierElement()+1 ; j++)
			{
				//numero du successeur ;  
				int numer_du_successeur = leNoeudCourant.accederAElement(j).getNumDest() ;
				
				//si le successeur n'est pas marqué 
				if(tabDeLabel_Origine1[numer_du_successeur].getMarquage()==false)
				{
					//num sommet source 
					
					//D.drawPoint(leNoeudCourant.getLongitude(), leNoeudCourant.getLatitude(),5);

					//temps de parcour entre source et son successeur 
					temps_courant = leNoeudCourant.temps_min(num_sommet_x, numer_du_successeur) ;	
					if(tabDeLabel_Origine1[numer_du_successeur].getCout()> tabDeLabel_Origine1[num_sommet_x].getCout() + temps_courant)
					{
						//on change le cout du successeur 
						tabDeLabel_Origine1[numer_du_successeur].setCout( tabDeLabel_Origine1[num_sommet_x].getCout() + temps_courant ); 

						//on met à jour le pere du successeur
						tabDeLabel_Origine1[numer_du_successeur].setPere(num_sommet_x) ;

						//on met à jour le tas 
						if (Tas_Origine1.array.contains(tabDeLabel_Origine1[numer_du_successeur]))
						{
							Tas_Origine1.update(tabDeLabel_Origine1[numer_du_successeur]) ;
						}
						else 
						{
							Tas_Origine1.insert(tabDeLabel_Origine1[numer_du_successeur]);
							//nombre_max_d_element_dans_le_tas++;
						}
					}
				}
			}
		}
			
		}
	}

	public float getMax(float f1, float f2)
	{
		float res=0;
		
		if(Float.compare(f1,f2)<0)
		{
			res=f2;
		}
		else if(Float.compare(f1,f2)>0)
		{
			res=f1;
		}
		return res;
	}
	
	
	
} //accolade fin classe 
