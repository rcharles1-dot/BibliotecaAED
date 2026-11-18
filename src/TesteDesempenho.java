import java.util.Random;

/**
 * Teste simples de desempenho para a análise de complexidade.
 * Mede o tempo da busca linear e da ordenação (insertion sort)
 * com listas de tamanhos diferentes.
 */
public class TesteDesempenho {

    static Biblioteca.ListaLivros criarLista(int n, boolean ordenada, Random r) {
        Biblioteca.ListaLivros lista = new Biblioteca.ListaLivros();
        for (int i = 0; i < n; i++) {
            int ano = ordenada ? 1000 + i : 1000 + r.nextInt(1100);
            lista.adicionar("Livro " + i, "Autor " + (i % 50), ano);
        }
        return lista;
    }

    public static void main(String[] args) {
        int[] tamanhos = {1000, 2000, 4000, 8000, 16000};
        Random r = new Random(42);

        System.out.println("n;ordenar_aleatoria_ms;ordenar_ja_ordenada_ms;busca_linear_ms");
        for (int n : tamanhos) {
            // ordenação com dados aleatórios (caso médio/pior)
            Biblioteca.ListaLivros a = criarLista(n, false, r);
            long t1 = System.nanoTime();
            a.ordenarPorAno();
            double msAleatoria = (System.nanoTime() - t1) / 1e6;

            // ordenação com dados já ordenados (melhor caso)
            Biblioteca.ListaLivros b = criarLista(n, true, r);
            long t2 = System.nanoTime();
            b.ordenarPorAno();
            double msOrdenada = (System.nanoTime() - t2) / 1e6;

            // busca linear de um autor que existe, repetida 200 vezes
            // (a saída é desviada para não encher o ecrã)
            java.io.PrintStream original = System.out;
            System.setOut(new java.io.PrintStream(new java.io.OutputStream() {
                public void write(int x) { }
            }));
            long t3 = System.nanoTime();
            for (int k = 0; k < 200; k++) {
                a.buscarPorTitulo("Livro " + (n - 1));
            }
            double msBusca = (System.nanoTime() - t3) / 1e6 / 200;
            System.setOut(original);

            System.out.printf("%d;%.2f;%.2f;%.3f%n", n, msAleatoria, msOrdenada, msBusca);
        }
    }
}
