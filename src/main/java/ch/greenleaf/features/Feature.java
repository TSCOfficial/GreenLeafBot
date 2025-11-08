package ch.greenleaf.features;

public abstract class Feature {
	protected long guild_id;
	
	public Feature(long guild_id){
		this.guild_id = guild_id;
		fetchDatabase();
		getTemplate();
	}
	
	private void fetchDatabase(){}
	
	private void getTemplate(){}
}
