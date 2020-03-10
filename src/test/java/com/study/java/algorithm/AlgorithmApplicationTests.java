package com.study.java.algorithm;


import com.study.java.algorithm.tree.BinarySearchTree;
import org.junit.Test;

import java.util.Objects;

public class AlgorithmApplicationTests {

    @Test
    public void testString() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        tree.add("a");
        tree.add("1");
        tree.add("2");
        tree.add("3");
        tree.add("4");
        tree.add("5");

        System.out.println("min:" + tree.min());

        System.out.println("max:" + tree.max());
    }

    @Test
    public void testVo() {
        BinarySearchTree<TestVo> tree = new BinarySearchTree<>();

        TestVo testVo = new TestVo(1, "name1");

        tree.add(testVo);

        testVo = new TestVo(1, "name1");

        tree.add(testVo);
        testVo = new TestVo(2, "name12");

        tree.add(testVo);

        testVo = new TestVo(3, "name13");

        tree.add(testVo);


        testVo =  new TestVo(4, "name14");

        tree.add(testVo);

        System.out.println("min:" + tree.min());

        System.out.println("max:" + tree.max());
        tree.remove(testVo);

        System.out.println("contains testVo :" + tree.contains(testVo));
    }

    class TestVo implements Comparable<TestVo> {


        private Integer id;

        private String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestVo testVo = (TestVo) o;
            return id.equals(testVo.id) &&
                    name.equals(testVo.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "TestVo{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        public TestVo(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        @Override
        public int compareTo(TestVo o) {
            if (this.id == o.getId())
                return 0;
            if (this.id > o.getId())
                return 1;

            return -1;
        }


    }

}
