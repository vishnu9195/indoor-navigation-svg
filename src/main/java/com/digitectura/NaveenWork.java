package com.digitectura;

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

//@Component
public class NaveenWork implements CommandLineRunner {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void run(String... args) throws Exception {
		List<String> nodesPojoList = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("You want to add node type yes else no");
			String value = scanner.nextLine();
			if (value.equalsIgnoreCase("yes")) {
				System.out.println("Enter paths one by one. when completed say stop");
				List<Integer> paths = new ArrayList<Integer>();
				while (true) {
					value = scanner.nextLine();
					if (value.equalsIgnoreCase("stop")) {
						break;
					} else {
						paths.add(Integer.parseInt(value));
					}
				}
				System.out.println("Enter id");
				value = scanner.nextLine();
				Integer id = Integer.parseInt(value);
				System.out.println("Enter distance");
				value = scanner.nextLine();
				Integer distance = Integer.parseInt(value);
				System.out.println("You want to enter name type yes else no");
				value = scanner.nextLine();
				NodesPojo nodesPojo = new NodesPojo(id, paths, distance);
				if (value.equalsIgnoreCase("yes")) {
					System.out.println("Enter name");
					value = scanner.nextLine();
					nodesPojo.setName(value);
				}
				String data = objectMapper.writeValueAsString(nodesPojo);
				System.out.println(data);
				nodesPojoList.add(data);
			} else {
				break;
			}
		}
		System.out.println("You want to check path exits type yes else no");
		String value = scanner.nextLine();
		if (value.equalsIgnoreCase("yes")) {
			System.out.println("Enter from name");
			String value1 = scanner.nextLine();

			System.out.println("Enter to named");
			value = scanner.nextLine();
			List<List<String>> paths = getPath(nodesPojoList, value, value1);
			for (List<String> path : paths) {
				System.out.println(path);
			}
		}
		scanner.close();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<List<String>> getPath(List<String> nodesList, String toName, String fromName)
			throws JsonMappingException, JsonProcessingException {
		HipsterMutableGraph<Integer, Integer> graph1 = new HashBasedHipsterGraph<Integer, Integer>();
		List<List<String>> paths = new ArrayList<List<String>>();
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
					graph1.connect(nodesPojoObj.getId(), path,
							(distanceMap.get(nodesPojoObj.getId()) + distanceMap.get(path)));
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
