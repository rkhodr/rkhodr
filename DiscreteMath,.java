import java.util.Scanner;

public class DiscreteMath {
     
    public static long factorial(int n) {
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Method to calculate combinations: C(n, k) = n! / (k! * (n - k)!)
    public static long combinations(int n, int k) {
        if (k > n) {
            return 0;  // Cannot choose more elements than available
        }
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    public static long permutations(int n, int k) {
        if (k > n) {
            return 0;  // It's not possible to pick more elements than available
        }
        return factorial(n) / factorial(n - k);
    }

    public static int countPartitions(int n) {
        // dp[i] will store the number of partitions of i
        int[] dp = new int[n + 1];
        dp[0] = 1;  // There's one way to partition 0 (the empty partition)

        // Dynamic programming: Build up the solution for each number from 1 to n
        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j++) {
                dp[j] += dp[j - i];
            }
        }

        return dp[n]; // The number of partitions of n
    }

    public static long stirlingNumber(int n, int x) { //set partition
        // dp[i][j] will store S(i, j), the Stirling number of the second kind
        long[][] dp = new long[n + 1][x + 1];

        // Base case: S(0, 0) = 1
        dp[0][0] = 1;

        // Fill the dynamic programming table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= x; j++) {
                dp[i][j] = j * dp[i - 1][j] + dp[i - 1][j - 1];
            }
        }

        // Return the Stirling number S(n, x)
        return dp[n][x];
    }

      // Method to calculate the number of ways to partition n distinct balls into up to x boxes
      public static long partitionsIntoAtMostXBoxes(int n, int x) {
        long result = 0;

        // Sum up S(n, i) for i from 1 to x
        for (int i = 1; i <= x; i++) {
            result += stirlingNumber(n, i);
        }

        return result;
    }



       public static String calculate(int n, int x, boolean ball, boolean box, String res){

        //SECTION 1- NO LIMITATIONS
        if((res.equals("n"))){
            if(ball && box){
                return "This is a multiplication principle problem. The total variations would be " + (int)Math.pow(x,n);
            }
            else if(!ball && box){
                return "This is a combinations with repetition problem. The total variations would be " + (int)combinations(n+x-1,n);
            }
            else if(ball && !box){
                return "This is a sum of set partition problem. The total variations would be " + (int)partitionsIntoAtMostXBoxes(n,x);
            }
            else if(!ball && !box){
                return "This is an integer partition problem. The total variations would be " + countPartitions(n+x);
            }
        }



        //SECTION 2- INJECTIVE
        if((res.equals("i"))){
            if(ball && box){
                return "This is a permutations problem. The total variations would be " + (int)permutations(n,x);
            }
            else if(!ball && box){
                return "This is a regular combinations. The total variations would be " + (int)combinations(n,x);
            }
            else if(ball && !box){
                if(n>x) return "With if there are more balls than boxes, but you can have at most one ball in each box, this is impossible (using every ball), so answer is 0";
                else return  "With there being an equal or lesser number of balls compared to indentical box, theres only one way to put them since the boxes are the same, so answer is 1";
            }
            else if(!ball && !box){
                if(n>x) return "With if there are more balls than boxes, but you can have at most one ball in each box, this is impossible (using every ball), so answer is 0";
                else return "With there being an equal or lesser number of balls compared to indentical box, theres only one way to put them since the boxes are the same, so answer is 1";
            }
        }



        //SECTION 3- SURJECTIVE
        if((res.equals("s"))){
            if(ball && box){
                return "This is a labeled set partition problem. The total variations would be " + (int)factorial(x) * (int)stirlingNumber(n,x);
            }
            else if(!ball && box){
                return "This is a combinations without repetition problem. The total variations would be " + (int)combinations(n-1,x-1);
            }
            else if(ball && !box){
                return "This is a set partition problem. The total variations would be " + stirlingNumber(n,x);
            }
            else if(!ball && !box){
                return "This is a integer partition problem. The total variations would be " + countPartitions(n);
            }
        }
        return "Invalid Input somewhere. Try again with valid inputs";
    }



        public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of 'balls': ");
        int balls = scanner.nextInt();

        System.out.print("Enter number of 'boxes': ");
        int boxes = scanner.nextInt();

        System.out.print("Are balls distinct? (true/false): ");
        boolean ballsDistinct = scanner.nextBoolean();

        System.out.print("Are boxes distinct? (true/false): ");
        boolean boxesDistinct = scanner.nextBoolean();

        System.out.print("Enter restrictions: n- for none, i- (injective) for at Most One, s- (surjective) for at Least One): ");
        String restriction = scanner.next();

        String result = calculate(balls, boxes, ballsDistinct, boxesDistinct, restriction);
        System.out.println("Number of variations: " + result);
    }
}
