package de.fuberlin.swp.ucd.net.client.model;

public class Item {
	
	/**	
	 * python code
	 * 
	 * 	name = models.CharField(max_length=30)
    	description = models.CharField(max_length=200)
    	image = models.CharField(max_length=200)
    	costs = models.IntegerField()
    	min_level = models.IntegerField()
    	item_type = models.ForeignKey(ItemType)
    	category = models.ForeignKey(ItemCategory, related_name='+')
    	allowed_category = models.ForeignKey(ItemCategory, related_name='+')
	 * 
	 */

	private String name;
	
	private String description;
	
	private String image;
	
	private Integer costs;
	
	private Integer minLevel;
	
	
	
	
}
