package customizedlinkedlist;


public class SLLTree<T> {
	
    private SLLNode<T> root;
    int height;
    public SLLTree(){
    	root = null;
    	height = -1;
    }
    boolean isEmpty(){
    	return(root == null);
    }
    boolean isLeaf(SLLNode<T> node){
    	if(node == null)
    		return false;
    		else
    			return (node.firstChild == null);
    }
    public void setRoot(SLLNode<T> n){
    	root = n;
    }
    public SLLNode<T> setChild(SLLNode<T> n, T el, int v){
    	if(n.firstChild == null){
    		n.firstChild = new SLLNode<T>(el);
    		n.firstChild.depth = n.depth + 1;
    	}
    	else{
    		insertSortedChild(n,  el, v);
    	}
    	SLLNode<T> p;
    	for( p = n.firstChild; (p.nextBrother != null); p = p.nextBrother);
    	return p;
    }
    public SLLNode<T> setChild(SLLNode<T> n, SLLNode<T> el, int v){
    	if(n.firstChild == null){
    		n.firstChild = el;
    		n.firstChild.depth = n.depth + 1;
    	}
    	else{
    		insertSortedChild(n,  el, v);
    	}
    	SLLNode<T> p;
    	for( p = n.firstChild; (p.nextBrother != null); p = p.nextBrother);
    	return p;
    }
    public void insertSortedChild(SLLNode<T> n, T el, int v){
    	SLLNode<T> p;
    	for( p = n.firstChild; (p.nextBrother != null && p.nextBrother.value >= v); p = p.nextBrother);
    	SLLNode<T> a = new SLLNode<T>(el,null,p.nextBrother);
    	p.nextBrother = a;
    	a.depth = n.firstChild.depth;
    }
    public void insertSortedChild(SLLNode<T> n, SLLNode<T> el, int v){
    	SLLNode<T> p;
    	for( p = n.firstChild; (p.nextBrother != null && p.nextBrother.value >= v); p = p.nextBrother);
    	el.nextBrother = p.nextBrother;
    	p.nextBrother = el;
    	el.depth = n.firstChild.depth;
    }
    public String toString(){
        if (root == null)
            return "ht=undefined";
        return ("ht="+height+"["+root.toString()+"]");
    }
}