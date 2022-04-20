package com.resolvix.lib.utils;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;

public class MapUtilsUT {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private enum HttpStatus {

        OK(200, "OK"),

        CREATED(201, "Created"),

        NO_CONTENT(204, "No content")

        ;

        private int statusCode;

        private String reason;

        HttpStatus(int statusCode, String reason) {
            this.statusCode = statusCode;
            this.reason = reason;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getReason() {
            return reason;
        }
    }

    @Before
    public void before() {
        //
    }

    @Test
    public void toMap() {
        assertThat(
            MapUtils.toMap(HttpStatus.values(), HttpStatus::getStatusCode),
            allOf(
                hasEntry(200, HttpStatus.OK),
                hasEntry(201, HttpStatus.CREATED),
                hasEntry(204, HttpStatus.NO_CONTENT)
            ));
    }
}
