package core;

public class LabelCovoiturage extends Label 
{
	//private int num_origine;
	
	private int nb_sortie;
	
	public LabelCovoiturage(boolean marquage, float cout, int pere, Noeud courant, int num_origine)
	{
		super(marquage, cout, pere, courant,num_origine);
		this.nb_sortie=0;
	}
	
	public int compareTo(Label other) 
	{	
		//System.out.println("HELLO");

		if ( this.getCout() < other.getCout())
			return -1;
			else return 0; 
	}
	
	public void incrementerNbSortie()
	{
		this.nb_sortie++;
	}
	
	public int getNbSortie()
	{
		return this.nb_sortie;
	}
}
