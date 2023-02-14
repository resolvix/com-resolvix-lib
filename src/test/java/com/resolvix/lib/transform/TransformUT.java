package com.resolvix.lib.transform;

import org.junit.Test;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThrows;

public class TransformUT {

    private static enum InputValues {

        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE;

    }

    private static enum OutputValues {

        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN;

    }

    private static class PlusFiveTransform
        implements Transform<InputValues, OutputValues> {

        @Override
        public OutputValues transform(InputValues input) {
            if (input == null)
                return null;

            switch (input) {
                case ONE:
                    return OutputValues.SIX;

                case TWO:
                    return OutputValues.SEVEN;

                case THREE:
                    return OutputValues.EIGHT;

                case FOUR:
                    return OutputValues.NINE;

                default:
                    return null;
            }
        }
    }

    private static final PlusFiveTransform PLUS_FIVE_TRANSFORM
        = new PlusFiveTransform();

    @Test
    public void transformGivenTransformableValue() {
        assertThat(
            PLUS_FIVE_TRANSFORM.transform(InputValues.ONE),
            equalTo(OutputValues.SIX));
    }

    @Test
    public void transformGivenNonTransformableValue() {
        assertThat(
            PLUS_FIVE_TRANSFORM.transform(InputValues.FIVE),
            nullValue());
    }

    @Test
    public void transformOrElseGivenTransformableValue() {
        assertThat(
            PLUS_FIVE_TRANSFORM.transformOrElse(InputValues.TWO, OutputValues.TEN),
            equalTo(OutputValues.SEVEN));
    }

    @Test
    public void transformOrElseGivenNonTransformableValue() {
        assertThat(
            PLUS_FIVE_TRANSFORM.transformOrElse(InputValues.FIVE, OutputValues.TEN),
            equalTo(OutputValues.TEN));
    }

    private IllegalArgumentException toIllegalArgumentException(InputValues input) {
        return new IllegalArgumentException(
            input.toString());
    }

    @Test
    public void transformOrThrowGivenTransformableValue() {
        assertThat(
            PLUS_FIVE_TRANSFORM.transformOrThrow(InputValues.THREE, this::toIllegalArgumentException),
            equalTo(OutputValues.EIGHT));
    }

    @Test
    public void transformOrThrowGivenNonTransformableValue() {
        IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> PLUS_FIVE_TRANSFORM.transformOrThrow(InputValues.FIVE, this::toIllegalArgumentException));
        assertThat(
            thrown.getMessage(),
            equalTo(InputValues.FIVE.toString()));
    }

    @Test
    public void transformToOptionalGivenTransformableValue() {
        assertThat(
            PLUS_FIVE_TRANSFORM.transformToOptional(InputValues.FOUR),
            is(optionalWithValue(OutputValues.NINE)));
    }

    @Test
    public void transformToOptionalGivenNonTransformableValue() {
        assertThat(
            PLUS_FIVE_TRANSFORM.transformToOptional(InputValues.FIVE),
            is(emptyOptional()));
    }
}
