package neuralnettest;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author phusisian
 */
public class NeuronLayers implements Drawable
{
    private NeuronLayer[] neuronLayers = new NeuronLayer[0];
    private BiasNeuron bias = new BiasNeuron(this);
    private Input input;
    private BackProp backProp;
    private boolean doBackProp = false;
    BinaryFunction binaryFunction;
    private ArrayList<Link> links = new ArrayList<Link>();
    public NeuronLayers(){
        //addBlankNeuronLayers(3);
        addRandomNeuronLayers(10, 15);
        setLayersDrawY();
        initNetNeuronLinks();
        bias.initNeuronLinks();
        binaryFunction = new BinaryFunction(this);
        input = binaryFunction.getRandomInput();
        input.calculateInput();
        
        backProp = new BackProp(this);
        //backProp.backPropagate(input);
        //input = Input.instantiateWithRandomInputsAndOutputs(this);
    }
    
    public void toggleBackProp(){
        doBackProp = !doBackProp;
    }
    
    public void backPropagate(){
        backProp.backPropagateUntilCostIsBelowPercent(input, .1, 200);
    }
    
    public ArrayList<Link> getLinks(){
        return backProp.getLinks();
    }
    
    public void addLink(Link l){
        backProp.addLink(l);
    }
    
    public void chooseNewInput(){
        input = binaryFunction.getRandomInput();
        input.calculateInput();
    }
    
    public void clearNeuronOutputs(){
        for(NeuronLayer nl : neuronLayers){
            nl.clearNeuronOutputs();
        }
    }
    
    private void addBlankNeuronLayers(int num){
        for(int i = 0; i < num; i++){
            addBlankNeuronLayer();
        }
    }
    /*Reminder that I need to add bias weights before I use the activation function*/
    private void addRandomNeuronLayers(int lowerBound, int upperBound){
        int randNum = (int)(Math.random() * (upperBound - lowerBound + 1)) + lowerBound;//I would make a static class to return common random functions like this.
        for(int i = 0; i < randNum; i++){
            addBlankNeuronLayer();
        }
    }
    
    public BiasNeuron getBias(){
        return bias;
    }
    
    public NeuronLayer getBaseLayer(){
        return neuronLayers[0];
    }
    
    public NeuronLayer getTopLayer(){
        return neuronLayers[neuronLayers.length - 1];
    }
    
    public void calculateOutput(){
        for(NeuronLayer nl : neuronLayers){
            nl.sendNeuronOutputsToNextLayer();
        }
    }
    
    public NeuronLayer[] getNeuronLayers(){
        return neuronLayers;
    }
    
    public void applyBiasAndWeightsToNeurons(){
        bias.sendOutputAcrossLinks();
    }
    
    private void setLayersDrawY(){
        int startNum = 800;
        int step = (int)(30 * Panel.scale);
        for(int i = 0; i < neuronLayers.length; i++){
            neuronLayers[i].setDrawY(startNum - (step*i));
        }
    }
    
    public NeuronLayer getNextLayer(int currentLayerNum){
        if(currentLayerNum < neuronLayers.length - 1){
            return neuronLayers[currentLayerNum + 1];
        }
        return null;//returning null is sloppy possibly.
    }
    
    private void addBlankNeuronLayer(){
        NeuronLayer[] temp = new NeuronLayer[neuronLayers.length + 1];
        for(int i = 0; i < neuronLayers.length; i++){
            temp[i] = neuronLayers[i];
        }
        temp[temp.length - 1] = new NeuronLayer(this, temp.length - 1);
        neuronLayers = temp;
    }

    private void initNetNeuronLinks(){
        for(NeuronLayer nl : neuronLayers){
            nl.initLayerNeuronLinks();
        }
    }
    
    public Neuron[] getAllNeuronsExcludingOutput(){
        int totalNeurons = 0;
        for(NeuronLayer nl : neuronLayers){
            totalNeurons += nl.getLayerSize();
        }
        totalNeurons -= neuronLayers[neuronLayers.length - 1].getNeurons().size();
        Neuron[] returnNeurons = new Neuron[totalNeurons];
        int count = 0;
        for(int layerNum = 0; layerNum < neuronLayers.length - 1; layerNum++){
            for(int neuronNum = 0; neuronNum < neuronLayers[layerNum].getLayerSize(); neuronNum++){
                returnNeurons[count] = neuronLayers[layerNum].getNeurons().get(neuronNum);
                count++;
            }
        }
        return returnNeurons;
    }
    
    @Override
    public void draw(Graphics g) {
        if(doBackProp){
            backProp.backPropagate(input);
        }
        bias.draw(g);
        for(NeuronLayer nl : neuronLayers){
            nl.draw(g);
        }
        input.draw(g);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
