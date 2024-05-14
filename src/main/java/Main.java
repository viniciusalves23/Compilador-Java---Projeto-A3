import java.text.DecimalFormat;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Menu:");
        System.out.println("1 - Resolver uma expressão matemática");
        System.out.println("2 - Converter linguagem aluno em linguagem Java");
        System.out.print("Escolha uma opção: ");

        try {
            int option = Integer.parseInt(reader.readLine());

            if (option == 1) {
                // Lógica para Resolver uma expressão matemática
                System.out.println("Insira uma expressão matemática:");
                String expression = reader.readLine(); // Lendo a expressão matemática
                MathScanner sc = new MathScanner(expression);
                List<ScannedToken> scanExp = sc.scan();
                Parser parser = new Parser(scanExp);
                List<ScannedToken> parsed = parser.parse();

                // Integração da classe VariableScanner para identificar e armazenar variáveis
                VariableScanner variableScanner = new VariableScanner();
                List<Variable> variables = variableScanner.scanVariables(expression);

                // Integração da classe JavaCodeGenerator para gerar o código Java
                // correspondente
                JavaCodeGenerator javaCodeGenerator = new JavaCodeGenerator();
                String javaCode = javaCodeGenerator.generateJavaCode(parsed);

                double result = sc.evaluate(parsed);
                DecimalFormat df = new DecimalFormat("#.##");
                String formattedResult = df.format(result).replace('.', ',');
                System.out.println("O valor da expressão é: " + formattedResult);

                // Exibir código Java gerado
                System.out.println("Código Java gerado:");
                System.out.println(javaCode);
            } else if (option == 2) {
                // Lógica para converter linguagem aluno em linguagem Java
                Scanner scanner = new Scanner(System.in);
                System.out.println("Insira o código na linguagem aluno:");
                StringBuilder input = new StringBuilder();
                input.append(scanner.nextLine()).append("\n");
                while (true) {
                    String line = scanner.nextLine().trim();
                    if (line.equals("fim;")) {
                        break;
                    }
                    input.append(line).append("\n");
                }
                scanner.close();

                String javaCode = ConversorLinguagemAluno.compile(input.toString());
                System.out.println("Código em Java gerado:\n");
                System.out.println(javaCode);
            } else {
                System.out.println("Opção inválida. Programa encerrado");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Fechar o leitor após o uso
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
