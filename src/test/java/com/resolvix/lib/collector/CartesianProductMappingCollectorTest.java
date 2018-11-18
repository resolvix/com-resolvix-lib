package com.resolvix.lib.collector;

import org.hamcrest.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class CartesianProductMappingCollectorTest {

    static class CartesianProduct<L, R> {

        private L l;

        private R r;

        CartesianProduct(L l, R r) {
            this.l = l;
            this.r = r;
        }

        public L getL() { return l; }

        public R getR() { return r; }
    }

    static <L, R> CartesianProduct<L, R> of(L l, R r) {
        return new CartesianProduct<L, R>(l, r);
    }

    static class OneToMany<L, R> {

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

        static <L, R> OneToMany<L, R> fold(OneToMany<L,R> left, OneToMany<L, R> right) {
            return null;
        }

        static <L, R> OneToMany<L, R> foldPartial(OneToMany<L, R> left, OneToMany<L, R> right) {
            return null;
        }

        L getL() { return l; }

        List<R> getListR() { return listR; }
    }

    static <L, R> OneToMany<L, R> toOneToMany(CartesianProduct<L, R> cartesianProduct) {
        return new OneToMany<L, R>(
            cartesianProduct.getL(),
            cartesianProduct.getR()
        );
    }

    static <L, R> OneToMany<L, R> toPartialOneToMany(CartesianProduct<L, R> cartesianProduct) {
        return new OneToMany<L, R>(
            null,
            cartesianProduct.getR()
        );
    }

    static <L, R> Matcher<OneToMany<L, R>> oneToManyMatcher(String l, String... rs) {
        return allOf(
            Matchers.hasProperty("L", equalTo(l)),
            Matchers.hasProperty("listR", hasItems(rs))
        );
    }

    private List<CartesianProduct<String, String>> cartesianProducts = Arrays.asList(
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

    @Before
    public void before() {

    }

    @Test
    public void acceptanceTest() {
        List<OneToMany<String, String>> oneToManies = cartesianProducts.stream()
            .collect(CartesianProductMappingCollector.asList(
                CartesianProductMappingCollectorTest::toOneToMany,
                CartesianProductMappingCollectorTest::toPartialOneToMany,
                OneToMany::fold,
                OneToMany::foldPartial
            ));

        assertThat(oneToManies, hasItems(
            oneToManyMatcher("a", "b", "c", "d", "e", "f"),
            oneToManyMatcher("b", "g", "h", "i", "j"),
            oneToManyMatcher("c", "k", "l", "m")
        ));
    }
}
