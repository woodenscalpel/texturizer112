package com.geodesictriangle.texturizer.events;

import net.minecraft.util.math.BlockPos;

public class blockdistance{
		BlockPos position;
    	Double distance;
    	public blockdistance(BlockPos pos, double d) {
    		this.position = pos;
    		this.distance = d;
		}
    	public Double getDistance() {
    		return -this.distance;
    	}
    }

