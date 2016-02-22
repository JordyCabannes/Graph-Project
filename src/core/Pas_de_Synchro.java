package core;
//exception à lever s'il n'y a pas de noeud de synchronisation
public class Pas_de_Synchro extends Exception
{
		private String contenu ;
		
		public Pas_de_Synchro(String contenu)
		{
			this.contenu=contenu ;
		}
	
}
