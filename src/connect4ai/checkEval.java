package connect4ai;

import customizedlinkedlist.*;

public class checkEval {
	public static void main(String args[]){
		int table[][] = {{0,0,0,0,0,0,0},
				         {0,0,0,0,0,0,0},
				         {0,0,0,0,0,0,0},
				         {0,0,0,0,0,0,0},
				         {0,0,0,0,0,0,0},
				         {0,0,0,1,0,0,0},};
		
		
		IterativeAlphaBetaPruning search = new IterativeAlphaBetaPruning(); 
		int action = search.ITERATIVE_DEEPENING_SEARCH(table,1,2);
		System.out.println(action+1);
	}
}
