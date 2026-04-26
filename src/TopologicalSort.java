import java.util.*;

public class TopologicalSort {

    public static class Result {
        long timeNs;
        long iterations;
        List<Integer> order;
    }

    public static Result sort(int V, List<List<Integer>> adj) {
        long iterations = 0; // Счетчик итераций алгоритма

        long startTime = System.nanoTime();

        int[] inDegree = new int[V];

        // ШАГ 1: Считаем входящие ребра для каждой вершины
        for (int u = 0; u < V; u++) {
            iterations++;
            for (int v : adj.get(u)) {
                iterations++;
                inDegree[v]++;
            }
        }

        // ШАГ 2: Добавляем в очередь вершины без зависимостей
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            iterations++;
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> topOrder = new ArrayList<>();

        // ШАГ 3: Извлекаем вершины и удаляем их связи
        while (!queue.isEmpty()) {
            iterations++;
            int u = queue.poll();
            topOrder.add(u);

            for (int v : adj.get(u)) {
                iterations++;
                if (--inDegree[v] == 0) {
                    queue.add(v);
                }
            }
        }

        long endTime = System.nanoTime();

        // ШАГ 4: Проверка графа на наличие циклов
        if (topOrder.size() != V) {
            throw new IllegalStateException("В графе обнаружен цикл! Топологическая сортировка невозможна.");
        }

        Result res = new Result();
        res.timeNs = endTime - startTime;
        res.iterations = iterations;
        res.order = topOrder;

        return res;
    }
}