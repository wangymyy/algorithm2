package com.study.java.algorithm.tree;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Queue;
/**
 * data structure unbalanced binary search tree
 * @author michael
 * @param <E>
 */
public class BinarySearchTree<E extends Comparable<E>> {

    /**
     * 二叉树节点个数
     */
    int size = 0;

    /**
     * 根节点
     */
    Node<E> root;

    public BinarySearchTree() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 插入一个元素
     * 若根节点为空，新建一个节点作为根节点并把element(需要插入的值)赋给根节点；
     * 根节点不为空，将element与根节点的值进行比较
     *     若小于等于根节点的值，判断根节点的左子树是否为空
     *         如果为空，新建左子树并赋值；
     *         否则，递归调用比较。
     *     若大于根节点的值，判断根节点的右子树是否为空
     *         如果为空，新建右子树并赋值；
     *         否则，递归调用比较。
     * @param element 需要插入的值
     */
    public void add(E element) {
        if(root == null) {
            root = new Node<E>(null, element, null);
        } else {
            addNode(root, element);
        }
        size++;
    }

    /**
     * 插入一个元素的具体实现(递归)
     * @param current 当前节点
     * @param element 需要插入的值
     */
    private void addNode(Node<E> current, E element) {
        // assert current not null
        if(element.compareTo(current.item) <= 0) {
            if(current.left == null) {
                current.left = new Node<E>(null, element, null);
            } else {
                addNode(current.left, element);
            }
        } else {
            if(current.right == null) {
                current.right = new Node<E>(null, element, null);
            } else {
                addNode(current.right, element);
            }
        }
    }

    /**
     * 查找一个元素是否为二叉树节点值
     * 首先和根节点的值进行比较
     *     若相等，找到，返回true；
     *     不相等，再和左孩子的值进行比较
     *         如果小于等于左孩子的值，则将左孩子作为根节点递归调用。
     *         否则将右孩子作为根节点递归调用。
     * @param element 待查找的元素
     * @return 找到返回true；树为空或未找到返回false
     */
    public boolean contains(E element) {
        return containsNode(root, element);
    }

    /**
     * contains具体实现(递归)
     * @param current 当前节点
     * @param element 待查找的元素
     * @return
     */
    private boolean containsNode(Node<E> current, E element) {
        if(current == null) {
            return false;
        }

        if(element.equals(current.item)) {
            return true;
        } else if(element.compareTo(current.item) <= 0) {
            return containsNode(current.left, element);
        } else {
            return containsNode(current.right, element);
        }
    }

    /**
     * 获得指定元素的父节点
     * 如果element等于根节点的值，本身为根节点，无父节点，返回null
     * 如果element小于等于当前节点(current)的值
     *     判断当前节点的左孩子(left)是否为空
     *         空，二叉树没有element元素，返回null
     *         不空，将左孩子的值和element比较，等于返回current；否则，将left作为当前节点递归调用。
     * 否则(element大于当前节点的值)
     *     判断当前节点的右孩子(right)是否为空
     *         空，二叉树没有element元素，返回null
     *         不空，将右孩子的值和element比较，等于返回current；否则，将right作为当前节点递归调用。
     * @param current 当前节点
     * @param element 待查找元素
     * @return
     */
    private Node<E> getParent(Node<E> current, E element) {
        // assert root not null
        if(element.equals(root.item)) {
            return null;
        }

        if(element.compareTo(current.item) <= 0) {
            if(current.left == null) {
                return null;
            } else if(element.equals(current.left.item)) {
                return current;
            } else {
                return getParent(current.left, element);
            }
        } else {
            if(current.right == null) {
                return null;
            } else if(element.equals(current.right.item)) {
                return current;
            } else {
                return getParent(current.right, element);
            }
        }
    }

    /**
     * 给定元素值获得该元素节点
     * @param root 根节点
     * @param element 给定元素
     * @return 该元素节点
     */
    private Node<E> getNode(Node<E> root, E element) {
        if(root == null) {
            return null;
        }

        if(element.equals(root.item)) {
            return root;
        } else if(element.compareTo(root.item) <= 0) {
            return getNode(root.left, element);
        } else {
            return getNode(root.right, element);
        }
    }

    /**
     * 删除元素
     * 删除元素为
     *     叶子节点
     *     有右子树无左子树
     *     有左子树无右子树
     *     既有右子树又有左子树
     * @param element 待删除的元素
     */
    public void remove(E element) {
        Node<E> node = getNode(root, element);

        if(node == null) {
            throw new NoSuchElementException();
        }

        Node<E> parent = getParent(root, element);

        if(size == 1) {
            root = null;
        } else if(node.left == null && node.right == null) {
            removeLeaf(parent, node);
        } else if(node.left == null && node.right != null) {
            if(element.compareTo(parent.item) < 0) {
                parent.left = node.right;
            } else {
                parent.right = node.right;
            }
        } else if(node.left != null && node.right == null) {
            if(element.compareTo(parent.item) < 0) {
                parent.left = node.left;
            } else {
                parent.right = node.left;
            }
        } else {
            Node<E> largest = node.left;
            if(largest.right == null) {
                node.item = largest.item;
                node.left = largest.left;
                largest = null;
            } else {
                while(largest.right != null) {
                    largest = largest.right;
                }
                Node<E> largestParent = getParent(node, largest.item);
                largestParent.right = null;
                node.item = largest.item;
            }
        }
        size--;
    }

    /**
     * 删除叶子节点
     * 判断待删除的叶子节点是父节点的左孩子还是右孩子，相应的将父节点的左孩子或右孩子设为null
     * @param parent 父节点
     * @param leaf 叶子节点
     */
    private void removeLeaf(Node<E> parent, Node<E> leaf) {
        E element = leaf.item;
        if(element.compareTo(parent.item) < 0) {
            parent.left = null;
        } else {
            parent.right = null;
        }
    }

    /**
     * 先序遍历
     * 根结点-左子树-右子树
     * @param container 存储容器
     */
    public void preOrder(Collection<E> container) {
        preOrderNode(root, container);
    }

    private void preOrderNode(Node<E> root, Collection<E> container) {
        if(root != null) {
            container.add(root.item);
            preOrderNode(root.left, container);
            preOrderNode(root.right, container);
        }

    }

    /**
     * 中序遍历
     * 左子树-根结点-右子树
     * @param container 存储容器
     */
    public void inOrder(Collection<E> container) {
        inOrderNode(root, container);
    }

    private void inOrderNode(Node<E> root, Collection<E> container) {
        if(root != null) {
            inOrderNode(root.left, container);
            container.add(root.item);
            inOrderNode(root.right, container);
        }
    }

    /**
     * 后序遍历
     * 左子树-右子树-根结点
     * @param container 存储容器
     */
    public void postOrder(Collection<E> container) {
        postOrderNode(root, container);
    }

    private void postOrderNode(Node<E> root, Collection<E> container) {
        if(root != null) {
            postOrderNode(root.left, container);
            postOrderNode(root.right, container);
            container.add(root.item);
        }
    }

    /**
     * 广度优先遍历
     * 使用队列  将同一高度的节点依次入队出队。
     * @param container 存储容器
     */
    public void breadthFirst(Collection<E> container) {
        Node<E> node = root;
        Queue<Node<E>> q = new ArrayDeque<Node<E>>();
        while(node != null) {
            container.add(node.item);
            if(node.left != null) {
                q.add(node.left);
            }
            if(node.right != null) {
                q.add(node.right);
            }
            if(!q.isEmpty()) {
                node = q.poll();
            } else {
                node = null;
            }
        }
    }

    /**
     * 获取二叉查找树的大小
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * 最小值
     * @return 二叉查找树最小值
     */
    public E min() {
        return getMin(root);
    }

    /**
     * 获取最小值
     * 根据性质，最小值为二叉查找树最左边的节点值
     * @param root 根结点
     * @return 最小值
     */
    public E getMin(Node<E> root) {
        // assert root not null
        if(root.left == null) {
            return root.item;
        } else {
            return getMin(root.left);
        }
    }

    /**
     * 最大值
     * @return 二叉查找树最大值
     */
    public E max() {
        return getMax(root);
    }

    /**
     * 获取最大值
     * 根据性质，最大值为二叉查找树最右边的节点值
     * @param root
     * @return 最大值
     */
    public E getMax(Node<E> root) {
        // assert root not null
        if(root.right == null) {
            return root.item;
        } else {
            return getMax(root.right);
        }
    }

    /**
     * 内部类 表示树节点
     * @author michael
     * @param <E>
     */
    private static class Node<E> {
        E item;
        Node<E> left;
        Node<E> right;

        /**
         * 构造一个新节点并指定左孩子和右孩子
         * @param left 左孩子
         * @param item 节点值
         * @param right 右孩子
         */
        Node(Node<E> left, E item, Node<E> right) {
            this.left = left;
            this.item = item;
            this.right = right;
        }
    }


    public static void main(String[] args) {
        BinarySearchTree<String> tree = new BinarySearchTree<>();

        tree.add("1234");
        tree.add("4567");

        tree.add("1");

        System.out.println( tree.min());

        System.out.println( tree.max());
    }

}
