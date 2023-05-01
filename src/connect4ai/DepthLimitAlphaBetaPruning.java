package connect4ai;

import java.util.ArrayList;

import Utility.FinalVariables;
import customizedlinkedlist.SLLNode;
import customizedlinkedlist.SLLTree;

public class DepthLimitAlphaBetaPruning implements FinalVariables{
	

	
	
	private int myTile;
	private int oponentTile;
	private int action;
	private SLLTree<int[][]> gameTree;
	
	public SLLTree<int[][]> getGameTree() {
		return gameTree;
	}
	public void setGameTree(SLLTree<int[][]> gameTree) {
		this.gameTree = gameTree;
	}
	public int getMyTile() {
		return myTile;
	}
	public void setMyTile(int myTile) {
		this.myTile = myTile;
	}
	public int getOponentTile() {
		return oponentTile;
	}
	public void setOponentTile(int oponentTile) {
		this.oponentTile = oponentTile;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	
	public int ALPHA_BETA_SEARCH(SLLNode<int[][]> state, int limit){
		
		int v = MAX_VALUE(state, -2147483648, 2147483647, limit);
		return findAction(state, v);
	}
	public int MAX_VALUE(SLLNode<int[][]> state, int alpha, int beta, int limit){
		
		int v = -2147483648;
		if (CUTOFF_TEST(state, limit))
			return state.value;
		
		state.player = "MAX";
		
		ArrayList<SLLNode<int[][]>> successors = SUCCESSORS(state, limit);
		for(int i = 0; i < successors.size(); i++){
			SLLNode<int[][]> s = successors.get(i);
			int w = MIN_VALUE(s,alpha,beta,limit);
			v = MAX(v,w);
			if (v>= beta) return v;
			alpha = MAX(alpha, v);
		}
		state.value = v;
		return v;
	}
	public int MIN_VALUE(SLLNode<int[][]> state, int alpha, int beta, int limit){
		int v = 2147483647;
		
		state.player = "MIN";
		
		if (CUTOFF_TEST(state, limit))
			return state.value;
		/*
		 * or
		 * if (CUTOFF_TEST(state, limit))
		 * 	return state.value
		 */
		
		ArrayList<SLLNode<int[][]>> successors = SUCCESSORS(state, limit);
		
		for(int i = 0; i < successors.size(); i++){
			SLLNode<int[][]> s = successors.get(i);
			v = MAX(v*-1, MAX_VALUE(s,alpha,beta,limit)*-1) * -1;
			if (v <= alpha) return v;
			beta = (MAX(beta*-1, v*-1)*-1);
		}
		state.value = v;
		return v;
	}
	public boolean CUTOFF_TEST(SLLNode<int[][]> state, int limit){
		if(state.depth == limit ){
			return true;
		}
		return false;
	}
	public int EVAL(SLLNode<int[][]> state){
		int eval = 0;
		int fourMyFeatures[][] = new int[1][2];
		int fourOpFeatures[][] = new int[1][2];
		int threeMyFeatures[][] = new int[1][2];
		int twoMyFeatures[][] = new int[1][2];
		int threeOpFeatures[][] = new int[1][2];
		int twoOpFeatures[][] = new int[1][2];
		
		fourMyFeatures = fourInARow(state.info,myTile,fourMyFeatures);
		fourOpFeatures = fourInARow(state.info,oponentTile,fourOpFeatures);		
		threeMyFeatures = threeInARow(state.info,myTile,threeMyFeatures);
		threeOpFeatures = threeInARow(state.info,oponentTile,threeOpFeatures);
		twoMyFeatures = twoInARow(state.info,myTile,twoMyFeatures);
		twoOpFeatures = twoInARow(state.info,oponentTile,twoOpFeatures);
		eval = (fourMyFeatures[0][0]* 100000)+(threeMyFeatures[0][0]* 9) + (threeMyFeatures[0][1] * 8) + (twoMyFeatures[0][0] * 4) + (twoMyFeatures[0][1] * 3)
			  -(fourOpFeatures[0][0] * 100000)-(threeOpFeatures[0][0]* 9) - (threeOpFeatures[0][1] * 8) - (twoOpFeatures[0][0] * 4) - (twoOpFeatures[0][1] * 3);
		
		return eval;
	}
	
	public int[][] threeInARow(int theArray[][], int Tile, int features[][]){
			int tilesTotal;
			if(Tile == 1){
				tilesTotal = 3;
			}
			else{
				tilesTotal = 6;
			}
			String cell4;
			for (int row=0; row<MAXROW; row++) {
				for (int col=0; col<MAXCOL-3; col++) {
					int t = theArray[row][col] + theArray[row][col+1]+theArray[row][col+2]+theArray[row][col+3];
					cell4 = theArray[row][col] +""+ theArray[row][col+1]+""+theArray[row][col+2]+""+theArray[row][col+3];
					if(t == tilesTotal && (t/Tile) == (cell4.length() - cell4.replaceAll(Tile+"", "").length())){
						boolean isUsefull = checkUsefullRow(theArray,row,col, Tile);
						if(isUsefull){
							features[0][0]++;
						}
						else{
							features[0][1]++;
						}
					}
				}
			}
			
			for (int row=0; row<MAXROW-3; row++) {
				for (int col=0; col<MAXCOL-3; col++) {
					int curr = theArray[row][col];
					int t = theArray[row][col] + theArray[row+1][col+1] + theArray[row+2][col+2] + theArray[row+3][col+3];
					cell4 = theArray[row][col] +""+ theArray[row+1][col+1] +""+ theArray[row+2][col+2] +""+ theArray[row+3][col+3];
					if(t == tilesTotal && (t/Tile) == (cell4.length() - cell4.replaceAll(Tile+"", "").length())){
						boolean isUsefull = checkUsefullDiagL(theArray,row,col, Tile);
						if(isUsefull){
							features[0][0]++;
						}
						else{
							features[0][1]++;
						}
					}
				}
			}
			
			for (int row=MAXROW-1; row>=3; row--) {
				for (int col=0; col<MAXCOL-3; col++) {
					int curr = theArray[row][col];
					int t = theArray[row][col] + theArray[row-1][col+1]	+theArray[row-2][col+2] + theArray[row-3][col+3];
					cell4 = theArray[row][col] +""+ theArray[row-1][col+1] +""+ theArray[row-2][col+2] +""+ theArray[row-3][col+3];
					if(t == tilesTotal && (t/Tile) == (cell4.length() - cell4.replaceAll(Tile+"", "").length())){
						boolean isUsefull = checkUsefullDiagR(theArray,row,col, Tile);
						if(isUsefull){
							features[0][0]++;
						}
						else{
							features[0][1]++;
						}
					}
				}
			}
			for (int col=0; col<MAXCOL; col++) {
				for (int row=0; row<MAXROW-3; row++) {
					int t = theArray[row][col] + theArray[row+1][col] + theArray[row+2][col] + theArray[row+3][col];
					cell4 = theArray[row][col] +""+ theArray[row+1][col] +""+ theArray[row+2][col] +""+ theArray[row+3][col];
					if(t == tilesTotal && (t/Tile) == (cell4.length() - cell4.replaceAll(Tile+"", "").length())){
							features[0][0]++;
					}
				}
			}
			return features;
			
			
	}
	public boolean checkUsefullRow(int table[][], int row, int col, int Tile){
		boolean usefull = false;
		for(int c = col; c < col+4; c++){
			if(table[row][c] == 0){
				if(row < 5 && table[row+1][c]>0){
					usefull = true;
					break;
				}
				else if(row == 5){
					usefull = true;
					break;
				}
				else{
					usefull = false;
					break;
				}
			}
		}
		return usefull;
	}
	public boolean checkUsefullDiagR(int table[][], int row, int col, int Tile){
		boolean usefull = false;
		
		for(int c = col, r = row; c < col+4 && r > row-4; c++, r--){
			if(table[r][c] == 0){
				if(r < 5 && table[r+1][c]>0){
					usefull = true;
					break;
				}
				else if(r == 5){
					usefull = true;
					break;
				}
				else{
					usefull = false;
					break;
				}
			}
		}

		return usefull;
	}
	public boolean checkUsefullDiagL(int table[][], int row, int col, int Tile){
		int flag = 0, flag2 = 0;
		if(table[row][col] == 0){
			flag = row;
			flag2 = col;
		}
		else if(table[row+1][col+1] == 0){
			flag = row+1;
			flag2 = col+1;
		}
		else if(table[row+2][col+2] == 0){
			flag = row+2;
			flag2 = col+2;
		}
		else if(table[row+3][col+3] == 0){
			flag = row+3;
			flag2 = col+3;
		}
		
		if(flag < 5 && table[flag+1][flag2] > 0){
			return true;
		}
		else if(flag == 5){
			return true;
		}
		
		return false;
	}
	
	public int[][] twoInARow(int theArray[][], int Tile, int features[][]){
		int tilesTotal;
		if(Tile == 1){
			tilesTotal = 2;
			
		}
		else{
			tilesTotal = 4;
		}
		String cell4;
		for (int row=0; row<MAXROW; row++) {
			for (int col=0; col<MAXCOL-3; col++) {
				int t = theArray[row][col] + theArray[row][col+1]+theArray[row][col+2]+theArray[row][col+3];
				cell4 = theArray[row][col] +""+ theArray[row][col+1]+""+theArray[row][col+2]+""+theArray[row][col+3];
				if(t == tilesTotal && (t/Tile)== (cell4.length() - cell4.replaceAll(Tile+"", "").length())){
					boolean isUsefull = checkTwoTileUsefullRow(theArray,row,col, Tile);
					if(isUsefull){
						features[0][0]++;
					}
					else{
						features[0][1]++;
					}
				}
			}
		}
		for (int col=0; col<MAXCOL; col++) {
			for (int row=0; row<MAXROW-3; row++) {
				int t = theArray[row][col] + theArray[row+1][col] + theArray[row+2][col] + theArray[row+3][col];
				cell4 = theArray[row][col] +""+ theArray[row+1][col] +""+ theArray[row+2][col] +""+ theArray[row+3][col];
				if(t == tilesTotal && (t/Tile)== (cell4.length() - cell4.replaceAll(Tile+"", "").length())){
						features[0][0]++;
				}
			}
		}
		for (int row=0; row<MAXROW-3; row++) {
			for (int col=0; col<MAXCOL-3; col++) {
				int t = theArray[row][col] + theArray[row+1][col+1] + theArray[row+2][col+2] + theArray[row+3][col+3];
				cell4 = theArray[row][col] +""+ theArray[row+1][col+1] +""+ theArray[row+2][col+2] +""+ theArray[row+3][col+3];
				if(t == tilesTotal && (t/Tile)== (cell4.length() - cell4.replaceAll(Tile+"", "").length())){
					boolean isUsefull = checkTwoTileUsefullDiagL(theArray,row,col, Tile);
					if(isUsefull){
						features[0][0]++;
					}
					else{
						features[0][1]++;
					}
				}
			}
		}
		for (int row=MAXROW-1; row>=3; row--) {
			for (int col=0; col<MAXCOL-3; col++) {
				int curr = theArray[row][col];
				int t = theArray[row][col] + theArray[row-1][col+1]	+theArray[row-2][col+2] + theArray[row-3][col+3];
				cell4 = theArray[row][col] +""+ theArray[row-1][col+1] +""+ theArray[row-2][col+2] +""+ theArray[row-3][col+3];
				if(t == tilesTotal && (t/Tile)== (cell4.length() - cell4.replaceAll(Tile+"", "").length())){
					boolean isUsefull = checkTwoTileUsefullDiagR(theArray,row,col, Tile);
					if(isUsefull){
						features[0][0]++;
					}
					else{
						features[0][1]++;
					}
				}
			}
		}
		return features;
		
		
	}
	public boolean checkTwoTileUsefullRow(int table[][], int row, int col, int Tile){
		boolean usefull = false;
		for(int c = col; c < col+4; c++){
			if(table[row][c] == 0){
				if(row < 5 && table[row+1][c]>0){
					usefull = true;
					break;
				}
				else if(row == 5){
					usefull = true;
					break;
				}
				else
					usefull = false;
			}
		}
		return usefull;
	}
	public boolean checkTwoTileUsefullDiagL(int table[][], int row, int col, int Tile){
		boolean usefull = false;
		
			for(int c = col, r = row; c < col+4 && r < row+4; c++, r++){
				if(table[r][c] == 0){
					if(r < 5 && table[r+1][c]>0){
						usefull = true;
						break;
					}
					else if(r == 5){
						usefull = true;
						break;
					}
					else
						usefull = false;
				}
			}
		
		return usefull;
	}
	public boolean checkTwoTileUsefullDiagR(int table[][], int row, int col, int Tile){
		boolean usefull = false;
	
		for(int c = col, r = row; c < col+4 && r > row-4; c++, r--){
			if(table[r][c] == 0){
				if(r < 5 && table[r+1][c]>0){
					usefull = true;
					break;
				}
				else if(r == 5){
					usefull = true;
					break;
				}
				else
					usefull = false;
			}
		}

		return usefull;
	}
	
	public int[][] fourInARow(int theArray[][], int Tile, int features[][]){
		int tilesTotal;
		if(Tile == 1){
			tilesTotal = 4;
		}
		else{
			tilesTotal = 8;
		}
		String cell4;
		for (int row=0; row<MAXROW; row++) {
			for (int col=0; col<MAXCOL-3; col++) {
				int t = theArray[row][col] + theArray[row][col+1]+theArray[row][col+2]+theArray[row][col+3];
				cell4 = theArray[row][col] +""+ theArray[row][col+1]+""+theArray[row][col+2]+""+theArray[row][col+3];
				if(t == tilesTotal && (t/Tile) == (cell4.length() - cell4.replaceAll(Tile+"", "").length())){
					features[0][0]++;
				}
			}
		}
		for (int row=0; row<MAXROW-3; row++) {
			for (int col=0; col<MAXCOL-3; col++) {
				int curr = theArray[row][col];
				int t = theArray[row][col] + theArray[row+1][col+1] + theArray[row+2][col+2] + theArray[row+3][col+3];
				cell4 = theArray[row][col] +""+ theArray[row+1][col+1] +""+ theArray[row+2][col+2] +""+ theArray[row+3][col+3];
				if(t == tilesTotal && (t/Tile) == (cell4.length() - cell4.replaceAll(Tile+"", "").length())){
					features[0][0]++;
				}
			}
		}
		for (int row=MAXROW-1; row>=3; row--) {
			for (int col=0; col<MAXCOL-3; col++) {
				int curr = theArray[row][col];
				int t = theArray[row][col] + theArray[row-1][col+1]	+theArray[row-2][col+2] + theArray[row-3][col+3];
				cell4 = theArray[row][col] +""+ theArray[row-1][col+1] +""+ theArray[row-2][col+2] +""+ theArray[row-3][col+3];
				if(t == tilesTotal && (t/Tile) == (cell4.length() - cell4.replaceAll(Tile+"", "").length())){
					features[0][0]++;
				}
			}
		}
		for (int col=0; col<MAXCOL; col++) {
			for (int row=0; row<MAXROW-3; row++) {
				int t = theArray[row][col] + theArray[row+1][col] + theArray[row+2][col] + theArray[row+3][col];
				cell4 = theArray[row][col] +""+ theArray[row+1][col] +""+ theArray[row+2][col] +""+ theArray[row+3][col];
				if(t == tilesTotal && (t/Tile) == (cell4.length() - cell4.replaceAll(Tile+"", "").length())){
					features[0][0]++;
				}
			}
		}
		return features;
	}
	public ArrayList<SLLNode<int[][]>> SUCCESSORS(SLLNode<int[][]> state, int limit){
		int[][] connect4ArrayReset = state.info;
		int[][] connect4Array = state.info;
		int value, v = 0;
		boolean prepared = false;
		if (CUTOFF_TEST(state, limit-1))
			prepared = true;
		
		
		if(state.player.equals("MAX"))
			value = myTile;
		else
			value = oponentTile;
		
		for(int j = 0; j < connect4Array[0].length; j++){
			connect4Array = connect4ArrayReset;
			int i = 0;
			while((i<connect4Array.length)){
				if((connect4Array[i][j] == 0)){
					i++;
				}
				else
					break;
			}
			i--;
			if(i > -1 ){
				connect4Array[i][j] = value;
				SLLNode<int[][]> a = new SLLNode<int[][]>( ArraytoArray(connect4Array) ) ;
				if(prepared)
					v = EVAL(a);
				a = gameTree.setChild(state, a , v);
				
				a.setAction(j);
				connect4Array[i][j] = 0;
			}
		}
			
		return state.getChildren();
	}
	public int[][] ArraytoArray(int t[][]){
		int a[][] = new int[6][7];
		for(int o = 0; o < t.length;o++){
			for(int p = 0; p < t[0].length; p++){
				a[o][p] = t[o][p];
			}
		}
		return a;
	}
	public int MAX(int a, int b){
		if(a > b){
			return a;
		}
			else return b;
	}
	
	public int findAction(SLLNode<int[][]> state, int v){
		int action = 0;
		for(SLLNode<int[][]> p = state.firstChild; p != null; p = p.nextBrother){
			if(v == p.value)
				action = p.getAction();
		}
		return action;
	}

}
