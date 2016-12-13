package neuralnettest;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author phusisian
 */
public class NeuronLayer implements Drawable
{
    //http://math.stackexchange.com/questions/2056394/how-does-backpropagation-work
    
    private ArrayList<Neuron> neurons = new ArrayList<Neuron>();
    private int drawY;
    private int layerNum;
    private NeuronLayers boundLayers;
    public NeuronLayer(NeuronLayers boundLayersIn, int layerNumIn){
        boundLayers = boundLayersIn;
        layerNum = layerNumIn;
        //addNeurons(1);
        addRandomNeurons(12, 15);
        setNeuronDrawPositions();
    }
    
    private void addRandomNeurons(int lowerBound, int upperBound){
        int randNum = (int)(Math.random() * (upperBound - lowerBound + 1)) + lowerBound;
        for(int i = 0; i < randNum; i++){
            neurons.add(new Neuron(this));
        }
    }
    
    private void addNeurons(int numNeurons){
        for(int i = 0; i < numNeurons; i++){
            neurons.add(new Neuron(this));
        }
    }
    
    public boolean isLastLayer(){
        return (boundLayers.getNextLayer(layerNum) == null);
    }
    
    public boolean isBaseLayer(){
        return (layerNum == 0);
    }
    
    public void sendNeuronOutputsToNextLayer(){
        for(Neuron n : neurons){
            n.setNeuronOutput();
            if(! isLastLayer()){
                n.sendOutputAcrossLinks();
            }
        }

    }
    
    public void setDrawY(int drawYIn){
        drawY = drawYIn;
    }
    
    public ArrayList<Neuron> getNeurons(){
        return neurons;
    }
    
    public void clearNeuronOutputs(){
        for(Neuron n : neurons){
            n.clearOutput();
        }
    }
    
    private void setNeuronDrawPositions(){
        int step = (int)(30 * Panel.scale);
        int radius = (int)(10 * Panel.scale);
        int startX = (int)(Panel.MID_SCREEN_X - (neurons.size() * step)/2.0);
        for(int i = 0; i < neurons.size(); i++){
            neurons.get(i).setDrawX(startX + (i*step));
        }
    }
    
    public int getLayerSize(){
        return neurons.size();
    }
    
    public NeuronLayers getNeuronLayers(){
        return boundLayers;
    }
    
    public int getDrawY(){
        return drawY;
    }
    
    public NeuronLayer getNextLayer(){
        return boundLayers.getNextLayer(layerNum);
    }
    
    public void initLayerNeuronLinks(){
        for(Neuron n : neurons){
            n.initNeuronLinks();
        }
    }
    
    @Override
    public void draw(Graphics g){
        
        g.drawString("Layer Number: " + Integer.toString(layerNum), 30, drawY);
        for(Neuron neuron : neurons){
            neuron.draw(g);
        }
    }
    
    
}
