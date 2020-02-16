package com.raja.rest.config;

/**
 * @author albert.mannam
 *
 */
public class RestWebConfiguration  implements Config{
	
	private String pack;
		
	public Config basePack(String pack) {
		
		this.pack = pack;
		
		return this;
	}

	
	@Override
	public Config loadConfiguration() {
		
		return this;
	}


	public String getPack() {
		return pack;
	}



	public void setPack(String pack) {
		this.pack = pack;
	}
	
}
