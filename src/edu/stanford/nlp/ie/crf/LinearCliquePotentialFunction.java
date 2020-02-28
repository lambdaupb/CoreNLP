package edu.stanford.nlp.ie.crf;

/**
 * @author Mengqiu Wang
 */
public class LinearCliquePotentialFunction implements CliquePotentialFunction {

  //FIXME really large 150k*24
  private final float[][] weights;

  LinearCliquePotentialFunction(float[][] weights) {
    this.weights = weights;
  }

  LinearCliquePotentialFunction(double[][] weights) {

    this.weights = CRFClassifier.toFloats(weights);
  }

  @Override
  public double computeCliquePotential(int cliqueSize, int labelIndex,
      int[] cliqueFeatures, double[] featureVal, int posInSent) {
    double output = 0.0;
    for (int m = 0; m < cliqueFeatures.length; m++) {
      double dotProd = weights[cliqueFeatures[m]][labelIndex];
      if (featureVal != null) {
        dotProd *= featureVal[m];
      }
      output += dotProd;
    }
    return output;
  }

}
