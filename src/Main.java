import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        GraphGenerator.generate();
        File dir = new File("data");
        File[] files = dir.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("Папка data пуста!");
            return;
        }

        try (PrintWriter csvWriter = new PrintWriter("results.csv")) {
            csvWriter.println("Vertices,Edges,Total_Size,Iterations,Time_ms");

            for (File file : files) {
                Scanner sc = new Scanner(file);
                int V = sc.nextInt(); // количество вершин
                int E = sc.nextInt(); // количество ребер

                // Инициализируем список смежности для графа
                List<List<Integer>> adj = new ArrayList<>();
                for (int i = 0; i < V; i++) {
                    adj.add(new ArrayList<>());
                }

                for (int i = 0; i < E; i++) {
                    int u = sc.nextInt();
                    int v = sc.nextInt();
                    adj.get(u).add(v);
                }
                sc.close();


                for (int i = 0; i < 5; i++) {
                    TopologicalSort.sort(V, adj);
                }


                int runs = 10;
                long totalTime = 0;
                long iterations = 0;

                for (int i = 0; i < runs; i++) {
                    TopologicalSort.Result res = TopologicalSort.sort(V, adj);
                    totalTime += res.timeNs;
                    iterations = res.iterations; // Одинаково, можно не суммировать
                }

                double timeMs = (totalTime / (double) runs) / 1_000_000.0;
                int totalSize = V + E;

                csvWriter.println(
                        V + "," + E + "," + totalSize + "," + iterations + "," + timeMs
                );

                System.out.println("Обработан: " + file.getName());
            }
        }

        System.out.println("Готово → results.csv");
    }
}