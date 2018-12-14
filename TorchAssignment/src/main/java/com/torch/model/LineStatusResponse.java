package com.torch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LineStatusResponse {
	private SubwayLine line;
	private LineStatus status;
	
	@JsonProperty("currently_delayed")
	public boolean isDelayed() {
		return LineStatus.DELAYS == status;
	}
	@JsonProperty("line_name")
	public String getLineName() {
		return line.getName();
	}
	
	public LineStatusResponse() {
		
	}
	
	public LineStatusResponse(SubwayLine line, LineStatus status) {
		this.line = line;
		this.status = status;
	}
	
	public LineStatus getStatus() {
		return status;
	}

	public void setStatus(LineStatus status) {
		this.status = status;
	}
	
	@JsonIgnore
	public SubwayLine getSubwayLine() {
		return line;
	}
	public void setSubwayLine(SubwayLine line) {
		this.line = line;
	}
}
