package com.digitectura;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.algorithm.Algorithm.SearchResult;
import es.usc.citius.hipster.graph.GraphSearchProblem;
import es.usc.citius.hipster.graph.HashBasedHipsterGraph;
import es.usc.citius.hipster.graph.HipsterMutableGraph;
import es.usc.citius.hipster.model.impl.WeightedNode;
import es.usc.citius.hipster.model.problem.SearchProblem;

//@Component
@SuppressWarnings("rawtypes")
public class CreateNodes implements CommandLineRunner {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void run(String... args) throws Exception {
//		List<String> nodesPojoList = new ArrayList<>();
//		List<Integer> paths = new ArrayList<Integer>();
//		paths.add(2);
//		paths.add(3);
//		paths.add(4);
//		NodesPojo nodesPojo = new NodesPojo(1, "Entrence 1", paths, 12);
//		String data = objectMapper.writeValueAsString(nodesPojo);
//		nodesPojoList.add(data);
//		paths = new ArrayList<Integer>();
//		paths.add(3);
//		paths.add(4);
//		paths.add(1);
//		paths.add(5);
//		nodesPojo = new NodesPojo(2, paths, 3);
//		data = objectMapper.writeValueAsString(nodesPojo);
//		nodesPojoList.add(data);
//
//		paths = new ArrayList<Integer>();
//		nodesPojo = new NodesPojo(5, paths, "desk1", 5);
//		data = objectMapper.writeValueAsString(nodesPojo);
//		nodesPojoList.add(data);
//
//		nodesPojo = new NodesPojo(3, paths, "desk2", 5);
//		data = objectMapper.writeValueAsString(nodesPojo);
//		nodesPojoList.add(data);
//
//		nodesPojo = new NodesPojo(4, paths, "desk3", 10);
//		data = objectMapper.writeValueAsString(nodesPojo);
//		nodesPojoList.add(data);
//
//		getPath(nodesPojoList);

	}

	@SuppressWarnings("unchecked")
	public List<Integer> getPath(List<String> nodesList) throws JsonMappingException, JsonProcessingException {
		HipsterMutableGraph<Integer, Integer> graph1 = new HashBasedHipsterGraph<Integer, Integer>();
		List<Integer> pathList = new ArrayList<Integer>();
		List<NodesPojo> nodesPojoList = new ArrayList<NodesPojo>();
		Map<Integer, Integer> distanceMap = new HashMap<Integer, Integer>();
		for (String nodeString : nodesList) {
			NodesPojo nodesPojoObj = objectMapper.readValue(nodeString, NodesPojo.class);
			nodesPojoList.add(nodesPojoObj);
			distanceMap.put(nodesPojoObj.getId(), nodesPojoObj.getDistance());
			graph1.add(nodesPojoObj.getId());
		}
		String cubicleId = "desk1";
		Integer to = null;
		Integer from = 1;
		for (NodesPojo nodesPojoObj : nodesPojoList) {
			if (nodesPojoObj.getPaths().size() > 0) {
				for (Integer path : nodesPojoObj.getPaths()) {
					graph1.connect(nodesPojoObj.getId(), path,
							(distanceMap.get(nodesPojoObj.getId()) + distanceMap.get(path)));
				}
			}
//			if (nodesPojoObj.getCubicleId() != null) {
//				if (nodesPojoObj.getCubicleId().equalsIgnoreCase(cubicleId)) {
//					to = nodesPojoObj.getId();
//				}
//			}
		}
//		if (to != null && from != null) {
//			SearchProblem<Integer, Integer, WeightedNode<Integer, Integer, Double>> p1 = GraphSearchProblem
//					.startingFrom(from).in(graph1).takeCostsFromEdges().build();
//			SearchResult ss = Hipster.createDijkstra(p1).search(to);
//			List<List<String>> paths = ss.getOptimalPaths();
//			for (List<String> path : paths) {
//				System.out.println(path);
//			}
//		}
		return pathList;
	}

}
