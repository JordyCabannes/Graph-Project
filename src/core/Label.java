package core;

public class Label implements Comparable<Label>{
	
	private boolean marquage;
	
	private float cout;
	
	private int  pere;
	
	private Noeud courant;
	private float estimation ; 
	
	private int num_origine;
	
	private int nb_sortie;
	
	private int fils;
	
	
	//private float deltatemps ; 
	
	public Label(boolean marquage, float cout, int pere, Noeud courant)
	{
		this.marquage=marquage;
		this.cout=cout;
		this.pere=pere;
		this.courant=courant;
		this.estimation= 0f;
	}
	
	public Label(boolean marquage, float cout, int pere, Noeud courant, int num_origine)
	{
		this.marquage=marquage;
		this.cout=cout;
		this.pere=pere;
		this.courant=courant;
		this.estimation= 0f;
		this.num_origine=num_origine;
		this.nb_sortie=0;
	}
	
	public Label(boolean marquage, float cout, int pere)
	{
		this.marquage=marquage;
		this.cout=cout;
		this.pere=pere;
	}
	
	public int getNumOrigine()
	{
		return num_origine ;
	}
	
	
	public float getCout()
	{
		return this.cout; 
	}
	
	public void setEstimation(float val)
	{
		this.estimation=val;
	}
	
	public float getEstimation()
	{
		return this.estimation;
	}
	
	public int getPere()
	{
		return this.pere;
	}
	
	public boolean getMarquage()
	{
		return this.marquage;
	}
	
	public Noeud getNoeudCourant()
	{
		return this.courant ;
	}

	
	public int compareTo(Label other) 
	{		
		if ( this.cout < other.getCout() )
			return -1;
		else return 0; 
	}
	
	public void setMarquage(boolean val ) 
	{
		this.marquage = val ;
	}
	
	public void setCout(float val ) 
	{
		this.cout = val ;
	}
	
	public void setPere(int value)
	{
		this.pere= value ;
	}
	
	public void incrementerNbSortie()
	{
		this.nb_sortie++;
	}
	
	public int getNbSortie()
	{
		return this.nb_sortie;
	}
	/*public void setdeltaTemps(float value)
	{
		this.deltatemps = value ;
	}
	
	public float getdeltatemps()
	{
		return this.deltatemps ; 
	}*/
	public int getFils()
	{
		return this.fils;
	}
	
	public void setFils(int val ) 
	{
		this.fils= val ;
	}
	
	
}
