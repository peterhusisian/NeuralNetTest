package neuralnettest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author phusisian
 */
public class Neuron implements Drawable
{
    private ActivationFunction actFunc = new Sigmoid();//different activiation functions can be chosen
    public static final int neuronRadius = 10;
    private NeuronLayer boundLayer;
    private int drawX;
    private double summedInputs;
    private ArrayList<Link> links = new ArrayList<Link>();
    private double neuronOutput;
    
    public Neuron(NeuronLayer boundLayerIn){
        boundLayer = boundLayerIn;
        summedInputs = 0;
    }
    
    public void setNeuronOutput(){
        neuronOutput = actFunc.getActivation(summedInputs);
    }
    
    public double getNeuronOutput(){
        return neuronOutput;
    }
    
    public void clearOutput(){
        summedInputs = 0;
        neuronOutput = 0;
    }
    
    public void sendOutputAcrossLinks(){
        //System.out.println("neuron output: " + neuronOutput);
        for(Link l : links){
            l.weightInputAndSend(neuronOutput);
        }
    }
    
    public void addLink(Link l){
        links.add(l);
    }
    
    public ArrayList<Link> getLinks(){
        return links;
    }
    
    public void initNeuronLinks(){
        NeuronLayer nextLayer = boundLayer.getNextLayer();
        if(nextLayer != null){
            ArrayList<Neuron> nextLayerNeurons = nextLayer.getNeurons();
            for(Neuron nextLayerNeuron : nextLayerNeurons){
                Link l = new Link(this, nextLayerNeuron);
                links.add(l);
            }
        }
    }
            
    public void setDrawX(int drawXIn){
        drawX = drawXIn;
    }
    
    public Point getDrawPoint(){
        return new Point(drawX, boundLayer.getDrawY());
    }
    
    public void addWeightedInput(double numIn){
        summedInputs += numIn;
    }
    

    @Override
    public void draw(Graphics g){
        for(Link l : links){
            l.draw(g);
        }
        Color weightedColor = new Color((int)(255*neuronOutput), (int)(255*neuronOutput), (int)(255*neuronOutput));
        g.setColor(weightedColor);
        g.fillOval(drawX - neuronRadius, boundLayer.getDrawY() - neuronRadius, 2 * neuronRadius, 2 * neuronRadius );
        g.setColor(Color.BLACK);
        g.drawOval(drawX - neuronRadius, boundLayer.getDrawY() - neuronRadius, 2 * neuronRadius, 2 * neuronRadius );
        
        if(boundLayer.isLastLayer()){
            g.drawString(Double.toString((int)((neuronOutput)*1000.0)/1000.0), drawX, boundLayer.getDrawY() - 50);
        }else if(boundLayer.isBaseLayer()){
             g.drawString(Double.toString((int)((neuronOutput)*1000.0)/1000.0), drawX, boundLayer.getDrawY() + 25);
        }
    }
}
