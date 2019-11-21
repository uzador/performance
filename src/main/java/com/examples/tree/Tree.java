package com.examples.tree;

import java.util.*;
import java.util.stream.Stream;

public class Tree<T extends Comparable<T>> {
    private T value;
    private Tree<T> left;
    private Tree<T> right;

    private Tree(T value) {
        this.value = value;
    }

    private static void visitNode(final Tree tree) {
        System.out.print(String.format("%s ", tree.value));
    }

    public static void inOrderTraversalRecursion(final Tree tree) {
        if (tree != null) {
            inOrderTraversalRecursion(tree.left);
            visitNode(tree);
            inOrderTraversalRecursion(tree.right);
        }
    }

    public static void preOrderTraversalRecursion(final Tree tree) {
        if (tree != null) {
            visitNode(tree);
            preOrderTraversalRecursion(tree.left);
            preOrderTraversalRecursion(tree.right);
        }
    }

    public static void postOrderTraversalRecursion(final Tree tree) {
        if (tree != null) {
            postOrderTraversalRecursion(tree.left);
            postOrderTraversalRecursion(tree.right);
            visitNode(tree);
        }
    }

    private static int getHeight(final Tree root) {
        if (root == null) {
            return -1;
        }

        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    public static boolean isBalanced(final Tree root) {
        if (root == null) {
            return true;
        }

        int heightDiff = getHeight(root.left) - getHeight(root.right);
        if (Math.abs(heightDiff) > 1) {
            return false;
        } else {
            return isBalanced(root.left) && isBalanced(root.right);
        }
    }

    public static void main(String[] args) {
        final Tree<Integer> tree = new Tree<>(100);
        tree.add(10);
        tree.add(110);
        tree.add(20);
        tree.add(5);
        tree.add(105);
        tree.add(120);

        inOrderTraversalRecursion(tree);
        System.out.println("--------------");
        preOrderTraversalRecursion(tree);
        System.out.println("--------------");
        postOrderTraversalRecursion(tree);
        System.out.println("--------------");

        Stream.of(5, 110, 100, 10, 300).forEach(val -> System.out.println(String.format("%s => %s", val, tree.exists(val))));

        System.out.println("--------------");
        tree.levelOrderTraversal();

        System.out.println("\n--------------");
        tree.preOrderTraversalIterative();
        System.out.println("\n--------------");
        tree.inOrderTraversalIterative();
        System.out.println("\n--------------");
        final int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        final Tree n = tree.createMinimalBST(array);

        System.out.println("\n ----------- LinkedList ------------------");
        System.out.println(tree.createLevelLinkedList(tree));

        System.out.println("\n ----------- LinkedListIterative ------------------");
        System.out.println(tree.createLevelLinkedListIterative(tree));

        System.out.println("\n ----------- isBalanced ------------------");
        System.out.println(isBalanced(tree));
    }

    public void add(T value) {
        if (value.compareTo(this.value) < 0) {
            if (left == null) {
                left = new Tree<>(value);
            } else {
                left.add(value);
            }
        } else {
            if (right == null) {
                right = new Tree<>(value);
            } else {
                right.add(value);
            }
        }
    }

    public boolean isEmpty() {
        return value == null;
    }

    public boolean exists(T value) {
        if (value == null) {
            return false;
        } else {
            if (value.compareTo(this.value) < 0) {
                if (left == null) {
                    return false;
                } else {
                    return left.exists(value);
                }
            } else if (value.compareTo(this.value) > 0) {
                if (right == null) {
                    return false;
                } else {
                    return right.exists(value);
                }
            } else {
                return true;
            }
        }
    }

    public void levelOrderTraversal() {
        final Queue<Tree<T>> queue = new LinkedList<>();
        Tree<T> top = this;
        do {
            visitNode(top);

            if (top.left != null) {
                queue.add(top.left);
            }

            if (top.right != null) {
                queue.add(top.right);
            }

            if (!queue.isEmpty()) {
                top = queue.poll();
            }
        } while (!queue.isEmpty());
    }

    public void preOrderTraversalIterative() {
        final Stack<Tree<T>> stack = new Stack<>();
        Tree<T> top = this;

        while (top != null || !stack.isEmpty()) {
            if (!stack.isEmpty()) {
                top = stack.pop();
            }

            while (top != null) {
                visitNode(top);
                if (top.right != null) {
                    stack.push(top.right);
                }

                top = top.left;
            }
        }
    }

    public void inOrderTraversalIterative() {
        final Stack<Tree<T>> stack = new Stack<>();
        Tree<T> top = this;

        while (top != null || !stack.isEmpty()) {
            if (!stack.isEmpty()) {
                top = stack.pop();
                visitNode(top);
                if (top.right != null) {
                    top = top.right;
                } else {
                    top = null;
                }
            }

            while (top != null) {
                stack.push(top);
                top = top.left;
            }
        }
    }

    public static<T extends Comparable<T>> List<List<T>> createLevelLinkedList(final Tree<T> root) {
        final List<List<T>> lists = new ArrayList<>();
        createLevelLinkedList(root, lists, 0);

        return lists;
    }

    private static<T extends Comparable<T>> void createLevelLinkedList(final Tree<T> root,
                                                                      final List<List<T>> lists,
                                                                      final int level) {
        if (root == null) return;

        List<T> list;
        if (lists.size() == level) {
            list = new LinkedList<>();
            lists.add(list);
        } else {
            list = lists.get(level);
        }

        list.add(root.value);

        createLevelLinkedList(root.left, lists, level + 1);
        createLevelLinkedList(root.right, lists, level + 1);
    }

    public static List<List<Tree>> createLevelLinkedListIterative(final Tree root) {
            final List<List<Tree>> result = new ArrayList<>();

        List<Tree> current = new LinkedList<>();
        if (root != null) {
            current.add(root);
        }

        while (current.size() > 0) {
            result.add(current);
            List<Tree> parents = current;
            current = new LinkedList<>();
            for (Tree parent : parents) {
                if (parent.left != null) {
                    current.add(parent.left);
                }
                if (parent.right != null) {
                    current.add(parent.right);
                }
            }
        }

        return result;
    }

    Tree createMinimalBST(final int[] array) {
        return createMinimalBST(array, 0, array.length - 1);
    }

    Tree createMinimalBST(final int[] array, int start, int end) {
        if (end < start) {
            return null;
        }

        final int mid = (start + end) / 2;
        final Tree n = new Tree(array[mid]);
        n.left = createMinimalBST(array, start, mid - 1);
        n.right = createMinimalBST(array, mid + 1, end);
        return n;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
