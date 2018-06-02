//package pl.graph;
//import java.util.*;
//
//class Graph {
//	class Polaczenie {
//		int v, w;
//
//		public Polaczenie(int v, int w) {
//			this.v = v;
//			this.w = w;
//		}
//
//		@Override
//		public String toString() {
//			return "(" + v + "," + w + ")";
//		}
//	}
//
//	List<Polaczenie> G[];
//
//	public Graph(int n) {
//		G = new LinkedList[n];
//		for (int i = 0; i < G.length; i++)
//			G[i] = new LinkedList<Polaczenie>();
//	}
//
//	boolean isConnected(int u, int v) {
//		for (Polaczenie i : G[u])
//			if (i.v == v)
//				return true;
//		return false;
//	}
//
//	void addEdge(int u, int v, int w) {
//		G[u].add(0, new Polaczenie(v, w));
//	}
//
//	@Override
//	public String toString() {
//		String result = "";
//		for (int i = 0; i < G.length; i++)
//			result += i + "=>" + G[i] + "\n";
//		return result;
//	}
//}
//
//public class GraphExample {
//	public static void main(String[] args) {
//		Graph g = new Graph(10);
//		g.addEdge(0, 2, 10);
//		g.addEdge(0, 5, 15);
//		g.addEdge(2, 5, 10);
//		g.addEdge(9, 3, 16);
//
//		System.out.println(g);
//		System.out.println(g.isConnected(9, 3));
//	}
//}