package neuralnettest;

/**
 *
 * @author phusisian
 */
public class BinaryFunction{
    
    private NeuronLayers neuronLayers;
    Input[] inputs;
    
    public BinaryFunction(NeuronLayers neuronLayersIn){
        neuronLayers = neuronLayersIn;
        inputs = new Input[(int)Math.pow(2, neuronLayers.getBaseLayer().getNeurons().size())];
        fillInputs();
    }
    
    public Input getRandomInput(){
        int randNum = (int)(inputs.length * Math.random());
        return inputs[randNum];
    }
    
    private void fillInputs(){
        for(int i = 0; i < inputs.length; i++){
            String binaryString = Integer.toBinaryString(i);
            while(binaryString.length() < neuronLayers.getBaseLayer().getNeurons().size()){
                binaryString = "0" + binaryString;
            }
            double[] inputNums = new double[binaryString.length()];
            for(int j = 0; j < binaryString.length(); j++){
                inputNums[j] = Double.parseDouble(binaryString.substring(j, j+1));
            }
            double[] randomOutputs = getRandomOutputs();
            inputs[i] = new Input(neuronLayers, inputNums, randomOutputs);
        }
    }
    
    private double[] getRandomOutputs(){
        double[] randomOutput = new double[neuronLayers.getTopLayer().getNeurons().size()];
        int randNum = (int)(Math.pow(2, neuronLayers.getTopLayer().getNeurons().size()) * Math.random());
        String binaryString = Integer.toBinaryString(randNum);
        for(int i = 0; i < binaryString.length(); i++){
            randomOutput[i] = Double.parseDouble(binaryString.substring(i, i+1));
        }
        return randomOutput;
    }
}
