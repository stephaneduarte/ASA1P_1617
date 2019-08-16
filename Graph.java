//PROJETO ASA

import java.io.*;
import java.util.*;

class InsuficientException extends Exception{}
class InconsistentException extends Exception{}

public class Graph{

	private int _numVertices;

	private LinkedList<Integer> _linked[];

	@SuppressWarnings("unchecked")
	public Graph(int numVertices){
		_numVertices = numVertices;
		_linked = new LinkedList[numVertices];
		for (int i=0; i < numVertices; i++){
			_linked[i] = new LinkedList<Integer>();
		}
	}

	public void edge(int u, int v){
		_linked[(u-1)].add(v-1); //Resolve-se o problema de comecar em 1
	}

	public boolean haveEdge(int u, int v){
		Iterator<Integer> iterator = _linked[u].iterator();
		while (iterator.hasNext()){
			int i = iterator.next();
			if (i == v){
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public void topologicalSortAux(int vertice, List visited, List list) throws InconsistentException, InsuficientException{
		visited.set(vertice,"opened");
		Iterator<Integer> iterator = _linked[vertice].iterator();
		while (iterator.hasNext()){
			int i = iterator.next();
			if(visited.get(i) == "opened"){
				throw new InconsistentException();
			}
			if(visited.get(i) == "nvisited"){
				topologicalSortAux(i, visited, list);
			}
		}
		visited.set(vertice,"visited");
		if (list.size() != 0 && !haveEdge(vertice, (int)list.get(list.size()-1))){
				throw new InsuficientException();
		}
		list.add(vertice);
	}

	public List topologicalSort() throws InconsistentException, InsuficientException{
		List<Integer> list = new ArrayList<Integer>();

		List<String> visited = new ArrayList<String>();

		for (int i = 0; i < _numVertices; i++){
			visited.add("nvisited");
		}

		for (int i = 0; i < _numVertices; i++){
			if (visited.get(i) == "nvisited"){
				topologicalSortAux(i, visited, list);
			}
		}

		return list;
	}

	public void printResults(List list){
		for (int i = (list.size() -1); i > (-1); i--){
			System.out.print((int)list.remove(i)+1); //Corrige-se para o numero dado pelo input
			if (i != 0) System.out.print(" ");
		}
		System.out.println();
	}

	public static void main(String args[])
    {
    	try{
	        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

	        //firstline = numVertices + " " + numLinhasInput
        	String[] firstline = bufferRead.readLine().split(" ");
	        Graph g = new Graph(Integer.parseInt(firstline[0]));

	        //line = antes + " " + depois
	        for (int i = 0; i < Integer.parseInt(firstline[1]); i++){
	        	String[] line = bufferRead.readLine().split(" ");
	        	g.edge(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
	        }
	        List list = g.topologicalSort();
	        g.printResults(list);
    	}
    	catch (InconsistentException e){ System.out.println("Incoerente"); }
    	catch (InsuficientException e){ System.out.println("Insuficiente"); }
    	catch (IOException e){  }
    }
};