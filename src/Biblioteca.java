import java.util.Scanner;

public class Biblioteca {
    //Cada livro é um nó da lista ligada
    static class Livro {
        String titulo;
        String autor;
        int ano;
        Livro proximo; // ligação para o próximo livro

        Livro(String titulo, String autor, int ano) {
            this.titulo = titulo;
            this.autor = autor;
            this.ano = ano;
            this.proximo = null;
        }

        @Override
        public String toString() {
            return "\"" + titulo + "\" | Autor: " + autor + " | Ano: " + ano;
        }
    }

    // Lista ligada com as operações da biblioteca
    static class ListaLivros {
        private Livro inicio; // primeiro nó
        private Livro fim; // último nó (inserir no fim sem percorrer)
        private int tamanho;

        public int getTamanho() {
            return tamanho;
        }

        public boolean estaVazia() {
            return inicio == null;
        }

        //Adiciona no fim. Devolve false se o livro já existir. Complexidade: o(n)
        public boolean adicionar(String titulo, String autor, int ano) {
            Livro actual = inicio;
            while (actual != null) {// verificar duplicados
                if (actual.titulo.equalsIgnoreCase(titulo)
                        && actual.autor.equalsIgnoreCase(autor)
                        && actual.ano == ano) {
                    return false;
                }
                actual = actual.proximo;
            }

            Livro novo = new Livro(titulo, autor, ano);
            if (inicio == null) {
                inicio = novo;
                fim = novo;
            } else {
                fim.proximo = novo;
                fim = novo;
            }
            tamanho++;
            return true;
        }

        // Remove o primeiro livro com esse título. Complexidade: o(n)
        public boolean remover(String titulo) {
            Livro anterior = null;
            Livro actual = inicio;

            while (actual != null) {
                if (actual.titulo.equalsIgnoreCase(titulo)) {
                    if (anterior == null) {
                        inicio = actual.proximo; // era o primeiro
                    } else {
                        anterior.proximo = actual.proximo;
                    }
                    if (actual == fim) {
                        fim = anterior; //era o último
                    }
                    tamanho--;
                    return true;
                }
                anterior = actual;
                actual = actual.proximo;
            }
            return false;
        }

        // Busca linear por título. Complexidade: o(n)
        public int buscarPorTitulo(String texto) {
            int encontrados = 0;
            String procura = texto.toLowerCase();
            Livro actual = inicio;
            while (actual != null) {
                if (actual.titulo.toLowerCase().contains(procura)) {
                    System.out.println(" -> " + actual);
                    encontrados++;
                }
                actual = actual.proximo;
            }
            return encontrados;
        }

        // Busca linear por autor. Complexidade: o(n)
        public int buscarPorAutor(String texto) {
            int encontrados = 0;
            String procura = texto.toLowerCase();
            Livro actual = inicio;
            while (actual != null) {
                if (actual.autor.toLowerCase().contains(procura)) {
                    System.out.println(" -> " + actual);
                    encontrados++;
                }
                actual = actual.proximo;
            }
            return encontrados;
        }

        // Insertion sort para lista ligada. Pior/médio caso o(n^2), melhor caso o(n)
        public void ordenarPorAno() {
            if (inicio == null || inicio.proximo == null) {
                return; // 0 ou 1 livro: já está ordenada
            }
            Livro ordenada = null; // início da lista ordenada
            Livro ultimoOrdenado = null; // último nó da lista ordenada
            Livro actual = inicio;

            while (actual != null) {
                Livro proximoOriginal = actual.proximo; // guardar antes de mudar a ligação

                if (ordenada == null) {
                    actual.proximo = null;
                    ordenada = actual;
                    ultimoOrdenado = actual;
                } else if (actual.ano < ordenada.ano) {
                    // entra no inicio
                    actual.proximo = ordenada;
                    ordenada = actual;
                } else if (actual.ano >= ultimoOrdenado.ano) {
                    // entra no fim sem percorrer ( melhor caso o(n)
                    actual.proximo = null;
                    ultimoOrdenado.proximo = actual;
                    ultimoOrdenado = actual;
                } else {
                    // procurar a posição certa
                    Livro p = ordenada;
                    while (p.proximo != null && p.proximo.ano <= actual.ano) {
                        p = p.proximo;
                    }
                    actual.proximo = p.proximo;
                    p.proximo = actual;
                }
                actual = proximoOriginal;
            }

            inicio = ordenada;

            // recalcular o apontador "fim"
            fim = inicio;
            while (fim.proximo != null) {
                fim = fim.proximo;
            }
        }

        // Mostra todos os livros. Complexidade: o(n)
        public void listar() {
            Livro actual = inicio;
            int i = 1;
            while (actual != null) {
                System.out.println(" " + i + ". " + actual);
                actual = actual.proximo;
                i++;
            }
        }
    }

    // Lê um texto que não pode ficar vazio
    static String lerTexto(Scanner sc, String mensagem) {
        String texto;
        do {
            System.out.print(mensagem);
            texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println(" O campo não pode ficar vazio.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    // Lê um inteiro dentro de um intervalo, repete até ser válido
    static int lerInteiro(Scanner sc, String mensagem, int min, int max) {
        while (true) {
            System.out.print(mensagem);
            String linha = sc.nextLine().trim();
            try {
                int valor = Integer.parseInt(linha);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println(" Valor fora do intervalo (" + min + " a " + max + ").");
            } catch (NumberFormatException e) {
                System.out.println(" Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    static void mostrarMenu() {
        System.out.println();
        System.out.println("========== BIBLIOTECA ==========");
        System.out.println(" 1 - Adicionar livro");
        System.out.println(" 2 - Remover livro");
        System.out.println(" 3 - Buscar livro por título");
        System.out.println(" 4 - Buscar livro por autor");
        System.out.println(" 5 - Ordenar por ano de publicação");
        System.out.println(" 6 - Listar todos os livros");
        System.out.println(" 7 - Carregar livros de exemplo");
        System.out.println(" 0 - Sair");
        System.out.println("===============================");
    }

    // Livros de exemplo para facilitar os testes7

    static void carregarExemplos(ListaLivros lista) {
        String[][] dados = {
                {"Dom Casmurro", "Machado de Assis", "1899"},
                {"Ualalapi", "Ungulani Ba Ka Khosa", "1987"},
                {"Terra Sonâmbula", "Mia Couto", "1992"},
                {"O Alquimista", "Paulo Coelho", "1988"},
                {"Niketche", "Paulina Chiziane", "2002"},
                {"Memórias Póstumas de Brás Cubas", "Machado de Assis", "1881"},
                {"Balada de Amor ao Vento", "Paulina Chiziane", "1990"}
        };
        int adicionados = 0;
        for (String[] d : dados) {
            if (lista.adicionar(d[0], d[1], Integer.parseInt(d[2]))) {
                adicionados++;
            }
        }
        System.out.println("Foram carregados " + adicionados + " livros de exemplo.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ListaLivros biblioteca = new ListaLivros();
        int opcao;

        System.out.println("Bem-vindo ao sistema de gerenciamento da biblioteca");

        do {
            mostrarMenu();
            opcao = lerInteiro(sc, "Escolha uma opção: ", 0, 7);

            switch (opcao) {
                case 1: {
                    String titulo = lerTexto(sc, "Título: ");
                    String autor = lerTexto(sc, "Autor: ");
                    int ano = lerInteiro(sc, "Ano de publicação: ", 1, 2100);
                    if (biblioteca.adicionar(titulo, autor, ano)) {
                        System.out.println("Livro adicionado com sucesso.");
                    } else {
                        System.out.println("Esse livro já existe na biblioteca.");
                    }
                    break;
                }
                case 2: {
                    if (biblioteca.estaVazia()) {
                        System.out.println("A biblioteca está vazia.");
                    } else {
                        String titulo = lerTexto(sc, "Título do livro a remover: ");
                        if (biblioteca.remover(titulo)) {
                            System.out.println("Livro removido com sucesso.");
                        } else {
                            System.out.println("Livro não encontrado.");
                        }
                    }
                    break;
                }
                case 3: {
                    String titulo = lerTexto(sc, "Título (ou parte dele): ");
                    System.out.println("Resultados:");
                    if (biblioteca.buscarPorTitulo(titulo) == 0) {
                        System.out.println(" Nenhum livro encontrado.");
                    }
                    break;
                }
                case 4: {
                    String autor = lerTexto(sc, "Autor (ou parte do nome): ");
                    System.out.println("Resultados: ");
                    if (biblioteca.buscarPorAutor(autor) == 0) {
                        System.out.println(" Nenhum livro encontrado.");
                    }
                    break;
                }
                case 5: {
                    if (biblioteca.estaVazia()) {
                        System.out.println("A biblioteca está vazia.");
                    } else {
                        biblioteca.ordenarPorAno();
                        System.out.println("Livros ordenados por ano de publicação:");
                        biblioteca.listar();
                    }
                    break;
                }
                case 6: {
                    if (biblioteca.estaVazia()) {
                        System.out.println("A biblioteca está vazia.");
                    } else {
                        System.out.println("Livros na biblioteca (" + biblioteca.getTamanho() + "):");
                        biblioteca.listar();
                    }
                    break;
                }
                case 7:
                    carregarExemplos(biblioteca);
                    break;
                case 0:
                    System.out.println("A sair do programa. Até breve!");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
        sc.close();
    }
}