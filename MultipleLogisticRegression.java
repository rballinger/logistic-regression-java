import java.util.*;

public class MultipleLogisticRegression {
    private final int N;    // number of total items
    private final int P;    // number of predictors
    private final double[] beta;  // regression coefficients

    public MultipleLogisticRegression(double[][] x, double[] y) {
        if (x.length != y.length) throw new RuntimeException("Invalid dimensions");
        if (x == null || y == null || x.length == 0 || y.length == 0) throw new RuntimeException("Input arrays cannot be null");

        // setup variables
        N = y.length;
        P = x[0].length;
        beta = new double[P + 1];
        double totalTrueOutcomes = 0.0;
        double totalFalseOutcomes = 0.0;
        double oTrue = 0.0;
        double oFalse = 0.0;
        double logit = 0.0;
        double totalTrueItems = 0.0;
        double totalFalseItems = 0.0;
        double oTotal = 0.0;
        double totalLogit = 0.0;

        // beta0 coefficient calculation
        for(int j = 0; j < P; j++){
            totalTrueOutcomes = 0.0;
            totalFalseOutcomes = 0.0;
            totalTrueItems = 0.0;
            totalFalseItems = 0.0;
            for(int i = 0; i < N; i++){
                if(y[i] != 0){
                    if(x[i][j] == 0){
                        totalFalseOutcomes++;
                    }
                }
                if(x[i][j] == 0){
                    totalFalseItems++;
                }
            }
            // calculate odds of predictor being false with outcome true
            oTotal = (totalFalseOutcomes / totalFalseItems)
                / ((totalFalseItems - totalFalseOutcomes) / totalFalseItems);
            logit = Math.log(oTotal);   // log of odds (coefficient)
            totalLogit += logit;
        }
        // store coefficient
        beta[0] = totalLogit;

        // all other beta values
        for(int j = 0; j < P; j++){
            totalTrueOutcomes = 0.0;
            totalFalseOutcomes = 0.0;
            totalTrueItems = 0.0;
            totalFalseItems = 0.0;
            for(int i = 0; i < N; i++){
                if(y[i] != 0){
                    if(x[i][j] != 0){
                        totalTrueOutcomes++;
                    }else{
                        totalFalseOutcomes++;
                    }   
                }
                if(x[i][j] != 0){
                    totalTrueItems++;
                }else{
                    totalFalseItems++;
                }
            }
            // calculate ratio of odds
            oTrue = totalTrueOutcomes / (totalTrueItems - totalTrueOutcomes);
            oFalse = totalFalseOutcomes / (totalFalseItems - totalFalseOutcomes);
            oTotal = oTrue / oFalse;
            // check if predictor had effect on outcome
            if(oTotal != 0){
                // find log of odds which is the coefficient
                logit = Math.log(oTotal);
                // store coefficient
                beta[j + 1] = logit;
            }else{
                // store 0 because predictor has no effect on outcome
                beta[j + 1] = 0.0;
            }
        }

        // output results
        System.out.println("\nResults Summary");
        System.out.println("________________");
        System.out.println("| Beta   Coeff |");
        System.out.println("|------|-------|");
        for(int i = 0; i < beta.length; i++){
            System.out.format("|%6d|%7.4f|\n", i, beta[i]);
        }
        System.out.println("|______|_______|\n");

        
    }

    public double beta(int j) {
        return beta[j];
    }

    public double findProbability(double[] x){
        double formula = 0.0;
        formula += beta[0];
        for(int i = 0; i < beta.length - 1; i++){
            formula += beta[i + 1] * x[i];
        }
        double odds = Math.exp(formula);
        double prob = odds / (1 + odds);
        return prob;
    }
}