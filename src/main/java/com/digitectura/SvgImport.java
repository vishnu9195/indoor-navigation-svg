package com.digitectura;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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

@Component
public class SvgImport implements CommandLineRunner {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void run(String... args) throws Exception {
		List<String> codes = new ArrayList<>();
		File file = new File("C:/Users/vishn/Downloads/1.svg");
		if (file.exists()) {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String data = scanner.nextLine();
				if (data.contains("</text>")) {
					if (!data.contains("id=")) {
						String split[] = data.split("</text>");
						if (split.length > 0) {
							split = split[0].split(">");
							if (split.length > 1) {
								String value = split[1];
								value = value.replaceAll("&quot;", "\"");
								codes.add(value);
							}
						}
					}
				}
			}
			scanner.close();

			List<List<Integer>> paths = getPath(codes, "desk_x5F_30_1_", "Digitectura Entrance");
			for (List<Integer> path : paths) {
				List<Integer> path1 = path;
				for (Integer path2 : path1) {
					System.out.print("-->line" + path2);
				}
			}
		}
	}

	public List<List<Integer>> getPath(List<String> nodesList, String toName, String fromName)
			throws JsonMappingException, JsonProcessingException {
		HipsterMutableGraph<Integer, Integer> graph1 = new HashBasedHipsterGraph<Integer, Integer>();
		List<List<Integer>> paths = new ArrayList<List<Integer>>();
		List<NodesPojo> nodesPojoList = new ArrayList<NodesPojo>();
		Map<Integer, Integer> distanceMap = new HashMap<Integer, Integer>();
		for (String nodeString : nodesList) {
			NodesPojo nodesPojoObj = objectMapper.readValue(nodeString, NodesPojo.class);
			nodesPojoList.add(nodesPojoObj);
			distanceMap.put(nodesPojoObj.getId(), nodesPojoObj.getDistance());
			graph1.add(nodesPojoObj.getId());
		}
		Integer from = null;
		Integer to = null;
		for (NodesPojo nodesPojoObj : nodesPojoList) {
			if (nodesPojoObj.getPaths().size() > 0) {
				for (Integer path : nodesPojoObj.getPaths()) {
					if (distanceMap.get(nodesPojoObj.getId()) != null && distanceMap.get(path) != null) {
						graph1.connect(nodesPojoObj.getId(), path,
								(distanceMap.get(nodesPojoObj.getId()) + distanceMap.get(path)));
					}
				}
			}
			if (nodesPojoObj.getName() != null) {
				if (nodesPojoObj.getName().equalsIgnoreCase(toName)) {
					to = nodesPojoObj.getId();
				} else if (nodesPojoObj.getName().equalsIgnoreCase(fromName)) {
					from = nodesPojoObj.getId();
				}
			}
		}
		if (to != null && from != null) {
			SearchProblem<Integer, Integer, WeightedNode<Integer, Integer, Double>> p1 = GraphSearchProblem
					.startingFrom(from).in(graph1).takeCostsFromEdges().build();
			SearchResult ss = Hipster.createDijkstra(p1).search(to);
			paths = ss.getOptimalPaths();
		}
		return paths;
	}

}
