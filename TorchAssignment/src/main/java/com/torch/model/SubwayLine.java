package com.torch.model;

public enum SubwayLine {
	RED("123"),
	GREEN("456"),
	PURPLE("7"),
	BLUE("ACE"),
	ORANGE("BDFM"),
	LIGHT_GREEN("G"),
	BROWN("JZ"),
	GREY("L"),
	YELLOW("NQR"),
	SHUTTLE("S"),
	SIR("SIR");
	
	private String name;
	
	private SubwayLine(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static SubwayLine getFromName(String n){
		
		for(SubwayLine line : SubwayLine.values()) {
			if(n.equalsIgnoreCase(line.getName())) {
				return line;
			}
		}
		
		return null;
	}
}
