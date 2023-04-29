package com.gmail.val59000mc.schematics;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Lobby extends Schematic {

	private static final String SCHEMATIC_NAME = "lobby";

	private int width, length, height;

	public Lobby(Location loc){
		super(SCHEMATIC_NAME, loc);

		// Dimensions for glass box
		width = 10;
		length = 10;
		height = 3;
	}

	@Override
	public void build(){
		// Paste schematic
		if (canBePasted()){
			super.build();

			height = getHeight();
			length = getLength();
			width = getWidth();
		}
		// Build glass box
		else {
			getLocation().add(0, 2, 0);
			int x = getLocation().getBlockX(), y=getLocation().getBlockY(), z=getLocation().getBlockZ();
			World world = getLocation().getWorld();
			for(int i = -length; i <= length; i++){
				for(int j = -height; j <= height; j++){
					for(int k = -width ; k <= width ; k++){
						if(i == -10
							|| i == 10
							|| j == -3
							|| j == 3
							|| k == -10
							|| k == 10
						){
							world.getBlockAt(x+i,y+j,z+k).setType(Material.GLASS);
						}else{
							world.getBlockAt(x+i,y+j,z+k).setType(Material.AIR);
						}
					}
				}
			}
		}
	}

	public void destroyBoundingBox(){
		int lobbyX = getLocation().getBlockX(), lobbyY = getLocation().getBlockY(), lobbyZ = getLocation().getBlockZ();

		World world = getLocation().getWorld();
		for(int x = -length; x <= length; x++){
			for(int y = height; y >= -height; y--){
				for(int z = -width ; z <= width ; z++){
					Block block = world.getBlockAt(lobbyX+x,lobbyY+y,lobbyZ+z);
					if(!block.getType().equals(Material.AIR)){
						block.setType(Material.AIR);
					}
				}
			}
		}
	}

}
