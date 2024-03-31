package com.cqyang.demo.jvm.linked;

import org.junit.Test;

public class NodeTest {

    @Test
    public void test() {
        Node l1 = new Node(9);
        l1.next = new Node(8);
        l1.next.next = new Node(7);

        Node l2 = new Node(5);
        l2.next = new Node(1);
        l2.next.next = new Node(2);

        Node sub = NodeUtil.sub(l1, l2);
        while (sub != null) {
            System.out.print(sub.val);
            sub = sub.next;
        }
    }
}
