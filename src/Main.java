import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

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

                try {
                    TopologicalSort.Result res = TopologicalSort.sort(V, adj);

                    double timeMs = res.timeNs / 1_000_000.0;
                    int totalSize = V + E; // Общий размер входных данных

                    csvWriter.println(V + "," + E + "," + totalSize + "," + res.iterations + "," + timeMs);
                    System.out.println("Успешно обработан: " + file.getName());

                } catch (IllegalStateException e) {
                    System.err.println("Ошибка в файле " + file.getName() + ": " + e.getMessage());
                }
            }
        }
        System.out.println("Все тесты завершены. Результаты сохранены в results.csv");
    }
}