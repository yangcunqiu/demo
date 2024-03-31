package com.cqyang.demo.jvm.linked;

public class Node {
    int val;
    Node next;
    Node() {}
    Node(int val) { this.val = val; }
}

class NodeUtil {

    public static Node sub(Node l1, Node l2) {
        long num1 = nodeToNumber(l1);
        long num2 = nodeToNumber(l2);
        return numberToNode(num1 - num2);
    }

    private static long nodeToNumber(Node node) {
        long number = 0;
        while (node != null) {
            number = number * 10 + node.val;
            node = node.next;
        }
        return number;
    }

    private static Node numberToNode(long number) {
        Node node = new Node(0);
        if (number == 0) {
            return new Node(0);
        }
        Node current = node;
        while (number > 0) {
            current.next = new Node((int) (number % 10));
            number /= 10;
            current = current.next;
        }
        return reverseList(node.next);
    }

    private static Node reverseList(Node head) {
        Node pre = null;
        Node current = head;
        while (current != null) {
            Node nextTemp = current.next;
            current.next = pre;
            pre = current;
            current = nextTemp;
        }
        return pre;
    }

}
