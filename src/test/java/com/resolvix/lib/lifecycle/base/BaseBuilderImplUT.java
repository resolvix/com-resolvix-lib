package com.resolvix.lib.lifecycle.base;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class BaseBuilderImplUT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static class LocalBuilder
        extends BaseBuilderImpl<LocalBuilder, String> {

        private String s;

        LocalBuilder() {
            this.s = "";
        }

        @Override
        protected LocalBuilder self() {
            return this;
        }

        @Override
        public String build() {
            return s;
        }

        private LocalBuilder append(String s) {
            this.s += s;
            return self();
        }
    }

    @Test
    public void testBuilderOnTrueWhenTrue() {
        LocalBuilder lb = new LocalBuilder();
        String s = lb.append("before")
            .on(true, b -> b.append(" then"))
            .append(" after")
            .build();
        assertThat(s, equalTo("before then after"));
    }

    @Test
    public void testBuilderOnTrueWhenFalse() {
        LocalBuilder lb = new LocalBuilder();
        String s = lb.append("before")
            .on(false, b -> b.append(" then"))
            .append(" after")
            .build();
        assertThat(s, equalTo("before after"));
    }

    @Test
    public void testBuilderOnTrueOnFalseWhenTrue() {
        LocalBuilder lb = new LocalBuilder();
        String s = lb.append("before")
            .on(true, b -> b.append(" then"), b-> b.append(" and"))
            .append(" after")
            .build();
        assertThat(s, equalTo("before then after"));
    }

    @Test
    public void testBuilderOnTrueOnFalseWhenFalse() {
        LocalBuilder lb = new LocalBuilder();
        String s = lb.append("before")
            .on(false, b -> b.append(" then"), b -> b.append(" and"))
            .append(" after")
            .build();
        assertThat(s, equalTo("before and after"));
    }
}
