package fordFulkerson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class FordFulkerson extends GraphConstruct {

	private static BufferedReader r;
	private static HashMap<String, HashMap<String, Nodes>> InputGraph;

	private static HashMap<String, Nodes> edge_connections;

	static boolean visited[];

	static long startTime, endTime;

	public static void main(String[] args) throws IOException {

		String inputLine = "";

		String source = "0";
		
		r = new BufferedReader(new FileReader(args[1]));

		int key = 0;

		InputGraph = new HashMap<String, HashMap<String, Nodes>>();

		while ((inputLine = r.readLine()) != null) {
			edge_connections = new HashMap<String, Nodes>();
			StringTokenizer st = new StringTokenizer(inputLine, " ");
			while (st.hasMoreTokens()) {
				String name = st.nextToken();
				int capacity = Integer.parseInt(st.nextToken());

				edge_connections.put(name, new Nodes(name, capacity));

			}
			InputGraph.put(Integer.toString(key), edge_connections);

			key++;

		}

		setBFS(InputGraph);
		startTime = System.currentTimeMillis();
		doBfs(new Nodes(source), InputGraph);

	}

	public static void setBFS(HashMap<String, HashMap<String, Nodes>> Input) {
		visited = new boolean[Input.keySet().size() + 2];
		for (int i = 0; i < Input.keySet().size() + 1; i++)
			visited[i] = false;
		// InputGraph=Input;
	}

	private static HashMap<String, Nodes> UpdateEdges, reverseEdges;
	private static HashMap<String, HashMap<String, Nodes>> tempGraph = new HashMap<String, HashMap<String, Nodes>>();;
    int NodeNumber = 0, DestLevel = 0;
	static int MaxFlow = 0;
	static int MinCapacity;
	int level = 0;
	int count = 0;
	Nodes source_new;

	private static LinkedList<Nodes> ShortPath = new LinkedList<Nodes>();
	private static HashMap<String, HashMap<String, Nodes>> InputGraph1 = new HashMap<String, HashMap<String, Nodes>>();

	public static void doBfs(Nodes source, HashMap<String, HashMap<String, Nodes>> InputGraph) {
		// TODO Auto-generated method stub
		boolean isDestinationAvailable = false;

		InputGraph1 = InputGraph;

		LinkedList<Nodes> Queue = new LinkedList<Nodes>();
		HashMap<String, Nodes> TraversalPath = new HashMap<String, Nodes>();
		HashMap<Nodes, Nodes> parent_Path = new HashMap<Nodes, Nodes>();

		int destination = InputGraph.size();
		visited[Integer.parseInt(source.getName())] = true;

		Queue.addFirst(source);

		while (!Queue.isEmpty()) {

			Nodes top = Queue.remove();
			HashMap<String, Nodes> ConnectedNodes = InputGraph.get(top.getName());

			Iterator<String> it = ConnectedNodes.keySet().iterator();

			while (it.hasNext()) {
				Nodes n1 = (Nodes) ConnectedNodes.get(it.next());

				if ((visited[Integer.parseInt(n1.getName())] != true) && n1.getweight() > 0) {

					visited[Integer.parseInt(n1.getName())] = true;
					n1.setVisited(true);
					Queue.add(n1);
					parent_Path.put(n1, top);

					TraversalPath.put(n1.getName(), n1);
				}
				if (Integer.parseInt(n1.getName()) == destination) {
					isDestinationAvailable = true;
					ShortPath.addFirst(n1);
					printPath(parent_Path, n1, source);
					Queue.clear();

					break;
				}
			}

		}

		if (isDestinationAvailable)

		{
			MinCapacity = getminCapacity(ShortPath);
			MaxFlow = constructResidualGraph(MinCapacity, ShortPath);

			setBFS(tempGraph);
			ShortPath.clear();
			doBfs(source, tempGraph);

		} else {

			System.out.println("The Maximum flow for the given graph is:" + MaxFlow);
			endTime = System.currentTimeMillis();
			System.out.println(
					"\nThe time of execution of FordFulkerson Algorithm is " + (endTime - startTime) + " millisecs");
		}

	}

	private static int constructResidualGraph(int c, LinkedList<Nodes> shortPath2) {
		// TODO Auto-generated method stub

		MaxFlow = MaxFlow + c;
		tempGraph = InputGraph1;

		for (int i = 0; i <= shortPath2.size() - 2; i++) {

			Nodes one = ShortPath.get(i);
			Nodes two = ShortPath.get(i + 1);
			UpdateEdges = InputGraph1.get(one.getName());
			Iterator<String> it = UpdateEdges.keySet().iterator();
			while (it.hasNext()) {
				Nodes sec = UpdateEdges.get(it.next());
				int cc = sec.getweight();
				if (sec.getName().equals(two.getName())) {
					if (cc == c) {
						UpdateEdges.remove(sec.getName());
					} else {
						int updcap = sec.getweight() - c;
						sec.setweight(updcap);
					}
					reverseEdges = InputGraph1.get(sec.getName());
					if (reverseEdges != null) {
						Iterator<String> it2 = reverseEdges.keySet().iterator();

						int flag = -1;
						while (it2.hasNext()) {
							if (it2.next().equals(one.getName())) {
								flag = 1;

							}
						}

						if (flag == -1) {
							Nodes n = new Nodes(one.getName(), c);
							reverseEdges.put(n.getName(), n);
							flag = -1;
						}
					} else {

						// Nodes n1= new Nodes(one.getName(),c);

					}
					tempGraph.put(one.getName(), UpdateEdges);
					if (reverseEdges != null)
						tempGraph.put(two.getName(), reverseEdges);
					break;
				}
			}
		}

		return MaxFlow;

	}

	public static int getminCapacity(LinkedList<Nodes> shortPath2) {
		// TODO Auto-generated method stub
		int min = 0;
		for (Nodes s : shortPath2) {
			if (min == 0)
				min = s.getweight();
			if (min > s.getweight())
				min = s.getweight();
		}
		
		return min;
	}

	public static void printPath(HashMap<Nodes, Nodes> parent_path, Nodes destination, Nodes source) {

		Nodes path = parent_path.get(destination);
		ShortPath.addFirst(path);

		while (!parent_path.get(destination).equals(source)) {

			printPath(parent_path, path, source);
			break;
		}

	}

}
