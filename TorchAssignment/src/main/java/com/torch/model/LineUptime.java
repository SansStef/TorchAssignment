package com.torch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LineUptime {
	private SubwayLine line;
	private double uptime;

	public LineUptime() {
		
	}
	
	public LineUptime(SubwayLine line, double uptime) {
		this.line = line;
		this.uptime = uptime;
	}
	
	@JsonProperty("line_name")
	public String getLineName() {
		return line.getName();
	}
	
	@JsonIgnore
	public SubwayLine getSubwayLine() {
		return line;
	}
	public void setSubwayLine(SubwayLine line) {
		this.line = line;
	}
	public double getUptime() {
		return uptime;
	}
	public void setUptime(double uptime) {
		this.uptime = uptime;
	}
}
