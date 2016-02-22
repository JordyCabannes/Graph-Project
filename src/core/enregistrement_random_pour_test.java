package core;

public class enregistrement_random_pour_test {
	
	private int origine ;
	private int destination;
	
	public enregistrement_random_pour_test(int origine ,int destination)
	{
		this.origine=origine;
		this.destination= destination ; 
	}
	public enregistrement_random_pour_test()
	{
	}
	
	public void  setorigne(int or)
	{
		this.origine=or;
	}
	
	public void  setdest(int dest)
	{
		this.destination=dest;
	}
	
	public int getorigne()
	{
		return this.origine;
	}
	
	
	public int getdest()
	{
		return this.destination;
	}
	
}
