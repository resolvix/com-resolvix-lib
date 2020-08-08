package com.resolvix.lib.reference;

import org.junit.Before;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class WeakReferenceUtilsUT {

    private static final BigDecimal ONE = new BigDecimal(1);

    private static final BigDecimal TWO = new BigDecimal(2);

    private static final BigDecimal THREE = new BigDecimal(3);

    private static final BigDecimal FOUR = new BigDecimal(4);

    private static final BigDecimal FIVE = new BigDecimal(5);

    private static final BigDecimal SIX = new BigDecimal(6);

    private static final BigDecimal SEVEN = new BigDecimal(7);

    private static final BigDecimal EIGHT = new BigDecimal(8);

    private static final BigDecimal NINE = new BigDecimal(9);

    private static final WeakReference<BigDecimal> WEAK_REFERENCE_ONE = new WeakReference<>(ONE);

    @SuppressWarnings("unused")
    private static final WeakReference<BigDecimal> WEAK_REFERENCE_TWO = new WeakReference<>(TWO);

    private static final WeakReference<BigDecimal> WEAK_REFERENCE_THREE = new WeakReference<>(THREE);

    @SuppressWarnings("unused")
    private static final WeakReference<BigDecimal> WEAK_REFERENCE_FOUR = new WeakReference<>(FOUR);

    private static final WeakReference<BigDecimal> WEAK_REFERENCE_FIVE = new WeakReference<>(FIVE);

    @SuppressWarnings("unused")
    private static final WeakReference<BigDecimal> WEAK_REFERENCE_SIX = new WeakReference<>(SIX);

    private static final WeakReference<BigDecimal> WEAK_REFERENCE_SEVEN = new WeakReference<>(SEVEN);

    @SuppressWarnings("unused")
    private static final WeakReference<BigDecimal> WEAK_REFERENCE_EIGHT = new WeakReference<>(EIGHT);

    private static final WeakReference<BigDecimal> WEAK_REFERENCE_NINE = new WeakReference<>(NINE);

    private List<WeakReference<BigDecimal>> weakReferences;

    @Before
    public void before() {
        this.weakReferences = new ArrayList<>();
        weakReferences.add(WEAK_REFERENCE_ONE);
        weakReferences.add(WEAK_REFERENCE_THREE);
        weakReferences.add(WEAK_REFERENCE_FIVE);
        weakReferences.add(WEAK_REFERENCE_SEVEN);
        weakReferences.add(WEAK_REFERENCE_NINE);
    }

    //
    //  find
    //

    @Test
    public void findOne() {
        assertThat(
            WeakReferenceUtils.find(weakReferences, ONE),
            sameInstance(WEAK_REFERENCE_ONE));
    }

    @Test
    public void findSeven() {
        assertThat(
            WeakReferenceUtils.find(weakReferences, SEVEN),
            sameInstance(WEAK_REFERENCE_SEVEN));
    }

    @Test
    public void findNine() {
        assertThat(
            WeakReferenceUtils.find(weakReferences, NINE),
            sameInstance(WEAK_REFERENCE_NINE));
    }

    //
    //  foreach
    //

    private <T, U> List<U> toList(List<WeakReference<T>> weakReferences, Function<T, U> functionTU) {
        List<U> us = new ArrayList<>(weakReferences.size());
        WeakReferenceUtils.foreach(
            weakReferences,
            (T t) -> us.add(functionTU.apply(t)));
        return us;
    }

    @Test
    public void foreach() {
        assertThat(
            toList(weakReferences, BigDecimal::longValue),
            contains(1L, 3L, 5L, 7L, 9L));
    }

    //
    //  compact
    //

    @Test
    public void compact() {
        assertThat(
            toList(weakReferences, BigDecimal::longValue),
            contains(1L, 3L, 5L, 7L, 9L));
        WEAK_REFERENCE_FIVE.clear();
        assertThat(
            toList(WeakReferenceUtils.compact(weakReferences), BigDecimal::longValue),
            contains(1L, 3L, 7L, 9L));
        WEAK_REFERENCE_ONE.clear();
        assertThat(
            toList(WeakReferenceUtils.compact(weakReferences), BigDecimal::longValue),
            contains(3L, 7L, 9L));
        WEAK_REFERENCE_NINE.clear();
        assertThat(
            toList(WeakReferenceUtils.compact(weakReferences), BigDecimal::longValue),
            contains(3L, 7L));
    }
}
