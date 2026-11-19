# Sistema de Gerenciamento de Biblioteca

Programa de consola desenvolvido em **Java** para gerenciar uma biblioteca de livros
(título, autor e ano de publicação), com um menu interativo.

Trabalho de Campo da disciplina de **Algoritmos e Estrutura de Dados**,
Licenciatura em Engenharia Informática — UnISCED.

## Funcionalidades

- **Adicionar livro** — regista título, autor e ano de publicação; recusa livros repetidos.
- **Remover livro** — remove o livro pelo título (não distingue maiúsculas de minúsculas).
- **Buscar por título** — mostra os livros cujo título contém o texto digitado.
- **Buscar por autor** — mostra os livros cujo autor contém o texto digitado.
- **Ordenar por ano de publicação** — do ano mais antigo para o mais recente.
- **Listar todos os livros** — mostra os livros guardados e o total.
- **Carregar livros de exemplo** — insere 7 livros para facilitar os testes.

O programa valida os dados digitados (texto onde se espera um número, opções fora do menu,
campos vazios) e repete a pergunta em vez de terminar com erro.

## Estrutura do projeto

```
BibliotecaAED/
├── src/
│   ├── Biblioteca.java        # Programa principal: menu, lista ligada e operações
│   ├── TesteCorrecao.java     # Teste automático de correção da lista ligada
│   └── TesteDesempenho.java   # Medição de tempos da ordenação e da busca
├── .gitignore
└── README.md
```

## Estruturas de dados e algoritmos utilizados

- **Lista ligada simples** — cada livro é um nó (`Livro`) com uma referência para o seguinte.
  A lista (`ListaLivros`) guarda o primeiro nó, o último e o número de livros.
- **Busca linear** — por título e por autor.
- **Insertion sort** — adaptado para lista ligada, estável, para ordenar por ano.
- **Estruturas de controlo** — menu com `switch` dentro de um ciclo `do-while`.

### Complexidade (n = número de livros)

| Operação                  | Tempo                              |
|---------------------------|------------------------------------|
| Adicionar livro           | O(n) (verificação de duplicados)   |
| Remover livro             | O(n)                               |
| Buscar por título / autor | O(n)                               |
| Ordenar por ano           | O(n²) pior e médio caso; O(n) melhor caso |
| Listar livros             | O(n)                               |

## Testes

- `TesteCorrecao.java` — faz 300 sequências de 80 operações aleatórias (adicionar, remover,
  ordenar e buscar) e compara a lista ligada com um modelo feito com `ArrayList`. Verifica
  também o tamanho e o apontador do último nó. Resultado esperado:
  `RESULTADO: tudo correcto.`
- `TesteDesempenho.java` — mede o tempo da ordenação e da busca com listas de 1 000 a
  16 000 livros. Os tempos dependem do computador.

## Dependências

Nenhuma biblioteca externa é necessária. Apenas o **JDK (Java Development Kit) versão 17 ou superior**.

Para verificar se o JDK está instalado:

```bash
java -version
javac -version
```

## Como configurar e executar

1. Clonar o repositório:

   ```bash
   git clone https://github.com/rcharles1-dot/BibliotecaAED.git
   cd BibliotecaAED
   ```

2. Compilar o código-fonte:

   ```bash
   javac -encoding UTF-8 -d bin src/*.java
   ```

3. Executar o programa:

   ```bash
   java -cp bin Biblioteca
   ```

4. Executar os testes (opcional):

   ```bash
   java -cp bin TesteCorrecao
   java -cp bin TesteDesempenho
   ```

> **Nota sobre acentuação:** se os caracteres acentuados aparecerem trocados na consola
> (ex.: `?` em vez de `ç`), execute com a codificação UTF-8 explícita:
> `java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -cp bin Biblioteca`
> No Windows, pode ser necessário correr `chcp 65001` antes, na CMD.

Alternativamente, o projeto pode ser aberto e executado diretamente no **IntelliJ IDEA**,
correndo o método `main` da classe `Biblioteca`.

## Autor

Roque Suraya Charles — Estudante de Engenharia Informática, UnISCED.
Código do Estudante: 51260131
Email do Estudante: rcharles1@estudantes.unisced.edu.mz
Contacto: 824921279
