package core;
import java.util.*;

public class Chemin {
	
	private ArrayList<Integer> SuccessionDeNoeud;
	
	public Chemin()
	{
		SuccessionDeNoeud = new ArrayList<Integer>();
	}
	
	public void FormerListeSuccesseur(int a)
	{
		SuccessionDeNoeud.add(new Integer(a));
	}
	
	public int accederElement(int i )
	{
		return SuccessionDeNoeud.get(i) ;
	}
	
	public int getNombreSommet()
	{
		return SuccessionDeNoeud.size() ;
	}

}