package core ;


import java.util.* ;

import base.Descripteur;

public class Arc 
{

	private int num_sommet_source ;

	private int num_sommet_destination ;

	private int num_zone_successeur ;

	private Descripteur descripteur_de_l_arete ;

	private int longeur_route ;

	private ArrayList <Segment> tab_delta_Segment;
	
	public Arc(int num_sommet_source,int num_sommet_destination,int num_zone_successeur,int longeur_route,Descripteur descripteur_de_l_arete)
	{
		this.num_sommet_source=num_sommet_source;
		this.num_sommet_destination= num_sommet_destination ;
		this.num_zone_successeur =num_zone_successeur ;
		this.longeur_route =longeur_route ;
		this.descripteur_de_l_arete =descripteur_de_l_arete ;
		tab_delta_Segment = new  ArrayList <Segment>() ;
	
	}
	
	
	
	public void ajouterSegment(float debut_longitude ,float debut_latitude, float fin_longitude ,float fin_latitude)
	{
		this.tab_delta_Segment.add(new Segment(debut_longitude,debut_latitude,fin_longitude,fin_latitude) ) ;
	}
	
	
	public float recupSegment(int num_segm,int numero_valeur_a_recuperer)
	{
		if (numero_valeur_a_recuperer==0)
		{
			return this.tab_delta_Segment.get(num_segm).getdebut_longitude() ;
		}else if (numero_valeur_a_recuperer==1)
		{
			return this.tab_delta_Segment.get(num_segm).getdebut_latitude() ;
		}else if (numero_valeur_a_recuperer==2)
		{
			return this.tab_delta_Segment.get(num_segm).getfin_longitude() ;
		}else
		{
			return this.tab_delta_Segment.get(num_segm).getfin_latitude() ;
		}
		
	}
	
	public int getNumSource()
	{
		return num_sommet_source ;
	}
	
	public int getNumDest()
	{
		return num_sommet_destination ;
	}
	
	public Descripteur getDescripteur()
	{
		return descripteur_de_l_arete ;
	}
	
	public int getLongueur()
	{
		return longeur_route ;
	}
	
	public ArrayList <Segment> getTableauDeSegment() 
	{
		return tab_delta_Segment ;
	}
	
	public int getNombreDeSegment()
	{
		return tab_delta_Segment.size() ;
	}
	
	public Segment getSegmentAemplacement(int num_emplacement )
	{
		return tab_delta_Segment.get(num_emplacement) ;
	}
	
	public int getNumzone()
	{
		return this.num_zone_successeur;
	}

}