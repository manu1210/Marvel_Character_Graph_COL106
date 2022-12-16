import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Map;



public class A4_2019EE30551{
	
	public static class Graph {
		
		// Data Containers
		Map<String , HashMap<String, Double>> graph = new HashMap<>();
		Map<String, Boolean> visited = new HashMap<>();
		ArrayList<ArrayList<String>> dfs = new ArrayList<>();
		
		public void addNode(String new_node) {
			// Time Complexity = O(1)
			
			if(!graph.containsKey(new_node)) {
				graph.put(new_node, new HashMap<>());
			}
			return;
			
		}
		
		public void addEdge(String start_point, String end_point, double weight ) {
			// Time Complexity = O(1)
			
			if(!graph.containsKey(start_point) || !graph.containsKey(end_point)) {
				// Vertice not available so may be wrong data
				return;
			}
			if(!graph.get(start_point).containsKey(end_point)) {
				graph.get(start_point).put(end_point, weight);
			}
			if(!graph.get(end_point).containsKey(start_point)) {
				graph.get(end_point).put(start_point, weight);
			}

		}
		
		public void average() {
			//Time Complexity = O(Vertices) .i.e number of vertices or keys in the map(graph)
			
			double edgecount = 0;
			double nodecount = 0;
			
			for(String v : graph.keySet()) {
				edgecount += graph.get(v).size();
				nodecount++;
			}
			if(nodecount == 0) {
				System.out.println("0.00");
				return;
			}
			// round off 
			double average = edgecount/nodecount;
			String temp = String.format("%.2f", average);
			average = Double.parseDouble(temp);
			average = Math.round(average * 100.0) / 100.0;
			temp = String.format("%.2f", average);
			System.out.println(temp);
			
		}
		
		public void rank() {
			
			ArrayList<Map.Entry<String, HashMap<String, Double>>> sortedGraph = mergeSort();
			
			int count =0;
			int size = sortedGraph.size();
			
			for (Map.Entry<String, HashMap<String, Double>> en : sortedGraph) {
				
				count++;
				if(count == size) {
					System.out.println(en.getKey()); 
				}
				else {
					System.out.print(en.getKey() + ","); 
				}
	            
	        }

		}
		
		public void independent_storylines_dfs() {
			
			for(Map.Entry<String , HashMap<String, Double>> i : graph.entrySet()) {
				visited.put(i.getKey(), false);
			}
			
			for(String keyelement : graph.keySet()) {
				
				 if (!visited.get(keyelement)) {
					 
					 ArrayList<String> dfsvisit = new ArrayList<>();
					 depthFirstSearch(keyelement , dfsvisit);
					 dfs.add(dfsvisit);
					 
				 }
				
			}
			
			ArrayList<ArrayList<String>> sortedDfs = mergeSortDfs(dfs);
			
			for(ArrayList<String> i : sortedDfs) {
				
				int count = 0;
				int size = i.size();
				
				for(String j : i) {
					
					count++;
					if(count == size) {
						System.out.println(j);
					}
					else {
						System.out.print(j + ",");
					}
					
				}
				
			}
			return;

		}
		
		public void depthFirstSearch(String vertice , ArrayList<String> visitdfs) {
			
		    visited.replace(vertice, true);
		    
		    visitdfs.add(vertice);
		    

		    HashMap<String, Double> allNeighbors = graph.get(vertice);
		    if (allNeighbors == null) {
		    	return;
		    }
		        
		    for (Map.Entry<String, Double> neighbor : allNeighbors.entrySet()) {
		        if (!visited.get(neighbor.getKey()))
		            depthFirstSearch(neighbor.getKey(), visitdfs);
		    }
		    
		}
		
		public ArrayList<Map.Entry<String, HashMap<String, Double>>> mergeSort() {
			
			ArrayList<Map.Entry<String, HashMap<String, Double>>> sortedArray = new ArrayList<>();
			
			// copying graph in ArrayList sorted
			for(Map.Entry<String , HashMap<String, Double>> i : graph.entrySet()) {
				sortedArray.add(i);
			}
			
			int endIndex = sortedArray.size()-1;
			mergeS(sortedArray, 0, endIndex);
			
			return sortedArray;
			
		}
		
		public void mergeS(ArrayList<Map.Entry<String, HashMap<String, Double>>> A, int startIndex, int endIndex) {
	        
	        if(startIndex<endIndex && (endIndex-startIndex)>=1){
	            int mid = (endIndex + startIndex)/2;
	            mergeS(A, startIndex, mid);
	            mergeS(A, mid+1, endIndex);        
	            
	            //merging SortedArray
	            merge(A, startIndex, mid, endIndex);            
	        }
	        
	    }  
		
		public void merge(ArrayList<Map.Entry<String, HashMap<String, Double>>> A, int startIndex, int midIndex, int endIndex) {
	        
	        ArrayList<Map.Entry<String, HashMap<String, Double>>> mergedSortedArray = new ArrayList<>();
	        
	        int leftIndex = startIndex;
	        int rightIndex = midIndex+1;
	        
	        while(leftIndex<=midIndex && rightIndex<=endIndex){
	        	
	        	if(compare(A.get(leftIndex), A.get(rightIndex))>=0) {
	        		mergedSortedArray.add(A.get(leftIndex));
	                leftIndex++;
	        	}
	        	else {
	        		mergedSortedArray.add(A.get(rightIndex));
	                rightIndex++;
	        	}
	        	
	        }       
	        
	        //Copying remaining elements
	        while(leftIndex<=midIndex){
	            mergedSortedArray.add(A.get(leftIndex));
	            leftIndex++;
	        }
	        while(rightIndex<=endIndex){
	            mergedSortedArray.add(A.get(rightIndex));
	            rightIndex++;
	        }
	        
	        int i = 0;
	        int j = startIndex;
	        //Setting sorted array to original one
	        while(i<mergedSortedArray.size()){
	            A.set(j, mergedSortedArray.get(i++));
	            j++;
	        }
	        
	    }
		
		public int compare(Map.Entry<String, HashMap<String, Double>> o1, Map.Entry<String, HashMap<String, Double>> o2) {
			
			Double intobject1 = 0.0;
			Double intobject2 = 0.0;
			for(Map.Entry<String, Double> i : o1.getValue().entrySet()) {
				intobject1 += i.getValue();
			}
			for(Map.Entry<String, Double> i : o2.getValue().entrySet()) {
				intobject2 += i.getValue();
			}
			
			if(intobject1.equals(intobject2)) {
				return o1.getKey().compareTo(o2.getKey());
			}
			
			return (intobject1).compareTo(intobject2);
			
		}
		
		public ArrayList<ArrayList<String>> mergeSortDfs(ArrayList<ArrayList<String>> dfsArray){
			
			ArrayList<ArrayList<String>> sortedArray = new ArrayList<>();
			
			// copying graph in ArrayList sorted
			for(ArrayList<String> i : dfsArray) {
				i = mergeSortArray(i);
				sortedArray.add(i);
			}
			
			int endIndex = sortedArray.size()-1;
			mergeSortDfsUtil(sortedArray, 0, endIndex);
			
			return sortedArray;
		}
		
		public void mergeSortDfsUtil(ArrayList<ArrayList<String>> A, int startIndex, int endIndex) {
	        
	        if(startIndex<endIndex && (endIndex-startIndex)>=1){
	            int mid = (endIndex + startIndex)/2;
	            mergeSortDfsUtil(A, startIndex, mid);
	            mergeSortDfsUtil(A, mid+1, endIndex);        
	            
	            //merging SortedArray
	            mergeDfs(A, startIndex, mid, endIndex);            
	        }
	        
	    }
		
		public void mergeDfs(ArrayList<ArrayList<String>> A, int startIndex, int midIndex, int endIndex) {
	        
			ArrayList<ArrayList<String>> mergedSortedArray = new ArrayList<>();
	        
	        int leftIndex = startIndex;
	        int rightIndex = midIndex+1;
	        
	        while(leftIndex<=midIndex && rightIndex<=endIndex){
	        	
	        	if(compareDfs(A.get(leftIndex), A.get(rightIndex))>=0) {
	        		mergedSortedArray.add(A.get(leftIndex));
	                leftIndex++;
	        	}
	        	else {
	        		mergedSortedArray.add(A.get(rightIndex));
	                rightIndex++;
	        	}
	        	
	        }   
	        
	        //Copying remaining elements
	        while(leftIndex<=midIndex){
	            mergedSortedArray.add(A.get(leftIndex));
	            leftIndex++;
	        }
	        while(rightIndex<=endIndex){
	            mergedSortedArray.add(A.get(rightIndex));
	            rightIndex++;
	        }
	        
	        int i = 0;
	        int j = startIndex;
	        //Setting sorted array to original one
	        while(i<mergedSortedArray.size()){
	            A.set(j, mergedSortedArray.get(i++));
	            j++;
	        }
	        
	    }
		
		public int compareDfs(ArrayList<String> o1, ArrayList<String> o2) {
			
			int intobject1 = o1.size();
			int intobject2 = o2.size();
			
			if(intobject1 == intobject2) {
				return (o1.get(0).compareTo(o2.get(0)));
			}
			
			return (intobject1 -intobject2);
			
		}
		
		public ArrayList<String> mergeSortArray(ArrayList<String> array){
			ArrayList<String> sortedArray = new ArrayList<>();
			
			// copying graph in ArrayList sorted
			for(String i : array) {
				sortedArray.add(i);
			}
			
			int endIndex = sortedArray.size()-1;
			mergeSortArrayUtil(sortedArray, 0, endIndex);
			
			return sortedArray;
			
		}
		
		public void mergeSortArrayUtil(ArrayList<String> A, int startIndex, int endIndex) {
	        
	        if(startIndex<endIndex && (endIndex-startIndex)>=1){
	            int mid = (endIndex + startIndex)/2;
	            mergeSortArrayUtil(A, startIndex, mid);
	            mergeSortArrayUtil(A, mid+1, endIndex);        
	            
	            //merging SortedArray
	            mergeArray(A, startIndex, mid, endIndex);            
	        }
	        
	    }
		
		public void mergeArray(ArrayList<String> A, int startIndex, int midIndex, int endIndex) {
	        
			ArrayList<String> mergedSortedArray = new ArrayList<>();
	        
	        int leftIndex = startIndex;
	        int rightIndex = midIndex+1;
	        
	        while(leftIndex <= midIndex && rightIndex <= endIndex){
	        	
	        	if(A.get(leftIndex).compareTo(A.get(rightIndex)) >= 0) {
	        		mergedSortedArray.add(A.get(leftIndex));
	                leftIndex++;
	        	}
	        	else {
	        		mergedSortedArray.add(A.get(rightIndex));
	                rightIndex++;
	        	}
	        	
	        }   
	        
	        //Copying remaining elements
	        while(leftIndex<=midIndex){
	            mergedSortedArray.add(A.get(leftIndex));
	            leftIndex++;
	        }
	        while(rightIndex<=endIndex){
	            mergedSortedArray.add(A.get(rightIndex));
	            rightIndex++;
	        }
	        
	        int i = 0;
	        int j = startIndex;
	        //Setting sorted array to original one
	        while(i<mergedSortedArray.size()){
	            A.set(j, mergedSortedArray.get(i++));
	            j++;
	        }
	        
	    }

	}
	
	public static void main(String args[]) throws FileNotFoundException {
		
		Graph graph = new Graph(); 
		
		String pathnodes = "";
        String pathedges = "";
        String command = "";
		// for direct input from commandline
		if(args.length >= 3) {
			
			pathnodes = args[0];
	        pathedges = args[1];
	        command = args[2];

		}
		
		String data = "";
		String input_node = "";

		try {
			
			BufferedReader edgereader = new BufferedReader(new FileReader(pathedges));
			edgereader.readLine();
			BufferedReader nodereader = new BufferedReader(new FileReader(pathnodes));
			nodereader.readLine();
			
			// Creating the Graph with only Vertices
			while((input_node = nodereader.readLine()) != null ) {
				
				String[] temp = input_node.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			
				graph.addNode(temp[1].replace("\"", ""));
			
			}
			
			// Adding Edges in the graph
			while( (data =  edgereader.readLine())!=null ) {
				
				String[] temp = data.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				graph.addEdge(temp[0].replace("\"", ""), temp[1].replace("\"", ""), Double.parseDouble(temp[2]));

			}
			
			switch (command) {
	        case "average":
	            graph.average();
	            break;
	        case "rank":
	            graph.rank();
	            break;
	        case "independent_storylines_dfs":
	            graph.independent_storylines_dfs();
	            break;
	        default:
	            break;
			}
			
			edgereader.close();
			nodereader.close();
			
		}
			catch (IOException e) {
				e.printStackTrace();
		}	
		
	}

}
