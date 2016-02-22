package core;

public class Segment {
	
		//changement : on enregistre les coordonnées pour dessiner entierement le segment ! 

		private float debut_longitude;
		private float debut_latitude ;
		private float fin_longitude ;
		private float fin_latitude ;
		
		
		//private boolean type; // à true pour une longitude et à faux pour une latitude
		
		public Segment(float debut_longitude ,float debut_latitude, float fin_longitude ,float fin_latitude)
		{
			this.debut_longitude =debut_longitude ;
			this.debut_latitude= debut_latitude ;
			this.fin_longitude =fin_longitude ;
			this.fin_latitude =fin_latitude ;
			
		}
		
		public float getdebut_longitude() 
		{
			return this.debut_longitude;
		}
		
		public float getdebut_latitude() 
		{
			return this.debut_latitude ;
		}
		
		public float getfin_longitude() 
		{
			return this.fin_longitude ;
		}
		
		public float getfin_latitude() 
		{
			return this.fin_latitude ;
		}
		
}