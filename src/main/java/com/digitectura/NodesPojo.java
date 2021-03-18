package com.digitectura;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NodesPojo {

	private Integer id;

	private String name;

	private List<Integer> paths = new ArrayList<Integer>();

	private Integer distance;

	public NodesPojo() {
		super();
	}

	public NodesPojo(Integer id, List<Integer> paths, Integer distance) {
		super();
		this.id = id;
		this.paths = paths;
		this.distance = distance;
	}

	public NodesPojo(Integer id, String name, List<Integer> paths, Integer distance) {
		super();
		this.id = id;
		this.name = name;
		this.paths = paths;
		this.distance = distance;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getPaths() {
		return paths;
	}

	public void setPaths(List<Integer> paths) {
		this.paths = paths;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

}
