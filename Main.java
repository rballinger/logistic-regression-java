import java.util.Arrays;
import java.util.Random;

public class Main {
  static int totalPeople = 20000;
  static int predictorCount = 2;

  public static void main(String[] args) {
    Random rand = new Random();
    double[] outcomes = new double[totalPeople];
    double[][] predictors2d = new double[totalPeople][predictorCount];
    int outcomeCount = 0;
    int maleLimit = 91;
    int maleFalseLimit = 74;
    int femaleFalseLimit = 77;
    int femaleFalse = 0;
    int maleFalse = 0;
    int male = 0;

    for(int i = 0; i < totalPeople; i++) {
      double[] predictors1d = new double[predictorCount];
      // setup regression data set
      // for testing with male/female population given in example at:
      // http://www.ats.ucla.edu/stat/mult_pkg/faq/general/odds_ratio.htm
    /*
      predictors1d[0] = 0.0;
      outcomes[i] = 0.0;
      if(male < maleLimit){
        if(maleFalse < maleFalseLimit){
          maleFalse++;
        }else{
          outcomes[i] = 1.0;
        }
        male++;
      }else{
        predictors1d[0] = 1.0;
        if(femaleFalse < femaleFalseLimit){
          femaleFalse++;
        }else{
          outcomes[i] = 1.0;
        }
      }
    */

      double randNum = rand.nextDouble();
      predictors1d[0] = rand.nextInt(2);
      predictors1d[1] = rand.nextInt(2);

      double prob = 0.05; // initial is 5% probability
      if(predictors1d[0] == 1){
        prob += 0.05;   // increases by 5% if first predictor is true
      }
      if(predictors1d[1] == 1){
        prob += 0.1;   // increases by 10% if second predictor is true
      }
      // sets outcome given probability based upon predictors
      if(randNum <= prob){
        outcomes[i] = 1;
      }else{
        outcomes[i] = 0;
      }

      if(outcomes[i] == 1){
        outcomeCount++;
      }

      for(int j = 0; j < predictorCount; j++) {
        predictors2d[i][j] = predictors1d[j];
      }
    }

    System.out.println(outcomeCount + " out of " + totalPeople + " had true outcomes.");

    MultipleLogisticRegression mlr;
    try{
      mlr = new MultipleLogisticRegression(predictors2d, outcomes);
      double[] testData = new double[predictorCount];
      /* for larger data set
      for(int i = 0; i < testData.length; i++){
        testData[i] = rand.nextInt(2);
      }
      */

      testData[0] = 0;
      testData[1] = 0;
      System.out.println("Probability calculated for [0,0]: " + mlr.findProbability(testData));
      testData[0] = 1;
      testData[1] = 0;
      System.out.println("Probability calculated for [1,0]: " + mlr.findProbability(testData));
      testData[0] = 0;
      testData[1] = 1;
      System.out.println("Probability calculated for [0,1]: " + mlr.findProbability(testData));
      testData[0] = 1;
      testData[1] = 1;
      System.out.println("Probability calculated for [1,1]: " + mlr.findProbability(testData));
    }catch(RuntimeException e){
        System.out.println("Multiple Logistic Regression failed.");
        e.printStackTrace();
        return;
    }

    System.out.println("Multiple Logistic Regression successful.");
  }
}