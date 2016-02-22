package core ;

import java.util.* ;

public class Noeud
{
	private float longitude;

	private float latitude;
		
	private ArrayList<Arc> arcSortants;
	
	

	public Noeud(float longitude, float latitude)
	{
		this.longitude=longitude;
		this.latitude=latitude;
		arcSortants=new ArrayList<Arc>();
	}
	
	public void ajouterArc(Arc a)
	{
		this.arcSortants.add(a);
	}

	public boolean appartient(Arc a)
	{
		
		return this.arcSortants.contains(a);
	}
	
	public void ajouterSegment(int num_arc, float debut_longitude ,float debut_latitude, float fin_longitude ,float fin_latitude)
	{
		this.arcSortants.get(num_arc).ajouterSegment(debut_longitude,debut_latitude,fin_longitude,fin_latitude);
	}
	
	public int numDernierElement()
	{
		return this.arcSortants.size()-1;
	}

	//retourne le nombre de successeurs d'un noeud
	public float nb_Successeurs()
	{
		ArrayList<Integer> successeurs = new ArrayList<Integer>();
		int nb_succ=0;
		
		for (int i=0; i<this.arcSortants.size(); i++)
		{
			if(successeurs.contains(this.arcSortants.get(i).getNumDest())==false)
			{
				successeurs.add(this.arcSortants.get(i).getNumDest());
				nb_succ++;
			}
		}
		
		
		return (float) nb_succ;
		
	}
	
	public float getLongitude()
	{
		return longitude ;
	}
	
	public float getLatitude()
	{
		return latitude;
	}
	
	public Arc accederAElement(int num_element)
	{
		return arcSortants.get(num_element);
	}
	
	//recupere la vitesse minimale entre un noeud source et un noeud de destination 
	public  int recupererVitesse(int NumSommetSource,int NumSOmmetDestination)
	{
		int vit = 0;
		int vitmax=0 ;
		int dist ;
		float t;
		float tmin =1000000;
		for(int i =0 ; i<arcSortants.size();i++)
		{
			if(arcSortants.get(i).getNumSource()==NumSommetSource && arcSortants.get(i).getNumDest() ==NumSOmmetDestination)
			{
				vit= arcSortants.get(i).getDescripteur().vitesseMax();
				dist=arcSortants.get(i).getLongueur() ; 
				t= (float)dist/vit ;
				if( t<tmin)
				{
					vitmax = vit ; 
					tmin = t ; 
					
				}
			}
		}
		
		//if(vitmax== 0)System.exit(1);
		return vitmax ;
	}
	
	//permet de recuperer les segments d'un arc pour déssiner  
	
	
	
	//recupere la logueur minimale entre un noeud source et un noeud de destination 
	public  int recupererlongueur(int NumSommetSource,int NumSOmmetDestination)
	{
		int vit = 0;
		int vitmax=0 ;
		int dist ;
		int  distmin =1000000;
		float t ; 
		float tmin =1000000;
		for(int i =0 ; i<arcSortants.size();i++)
		{
			if(arcSortants.get(i).getNumSource()==NumSommetSource && arcSortants.get(i).getNumDest() ==NumSOmmetDestination)
			{
				vit= arcSortants.get(i).getDescripteur().vitesseMax();
				dist=arcSortants.get(i).getLongueur() ; 
				t= (float)dist/vit ;
				if( t<tmin)
				{
					vitmax = vit ; 
					distmin = dist ;
					tmin= t; 
					
				}
			}
		}
		
		//if(tmin==1000000)//System.exit(1);
		return distmin ;
	}
	
	public ArrayList<Segment> recupererLeSegment(int sommentSource , int SommetDestination)
	{ 
		//se base sur la recherche de la vitesse max pour rechercher les bons segments à dessiner ;
		ArrayList<Segment> leSegment =new  ArrayList <Segment>() ; 
		ArrayList<Segment> lesBonSegments =new  ArrayList <Segment>() ; 
		
		int vit = 0;
		int vitmax=0;
		
		for(int i =0 ; i<arcSortants.size();i++)
		{
			if(arcSortants.get(i).getNumSource()==sommentSource && arcSortants.get(i).getNumDest() ==SommetDestination)
			{
				leSegment =arcSortants.get(i).getTableauDeSegment() ;
				vit= arcSortants.get(i).getDescripteur().vitesseMax();
				if(vit>vitmax)
				{
					lesBonSegments = leSegment ;
					vitmax =vit ;	
				}
			}
		}
		//debug 
		if(lesBonSegments.size()==0){System.out.println("pas de segment entre : "+sommentSource + " et " + SommetDestination) ; System.exit(1) ;}
		//if (vitmax==0) System.exit(1);
		return lesBonSegments;
	}
	
	//renvoie l'arc qui a le temps de parcours le plus petit 
	public float temps_min(int sommentSource , int SommetDestination)
	{
		int  numerateur = this.recupererlongueur(sommentSource,SommetDestination);
		int  denominateur = this.recupererVitesse(sommentSource,SommetDestination);
		return ((float)numerateur/(float)denominateur)*60/1000;
	}

	//renvoie le temps de parcours d'un arc par une pieton à sa vitesse 
	public float temps_min_pieton(int sommentSource , int SommetDestination)
	{
		int  numerateur = this.recupererlongueur(sommentSource,SommetDestination);
		int  denominateur = 4;
		return ((float)numerateur/(float)denominateur)*60/1000;
	}
	
	//renvoie l'arc qui a la distance  de parcours le plus petit 
	public float distanceMin(int NumSommetSource,int NumSOmmetDestination)
	{
		int vit = 0;
		int vitmax=0 ;
		int dist ;
		int  distmin = Integer.MAX_VALUE;
		
		for(int i =0 ; i<arcSortants.size();i++)
		{
			if(arcSortants.get(i).getNumSource()==NumSommetSource && arcSortants.get(i).getNumDest() ==NumSOmmetDestination)
			{
				dist=arcSortants.get(i).getLongueur() ; 
				if( dist<distmin)
				{
					
					distmin = dist ; 
					
				}
			}
		
		}
		if( distmin==Integer.MAX_VALUE)
		{
			System.out.println("Distance min fausse");
			System.exit(1);
		}
		return (float)distmin;
		
	}
	
	
}