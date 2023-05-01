package customizedlinkedlist;

import java.util.ArrayList;

public class SLLNode<T> {
	private int action;
	public String player;
    public T info;
    public int depth;
    public int value;
    public SLLNode<T> nextBrother;
    public SLLNode<T> firstChild;
    
    public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public SLLNode(T el) {
        info = el;
        nextBrother = null;
        firstChild = null;
        value = 0;
    }
    
    public SLLNode(T el, SLLNode<T> ptr1 ,SLLNode<T> ptr2) {
        info = el;
        firstChild = ptr1;
        nextBrother = ptr2;
    }
    
    public String toString() {
        String s="";
        if(this != null){
        	s = "K=" + info+" depth="+depth;
        	if(firstChild != null){
        		s += " C=[ "+ firstChild.toString();
        		if(firstChild.nextBrother != null){
        			s+= ", "+firstChild.nextBrother.toString()+" ]";
        		}
        		else{
        			s+= " ]";
        		}
        	}
        }
        
        return s;
    }
    public ArrayList<SLLNode<T>> getChildren(){
    	ArrayList<SLLNode<T>> childrens =  new ArrayList<SLLNode<T>>();
    		for(SLLNode<T> p = firstChild; p != null; p = p.nextBrother ){
    			childrens.add(p);
    		}
    	return childrens;
    }
}
