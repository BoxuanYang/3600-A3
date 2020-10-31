import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class A3_u6778195 {
    private static int B;
    private static int n;
    private static ArrayList<Integer>[] salaries;


    public static void readFile(String fileName) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader(fileName));
        B = in.nextInt() * 10;
        n = in.nextInt();
        salaries = new ArrayList[n+1];
        for (int i=1; i <= n; i++) {
            salaries[i] = new ArrayList<Integer>();
            int ki = in.nextInt(); // #applicants for every position
            for (int j = 1; j <= ki ; j++) {
                salaries[i].add(in.nextInt());
            }
        }
        in.close();
    }

    public static int testResult(int test) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader("res.txt"));

        String str = "";
        for(int i = 0; i < test; i++){
            str = in.nextLine();
        }
        in.close();

        String[] words = str.split(" ");
        int[] indexes = new int[n];

        for(int i = 0; i < n; i++){
            indexes[i] = Integer.valueOf(words[4 + i]);
        }



        int result = Integer.valueOf(words[3]) * 1000;
        System.out.println("Solution result is: " + result + " ");
        System.out.println("Solution indexes and salaries are: ");
        int sum = 0;
        for (int i = 0; i < n; i++) {
            System.out.print(indexes[i] + " ");
            System.out.print(salaries[i+1].get(indexes[i]-1));
            sum += salaries[i+1].get(indexes[i]-1);
            System.out.println();
        }
        System.out.println("Sum of them are: " + sum);

        return 0;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "test1.txt";
        readFile(fileName);

        testResult(fileName.charAt(4) - '0');

        /**
         * largest[i][j] = max(largest[i-1][j-s[i][v]] + s[i][v])
         */
        int[][] largest = new int[n+1][B+1];

        for (int i=1; i <= n; i++) {

            for (int j = 0; j <= B; j++) {

                int best = Integer.MIN_VALUE;

                for (int k = 0; k < salaries[i].size(); k++) {
                    int salary = salaries[i].get(k);

                    if(j - salary < 0)
                        continue;


                    int sub = largest[i - 1][j - salary];

                    if (sub == Integer.MIN_VALUE) {
                        continue;
                    }

                    best = Math.max(best, largest[i - 1][j - salary] + salary);
                }

                largest[i][j] = best;
            }
        }

        int best = largest[n][B];

        if (best == Integer.MIN_VALUE) {
            System.out.println("no solution");
            return;
        }

        int[] selected = new int[n + 1]; // indexes: 1, 2...n
        int j = B;
        for (int i = n; i > 0; i--) {

            int nextJ = -1;

            for (int k = 0; k < salaries[i].size(); k++) {

                int salary = salaries[i].get(k);

                if(j - salary < 0) {
                    continue;
                }

                int sub = largest[i - 1][j - salary];

                if (sub == Integer.MIN_VALUE) {
                    continue;
                }

                if(largest[i][j] == sub + salary) {
                    nextJ = j - salary;
                    selected[i] = k;
                }

            }

            j = nextJ;
        }

        System.out.println("Our result is: " + best * 1000 + " ");
        System.out.println("Our indexes and salaries are: ");
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            System.out.print( (selected[i] + 1) + " ");
            System.out.print(salaries[i].get(selected[i]));
            sum += salaries[i].get(selected[i]);
            System.out.println();
        }
        System.out.println("Sum of them are: " + sum);

    }
}
