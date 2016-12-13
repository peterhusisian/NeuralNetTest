package neuralnettest;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author phusisian
 */
public class Input implements Drawable{
    private double[] inputs;
    private NeuronLayers neuronLayers;
    private double[] expectedOutput;
    public Input(NeuronLayers neuronLayersIn, double[] inputsIn, double[] expectedOutputIn){
        inputs = inputsIn;
        expectedOutput = expectedOutputIn;
        for(double d : inputs){
            System.out.print(d + ", ");
        }
        
        neuronLayers = neuronLayersIn;
    }
    
    public double[] getExpectedOutput(){
        return expectedOutput;
    }
    
    public void calculateInput(){
        neuronLayers.clearNeuronOutputs();
        setNetInputs();
        calculateOutputs();
    }
    
    public static Input instantiateWithRandomInputsAndOutputs(NeuronLayers neuronLayersIn){
        
        double[] inputs = new double[neuronLayersIn.getBaseLayer().getNeurons().size()];
        double[] expectedOutput = new double[neuronLayersIn.getTopLayer().getNeurons().size()];
        for(int i = 0; i < inputs.length; i++){
            inputs[i] = (int)(Math.round(Math.random()));
        }
        for(int i = 0; i < expectedOutput.length; i++){
            expectedOutput[i] = (int)(Math.round(Math.random()));
        }
        return new Input(neuronLayersIn, inputs, expectedOutput);
    }
    
    private void setNetInputs(){
        ArrayList<Neuron> baseNeurons = neuronLayers.getBaseLayer().getNeurons();
        for(int i = 0; i < baseNeurons.size(); i++){
            baseNeurons.get(i).addWeightedInput(inputs[i]);
            neuronLayers.getBias().sendOutputAcrossLinks();
            baseNeurons.get(i).setNeuronOutput();
        }
    }
    
    private void calculateOutputs(){
        neuronLayers.calculateOutput();
    }

    public boolean isAnswerCorrect(){
        ArrayList<Neuron> topNeurons = neuronLayers.getTopLayer().getNeurons();
        for(int i = 0; i < topNeurons.size(); i++){
            if(Math.round(topNeurons.get(i).getNeuronOutput()) != expectedOutput[i]){
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void draw(Graphics g) 
    {
        int step = 50;
        g.drawString("Inputs: ", 400, 100);
        for(int i = 0; i < inputs.length; i++){
            g.drawString(Double.toString(inputs[i]) + ", ", 500 + step*i, 100);
        }
        g.drawString("Expected Outputs: ", 350, 120);
        for(int i = 0; i < expectedOutput.length; i++){
            g.drawString(Double.toString(expectedOutput[i]) + ", ", 500 + step*i, 120);
        }
        g.drawString("Net Outcome: " + Boolean.toString(isAnswerCorrect()), 500, 140);
    }
    
}
