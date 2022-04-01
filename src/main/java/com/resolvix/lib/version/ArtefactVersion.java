package com.resolvix.lib.version;

import org.checkerframework.checker.units.qual.A;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArtefactVersion
    implements Comparable<ArtefactVersion>
{
    private static final String ARTEFACT_VERSION_REGEX = "^([0-9]+)(?:\\.([0-9]+))?(?:\\.([0-9]+))?(?:-([a-zA-Z]+))?(?:-([0-9]+))?$";

    private static final Pattern ARTEFACT_VERSION_PATTERN = Pattern.compile(ARTEFACT_VERSION_REGEX);

    private final Integer majorVersion;

    private final Integer minorVersion;

    private final Integer incrementalVersion;

    private final String qualifier;

    private final Integer buildNumber;

    private ArtefactVersion(
        Integer majorVersion, Integer minorVersion, Integer incrementalVersion, String qualifier, Integer buildNumber) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.incrementalVersion = incrementalVersion;
        this.qualifier = qualifier;
        this.buildNumber = buildNumber;
    }

    public static ArtefactVersion of(
        int majorVersion, int minorVersion, int incrementalVersion, String qualifier, int buildNumber) {
        return new ArtefactVersion(
            majorVersion, minorVersion, incrementalVersion, qualifier, buildNumber);
    }

    public static ArtefactVersion of(
        int majorVersion, int minorVersion, int incrementalVersion, String qualifier) {
        return new ArtefactVersion(
            majorVersion, minorVersion, incrementalVersion, qualifier, null);
    }

    public static ArtefactVersion of(
        int majorVersion, int minorVersion, int incrementalVersion) {
        return new ArtefactVersion(
            majorVersion, minorVersion, incrementalVersion, null, null);
    }

    public static ArtefactVersion of(
        int majorVersion, int minorVersion) {
        return new ArtefactVersion(
            majorVersion, minorVersion, null, null, null);
    }

    public static ArtefactVersion of(int majorVersion) {
        return new ArtefactVersion(
            majorVersion, null, null, null, null);
    }

    public static ArtefactVersion parse(String artefactVersion) {
        Matcher matcher = ARTEFACT_VERSION_PATTERN.matcher(artefactVersion);
        if (!matcher.matches())
            throw new IllegalArgumentException();

        String scratchMajorVersion = matcher.group(1);
        Integer intMajorVersion = scratchMajorVersion != null ? Integer.parseInt(scratchMajorVersion) : null;

        String scratchMinorVersion = matcher.group(2);
        Integer intMinorVersion = scratchMinorVersion != null ? Integer.parseInt(scratchMinorVersion) : null;

        String scratchIncrementalVersion = matcher.group(3);
        Integer intIncrementalVersion = scratchIncrementalVersion != null ? Integer.parseInt(scratchIncrementalVersion) : null;

        String strQualifier = matcher.group(4);

        String scratchBuildNumber = matcher.group(5);
        Integer intBuildNumber = scratchBuildNumber != null ? Integer.parseInt(scratchBuildNumber) : null;

        return new ArtefactVersion(
            intMajorVersion, intMinorVersion, intIncrementalVersion, strQualifier, intBuildNumber);
    }

    public int getMajorVersion() {
        return majorVersion != null ? majorVersion : 0;
    }

    public int getMinorVersion() {
        return minorVersion != null ? minorVersion : 0;
    }

    public int getIncrementalVersion() {
        return incrementalVersion != null ? incrementalVersion : 0;
    }

    public String getQualifier() {
        return qualifier;
    }

    public int getBuildNumber() {
        return buildNumber != null ? buildNumber : 0;
    }

    @Override
    public int compareTo(ArtefactVersion o) {
        if (o == null)
            throw new NullPointerException();

        int leftMajorVersion = getMajorVersion();
        int rightMajorVersion = o.getMajorVersion();
        if (leftMajorVersion > rightMajorVersion)
            return 1;

        if (leftMajorVersion < rightMajorVersion)
            return -1;

        int leftMinorVersion = getMinorVersion();
        int rightMinorVersion = o.getMinorVersion();
        if (leftMinorVersion > rightMinorVersion)
            return 1;

        if (leftMinorVersion < rightMinorVersion)
            return -1;

        int leftIncrementalVersion = getIncrementalVersion();
        int rightIncrementalVersion = o.getIncrementalVersion();
        if (leftIncrementalVersion > rightIncrementalVersion)
            return 1;

        if (leftIncrementalVersion < rightIncrementalVersion)
            return -1;

        String leftQualifier = getQualifier();
        String rightQualifier = o.getQualifier();
        if (leftQualifier == null && rightQualifier != null)
            return 1;

        if (leftQualifier != null && rightQualifier == null)
            return -1;

        if (leftQualifier != null) {
            int result = leftQualifier.compareTo(rightQualifier);
            if (result != 0)
                return result;
        }

        int leftBuildNumber = getBuildNumber();
        int rightBuildNumber = o.getBuildNumber();
        if (leftBuildNumber > rightBuildNumber)
            return 1;

        if (leftBuildNumber < rightBuildNumber)
            return -1;

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof ArtefactVersion))
            return false;

        ArtefactVersion v = (ArtefactVersion) o;
        return (compareTo(v) == 0);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (majorVersion != null) {
            sb.append(majorVersion);
        } else if (minorVersion != null && incrementalVersion != null) {
            sb.append("0");
        }
        if (minorVersion != null) {
            sb.append('.');
            sb.append(minorVersion);
        } else if (incrementalVersion != null) {
            sb.append(".0");
        }
        if (incrementalVersion != null) {
            sb.append('.');
            sb.append(incrementalVersion);
        }
        if (qualifier != null) {
            sb.append('-');
            sb.append(qualifier);
        }
        if (buildNumber != null) {
            sb.append('-');
            sb.append(buildNumber);
        }
        return sb.toString();
    }
}
