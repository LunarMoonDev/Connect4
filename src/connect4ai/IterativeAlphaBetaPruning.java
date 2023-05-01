package connect4ai;

import customizedlinkedlist.SLLNode;
import customizedlinkedlist.SLLTree;

public class IterativeAlphaBetaPruning {
	public int ITERATIVE_DEEPENING_SEARCH (int[][] state,int mainTile, int opTile){
		SLLNode<int[][]> initialState = new SLLNode<int[][]> (state);
		SLLTree<int[][]> gameTree = new SLLTree<int[][]>();
		int result = 0;
		DepthLimitAlphaBetaPruning search = new DepthLimitAlphaBetaPruning();
		search.setGameTree(gameTree);
		search.setMyTile(mainTile);
		search.setOponentTile(opTile);
		
		double startTime = System.nanoTime() / 1000000000;
		double endTime = 0;
		for(int depth = 0; depth < 7; depth++){
				
				result = search.ALPHA_BETA_SEARCH(initialState, depth);
				endTime = System.nanoTime() / 1000000000;
				System.out.println("Time: " + (endTime - startTime) +"Depth: "+ depth);
			
		}
		return result;
	}
}
