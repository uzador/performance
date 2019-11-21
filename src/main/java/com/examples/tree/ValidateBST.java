package com.examples.tree;

import java.util.ArrayList;
import java.util.List;

public class ValidateBST<T extends Comparable<T>> {
    TreeNode<T> root;

    public void add(T value) {
        final TreeNode<T> n = new TreeNode<>(value);

        if (root == null) {
            root = n;
        } else {
            add(root, n);
        }
    }

    public boolean isValidBST() {
        final List<T> list = new ArrayList<>();
        copyBST(root, list);
        System.out.println("list: " + list);

        for(int i = 1; i < list.size(); i++) {
            if (list.get(i).compareTo(list.get(i - 1)) <= 0 ) {
                return false;
            }
        }

        return true;
    }

    private void copyBST(TreeNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }

        copyBST(node.left, list);
        list.add(node.value);
        copyBST(node.right, list);
    }

    private void add(TreeNode<T> node, TreeNode<T> n) {
        if (n.value.compareTo(node.value) < 1) {
            if (node.left == null) {
                node.left = n;
            } else {
                add(node.left, n);
            }
        } else {
            if (node.right == null) {
                node.right = n;
            } else {
                add(node.right, n);
            }
        }
    }

    private class TreeNode<T> {
        final T value;
        TreeNode<T> right;
        TreeNode<T> left;

        public TreeNode(T value) {
            this.value = value;
        }

        public void show() {
            System.out.print(this.value.toString() + " ");
            if (this.left != null) this.left.show();
            if (this.right != null) this.right.show();
        }
    }

    public static void main(String[] args) {
        final ValidateBST<Integer> tree = new ValidateBST<>();
        tree.add(100);
        tree.add(80);
        tree.add(120);
        tree.add(70);
        tree.add(90);
        tree.add(110);
        tree.add(130);

        tree.root.show();

        System.out.println();
        System.out.println("Valid: " + tree.isValidBST());
    }
}
