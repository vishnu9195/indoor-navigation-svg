package com.digitectura;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import es.usc.citius.hipster.algorithm.Algorithm.SearchResult;
import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.graph.GraphSearchProblem;
import es.usc.citius.hipster.graph.HashBasedHipsterGraph;
import es.usc.citius.hipster.graph.HipsterMutableGraph;
import es.usc.citius.hipster.model.problem.SearchProblem;

//@Component
@SuppressWarnings({ "unchecked" })
public class Test implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		HipsterMutableGraph<String, Integer> graph1 = new HashBasedHipsterGraph<String, Integer>();
		graph1.add("A");
		graph1.add("B");
		graph1.add("C");
		graph1.add("D");
		graph1.add("E");
		graph1.add("F");
		graph1.connect("A", "B", 4);
		graph1.connect("B", "A", 4);
		graph1.connect("A", "C", 2);
		graph1.connect("C", "A", 2);
		graph1.connect("B", "C", 5);
		graph1.connect("C", "B", 5);
		graph1.connect("B", "D", 10);
		graph1.connect("D", "B", 10);
		graph1.connect("C", "E", 3);
		graph1.connect("E", "C", 3);
		graph1.connect("D", "F", 11);
		graph1.connect("F", "D", 11);
		graph1.connect("E", "D", 4);
		graph1.connect("D", "E", 4);
		SearchProblem p1 = GraphSearchProblem.startingFrom("A").in(graph1).takeCostsFromEdges().build();
		SearchResult ss = Hipster.createDijkstra(p1).search("F");
		List<List<String>> paths = ss.getOptimalPaths();
		for (List<String> path : paths) {
			System.out.println(path);
		}

	}

}
