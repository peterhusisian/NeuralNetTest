package neuralnettest;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author phusisian
 */
public class Link implements Drawable
{
    private Neuron baseNeuron, endNeuron;
    private double weight;
    private boolean isBiasLink = false;
    public Link(Neuron baseNeuronIn, Neuron endNeuronIn)
    {
        baseNeuron = baseNeuronIn;
        endNeuron = endNeuronIn;
        setRandomWeight();
    }
    
    
    public void setBiasLink(boolean b){
        isBiasLink = b;
    }
    
    public boolean isBiasLink(){
        return isBiasLink;
    }
    
    private void setRandomWeight()
    {
        weight = 5 * Math.random() - 2.5;//+ or - the random value is arbitrary
        //System.out.println("Rand weight: " + weight);
    }
    
    public void weightInputAndSend(double input){
        endNeuron.addWeightedInput(input * weight);
    }

    public double getWeight(){
        return weight;
    }
    
    public void setWeight(double d){
        weight = d;
    }
    
    @Override
    public void draw(Graphics g) 
    {
        Color c;
        
        try {//this is super sloppy
          if(weight > 0){
            c = new Color(0,255,0,55+(int)(200 * (weight/2.5)));
            }else{
                c = new Color(255,0,0,55+(int)(Math.abs(200 * (weight/2.5))));
            }  
            g.setColor(c);
        } catch (Exception e) {
        }
        
        
        //g.setColor(new Color(0, 0, 0, 125));
        g.drawLine((int)baseNeuron.getDrawPoint().getX(), (int)baseNeuron.getDrawPoint().getY(), (int)endNeuron.getDrawPoint().getX(), (int)endNeuron.getDrawPoint().getY());
        g.setColor(Color.BLACK);
    }
}
