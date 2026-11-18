import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Teste automático da lista ligada da biblioteca.
 *
 * Faz milhares de operações aleatórias (adicionar, remover, ordenar, buscar)
 * e compara cada resultado com um modelo simples feito com ArrayList.
 * Também confirma que o tamanho e o apontador "fim" da lista ficam sempre certos.
 */
public class TesteCorrecao {

    static Field campoInicio, campoFim;
    static int testes = 0;
    static int falhas = 0;

    static void verificar(boolean condicao, String descricao) {
        testes++;
        if (!condicao) {
            falhas++;
            if (falhas <= 10) {
                System.out.println("FALHA: " + descricao);
            }
        }
    }

    // Lê o conteúdo da lista ligada para uma lista normal
    static List<String> conteudo(Biblioteca.ListaLivros l) throws Exception {
        List<String> res = new ArrayList<>();
        for (Biblioteca.Livro x = (Biblioteca.Livro) campoInicio.get(l); x != null; x = x.proximo) {
            res.add(x.titulo + "|" + x.autor + "|" + x.ano);
        }
        return res;
    }

    static List<String> conteudoModelo(List<String[]> m) {
        List<String> res = new ArrayList<>();
        for (String[] l : m) {
            res.add(l[0] + "|" + l[1] + "|" + l[2]);
        }
        return res;
    }

    // Conta quantos resultados uma busca mostra (a busca escreve no ecrã)
    static int contarBusca(Biblioteca.ListaLivros l, boolean porTitulo, String texto) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        int n = porTitulo ? l.buscarPorTitulo(texto) : l.buscarPorAutor(texto);
        System.setOut(original);
        return n;
    }

    public static void main(String[] args) throws Exception {
        campoInicio = Biblioteca.ListaLivros.class.getDeclaredField("inicio");
        campoFim = Biblioteca.ListaLivros.class.getDeclaredField("fim");
        campoInicio.setAccessible(true);
        campoFim.setAccessible(true);

        Random r = new Random(2026);

        for (int rodada = 0; rodada < 300; rodada++) {
            Biblioteca.ListaLivros lista = new Biblioteca.ListaLivros();
            List<String[]> modelo = new ArrayList<>();

            for (int passo = 0; passo < 80; passo++) {
                int op = r.nextInt(10);

                if (op <= 4) { // adicionar (50%)
                    String titulo = "Titulo" + r.nextInt(12);
                    String autor = "Autor" + r.nextInt(4);
                    int ano = 1990 + r.nextInt(6);
                    boolean existe = false;
                    for (String[] l : modelo) {
                        if (l[0].equalsIgnoreCase(titulo) && l[1].equalsIgnoreCase(autor)
                                && l[2].equals(String.valueOf(ano))) {
                            existe = true;
                        }
                    }
                    boolean resultado = lista.adicionar(titulo, autor, ano);
                    verificar(resultado == !existe, "adicionar devolveu valor errado");
                    if (!existe) {
                        modelo.add(new String[]{titulo, autor, String.valueOf(ano)});
                    }
                } else if (op <= 7) { // remover (30%)
                    String titulo = "titulo" + r.nextInt(12); // minúsculas de propósito
                    boolean removeu = false;
                    for (int i = 0; i < modelo.size(); i++) {
                        if (modelo.get(i)[0].equalsIgnoreCase(titulo)) {
                            modelo.remove(i);
                            removeu = true;
                            break;
                        }
                    }
                    verificar(lista.remover(titulo) == removeu, "remover devolveu valor errado");
                } else if (op == 8) { // ordenar (10%)
                    lista.ordenarPorAno();
                    modelo.sort(Comparator.comparingInt(l -> Integer.parseInt(l[2]))); // estável
                } else { // buscar (10%)
                    String t = "titulo" + r.nextInt(12);
                    int esperadoT = 0;
                    for (String[] l : modelo) {
                        if (l[0].toLowerCase().contains(t)) esperadoT++;
                    }
                    verificar(contarBusca(lista, true, t) == esperadoT, "busca por título com contagem errada");

                    String a = "autor" + r.nextInt(4);
                    int esperadoA = 0;
                    for (String[] l : modelo) {
                        if (l[1].toLowerCase().contains(a)) esperadoA++;
                    }
                    verificar(contarBusca(lista, false, a) == esperadoA, "busca por autor com contagem errada");
                }

                // depois de cada operação: conteúdo, tamanho e apontador "fim"
                verificar(conteudoModelo(modelo).equals(conteudo(lista)), "conteúdo diferente do modelo");
                verificar(lista.getTamanho() == modelo.size(), "tamanho errado");
                verificar(lista.estaVazia() == modelo.isEmpty(), "estaVazia errado");

                Biblioteca.Livro fim = (Biblioteca.Livro) campoFim.get(lista);
                if (modelo.isEmpty()) {
                    verificar(fim == null || lista.estaVazia(), "fim deveria ser nulo");
                } else {
                    String[] ultimo = modelo.get(modelo.size() - 1);
                    verificar(fim != null && fim.proximo == null
                                    && fim.titulo.equals(ultimo[0]) && fim.autor.equals(ultimo[1]),
                            "apontador fim não está no último livro");
                }
            }
        }

        System.out.println("Verificações: " + testes + " | Falhas: " + falhas);
        System.out.println(falhas == 0 ? "RESULTADO: tudo correcto." : "RESULTADO: há falhas.");
    }
}