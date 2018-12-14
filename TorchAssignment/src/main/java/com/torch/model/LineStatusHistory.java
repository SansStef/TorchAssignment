package com.torch.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = "subway_line_status_history")
public class LineStatusHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    
	@Enumerated(EnumType.STRING)
	@Column(name="subway_line", length = 15)
	private SubwayLine subwayLine;

	@Enumerated(EnumType.STRING)
	@Column(name="line_status", length = 15)
	private LineStatus status;
    
    @CreationTimestamp
	@Column(name="updated_at")
    private Date updatedAt;

    public LineStatusHistory() {
    	
    }
    
	public LineStatusHistory(SubwayLine line, LineStatus status) {
		this.subwayLine = line;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SubwayLine getSubwayLine() {
		return subwayLine;
	}

	public void setSubwayLine(SubwayLine line) {
		this.subwayLine = line;
	}

	public LineStatus getStatus() {
		return status;
	}

	public void setStatus(LineStatus status) {
		this.status = status;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
