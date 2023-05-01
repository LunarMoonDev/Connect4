package customizedlinkedlist;

import java.util.*;

public class SLLTest {

    static Scanner Scan = new Scanner(System.in);

    public static void main(String[] args) {
        SLLTree<String> t = new SLLTree<String>();
        SLLNode<String> a1 = new SLLNode<String>("A");
        t.setRoot(a1);
        SLLNode<String> a2 = t.setChild(a1, "B", 0);
        SLLNode<String> a3 = t.setChild(a1, "D", 1);
        SLLNode<String> a4 = t.setChild(a2, "C", 0);
        SLLNode<String> a5 = t.setChild(a3, "E", 0);
        SLLNode<String> a6 = t.setChild(a3, "H", 1);
        SLLNode<String> a7 = t.setChild(a5, "F", 0);
        SLLNode<String> a8 = t.setChild(a5, "G", 1);
        SLLNode<String> a9 = t.setChild(a6, "I", 1);
        System.out.println(a1.toString());
        
        
        ArrayList<SLLNode<String>> a = a1.getChildren();
        SLLNode<String> left = a.get(0);
        System.out.println(left.info);
    }
}
