package com.resolvix.lib.collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * An accumulator designed to map a value of type {@code T},
 * comprising a repeating component and a non-repeating
 * component, to a value of type {@code I} that represents
 * a maplet from a single instance of the repeating component
 * to the list non-repeating components; where the index for
 * the repeating component is of type {@code II}.
 *
 * @param <T> the type of value comprising both the repeating
 *           component, and the non-repeating component
 *
 * @param <I> the type of value representing the maplet of the
 *           repeating component, to the non-repeating
 *           component
 *
 * @param <II> the type of the index for the repeating
 *            component for the purposes of linking the
 *            non-repeatable components to the singular
 *            repeating component
 */
public class CartesianProductMappingListAccumulator<T, I, II> {

    private Function<T, I> mappingFunction;

    private Function<T, I> partialMappingFunction;

    private Function<T, II> indexMapping;

    private BinaryOperator<I> foldingFunction;

    private BinaryOperator<I>  appendingPartialFunction;

    private Map<II, I> mapI;

    public CartesianProductMappingListAccumulator(
            Function<T, I> mappingFunction,
            Function<T, I> partialMappingFunction,
            Function<T, II> indexMapping,
            BinaryOperator<I> foldingFunction,
            BinaryOperator<I> appendingPartialFunction) {
        this.mappingFunction = mappingFunction;
        this.partialMappingFunction = partialMappingFunction;
        this.indexMapping = indexMapping;
        this.foldingFunction = foldingFunction;
        this.appendingPartialFunction = appendingPartialFunction;
        this.mapI = new HashMap<>();
    }

    public void transformAndAppend(T t) {
        II ii = indexMapping.apply(t);
        I i = mapI.get(ii);
        if (i == null) {
            i = mappingFunction.apply(t);
            mapI.put(ii, i);
        } else {
            I ip = partialMappingFunction.apply(t);
            appendingPartialFunction.apply(i, ip);
        }
    }

    public CartesianProductMappingListAccumulator<T, I, II> combine(CartesianProductMappingListAccumulator<T, I, II> r) {
        Map<II, I> inMapI, outMapI;
        if (mapI.size() >= r.mapI.size()) {
            inMapI = r.mapI;
            outMapI = mapI;
        } else {
            inMapI = mapI;
            outMapI = r.mapI;
        }

        inMapI.forEach((II ii, I i) -> {
            I destI = outMapI.get(ii);
            I foldedI = foldingFunction.apply(destI, i);
            outMapI.put(ii, foldedI);
        });

        this.mapI = outMapI;
        return this;
    }

    public List<I> toList() {
        List<I> listI = new ArrayList<>();
        listI.addAll(mapI.values());
        return listI;
    }
}
