package com.resolvix.lib.dependency.impl;

import com.resolvix.lib.dependency.api.CyclicDependencyException;
import com.resolvix.lib.dependency.api.DependencyNotFoundException;
import com.resolvix.lib.utils.MapUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenericDependencyResolver {

    private GenericDependencyResolver() {
        // Private constructor to prevent instantiation
    }

    /**
     * Internal class that keeps track of an object and its dependencies
     */
    private static class Node<K, T> {
        private final K key;
        private final T value;
        private final Set<K> directDependencies;
        private Set<K> allDependencies; // All transitive dependencies
        private boolean visited;
        private boolean beingVisited;

        public Node(K key, T value, Set<K> directDependencies) {
            this.key = key;
            this.value = value;
            this.directDependencies = directDependencies;
            this.allDependencies = new HashSet<>();
            this.visited = false;
            this.beingVisited = false;
        }

        public K getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }

        public Set<K> getDirectDependencies() {
            return directDependencies;
        }

        public Set<K> getAllDependencies() {
            return allDependencies;
        }

        public void setAllDependencies(Set<K> allDependencies) {
            this.allDependencies = allDependencies;
        }
    }

    /**
     * Performs topological sort on a directed graph.
     */
    private static <K, T> List<Node<K, T>> topologicalSort(Map<K, Node<K, T>> graph)
        throws CyclicDependencyException, DependencyNotFoundException {

        List<Node<K, T>> result = new ArrayList<>();
        Set<K> visited = new HashSet<>();
        Set<K> currentPath = new HashSet<>();

        // First, compute all transitive dependencies
        for (K key : graph.keySet()) {
            computeAllDependencies(key, graph, new HashSet<>());
        }

        // Then perform topological sort
        for (K key : graph.keySet()) {
            if (!visited.contains(key)) {
                topologicalSortVisit(key, graph, visited, currentPath, result);
            }
        }

        // Reverse the result to get dependencies first
        Collections.reverse(result);
        return result;
    }

    /**
     * Computes all transitive dependencies for a node
     */
    private static <K, T> Set<K> computeAllDependencies(K key, Map<K, Node<K, T>> graph, Set<K> visiting)
        throws CyclicDependencyException, DependencyNotFoundException {

        Node<K, T> node = graph.get(key);
        if (node == null) {
            throw new DependencyNotFoundException(null, key);
        }

        // Return cached result if already computed
        if (!node.getAllDependencies().isEmpty()) {
            return node.getAllDependencies();
        }

        // Check for cycles
        if (visiting.contains(key)) {
            // Find the dependency that caused the cycle
            for (K dependency : node.getDirectDependencies()) {
                if (visiting.contains(dependency)) {
                    throw new CyclicDependencyException(key, dependency);
                }
            }
            throw new CyclicDependencyException(key, null);
        }

        visiting.add(key);
        Set<K> allDependencies = new HashSet<>(node.getDirectDependencies());

        for (K dependency : node.getDirectDependencies()) {
            Node<K, T> dependencyNode = graph.get(dependency);
            if (dependencyNode == null) {
                throw new DependencyNotFoundException(key, dependency);
            }
            Set<K> transitiveDependencies = computeAllDependencies(dependency, graph, visiting);
            allDependencies.addAll(transitiveDependencies);
        }

        visiting.remove(key);
        node.setAllDependencies(allDependencies);
        return allDependencies;
    }

    /**
     * Depth-first search implementation for topological sorting
     */
    private static <K, T> void topologicalSortVisit(
        K key,
        Map<K, Node<K, T>> graph,
        Set<K> visited,
        Set<K> currentPath,
        List<Node<K, T>> result) throws CyclicDependencyException, DependencyNotFoundException {

        if (currentPath.contains(key)) {
            throw new CyclicDependencyException(key, null);
        }

        if (visited.contains(key)) {
            return;
        }

        currentPath.add(key);
        Node<K, T> node = graph.get(key);
        if (node == null) {
            throw new DependencyNotFoundException(null, key);
        }

        for (K dependency : node.getDirectDependencies()) {
            if (!visited.contains(dependency)) {
                topologicalSortVisit(dependency, graph, visited, currentPath, result);
            }
        }

        visited.add(key);
        currentPath.remove(key);
        result.add(node);
    }

    /**
     * Convert an object to a Node with its dependencies
     */
    private static <K, T> Node<K, T> toNode(
        T t,
        Function<T, K> getIdentifier,
        Function<T, K[]> getDependencies) {
        K key = getIdentifier.apply(t);
        K[] dependencies = getDependencies.apply(t);
        Set<K> directDependencies = new HashSet<>(Arrays.asList(dependencies));
        return new Node<>(key, t, directDependencies);
    }

    /**
     * Resolves dependencies among a collection of objects, returning them in topological order.
     * Objects with no dependencies, or whose dependencies have already been included in the
     * result, come first in the returned array.
     *
     * @param classT the class of objects being sorted
     * @param identifier function to extract the unique identifier from an object
     * @param directDependencies function to extract the direct dependencies of an object
     * @param ts array of objects to sort topologically
     * @return an array containing the input objects sorted in topological order
     * @throws CyclicDependencyException if a dependency cycle is detected
     * @throws DependencyNotFoundException if a referenced dependency cannot be found
     */
    @SafeVarargs
    public static <T, K> T[] resolveDependencies(
        Class<T> classT,
        Function<T, K> identifier,
        Function<T, K[]> directDependencies,
        T... ts) throws CyclicDependencyException, DependencyNotFoundException {

        // Convert each object to a Node
        Map<K, Node<K, T>> graph = new HashMap<>();
        for (T t : ts) {
            Node<K, T> node = toNode(t, identifier, directDependencies);
            graph.put(node.getKey(), node);
        }

        // Perform topological sort
        List<Node<K, T>> sortedNodes = topologicalSort(graph);

        // Convert result back to an array of T
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(classT, ts.length);

        for (int i = sortedNodes.size() - 1, j = 0; i >= 0; i--, j++) {
            result[j] = sortedNodes.get(i).getValue();
        }

        return result;
    }
}
