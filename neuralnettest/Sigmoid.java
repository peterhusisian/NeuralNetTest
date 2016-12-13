package neuralnettest;

/**
 *
 * @author phusisian
 */
public class Sigmoid implements ActivationFunction {
    @Override
    public double getActivation(double sumIn) {
        return 1.0/(1 + Math.exp(- sumIn));
    }
}
