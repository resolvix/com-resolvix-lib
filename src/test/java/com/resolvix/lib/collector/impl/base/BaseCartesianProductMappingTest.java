package com.resolvix.lib.collector.impl.base;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.contains;

public abstract class BaseCartesianProductMappingTest {

    protected static class CartesianProduct<L, R> {

        private L l;

        private R r;

        CartesianProduct(L l, R r) {
            this.l = l;
            this.r = r;
        }

        public L getL() { return l; }

        public R getR() { return r; }
    }

    protected static <L, R> CartesianProduct<L, R> of(L l, R r) {
        return new CartesianProduct<L, R>(l, r);
    }

    protected static class OneToMany<L, R> {

        private L l;

        private List<R> listR;

        OneToMany(L l) {
            this.l = l;
            this.listR = new ArrayList<>();
        }

        OneToMany(L l, R r) {
            this(l);
            this.listR.add(r);
        }

        OneToMany(L l, List<R> listR) {
            this(l);
            this.listR.addAll(listR);
        }

        public static <L, R> OneToMany<L, R> fold(OneToMany<L,R> left, OneToMany<L, R> right) {
            assertTrue(
                    left.getL() != null
                            && right.getL() != null
                            && right.getL().equals(left.getL()));

            List<R> listR = new ArrayList<>();
            listR.addAll(left.getListR());
            listR.addAll(right.getListR());

            return new OneToMany<L, R>(
                    left.getL(),
                    listR);
        }

        public static <L, R> OneToMany<L, R> foldPartial(OneToMany<L, R> left, OneToMany<L, R> right) {
            assertTrue(
                    left.getL() != null
                    && right.getL() == null);

            left.getListR().addAll(
                    right.getListR());

            return left;
        }

        public L getL() { return l; }

        public List<R> getListR() { return listR; }
    }

    protected static <L, R> OneToMany<L, R> toOneToMany(L l, R r) {
        return new OneToMany<L, R>(l, r);
    }

    protected static <L, R> OneToMany<L, R> toOneToMany(CartesianProduct<L, R> cartesianProduct) {
        return new OneToMany<L, R>(
                cartesianProduct.getL(),
                cartesianProduct.getR()
        );
    }

    protected static <L, R> OneToMany<L, R> toPartialOneToMany(CartesianProduct<L, R> cartesianProduct) {
        return new OneToMany<L, R>(
                null,
                cartesianProduct.getR()
        );
    }

    protected static <L, R> Matcher<OneToMany<L, R>> oneToManyMatcher(String l, String... rs) {
        Matcher<OneToMany<L, R>> oneToManyMatcher = allOf(
                Matchers.hasProperty("l", equalTo(l)),
                Matchers.hasProperty("listR", contains(rs))
        );
        return oneToManyMatcher;
    }

    protected List<CartesianProduct<String, String>> cartesianProducts = Arrays.asList(
            of("a", "b"),
            of("a", "c"),
            of("a", "d"),
            of("a", "e"),
            of("a", "f"),
            of("b", "g"),
            of("b", "h"),
            of("b", "i"),
            of("b", "j"),
            of("c", "k"),
            of("c", "l"),
            of("c", "m")
    );
}
