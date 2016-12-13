package neuralnettest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author phusisian
 */
public class BiasNeuron extends Neuron implements Drawable
{
    public static final double biasValue = 1.0;
    private NeuronLayers boundLayers;
    private int drawY = 750, drawX = 400;
    public BiasNeuron(NeuronLayers boundLayersIn){
        super(null);//using null is dangerous and the result of poor foresight in my neuron creation. A bias neuron is not bound to a single layer.
        boundLayers = boundLayersIn;
    }
    
    
    @Override
    public void initNeuronLinks(){
        Neuron[] totalNeurons = boundLayers.getAllNeuronsExcludingOutput();
        for(Neuron n : totalNeurons){
            Link l = new Link(this, n);
            l.setBiasLink(true);
            addLink(l);
        }
    }
    
    @Override
    public void sendOutputAcrossLinks(){
        for(Link l : getLinks()){
            l.weightInputAndSend(biasValue);
        }
    }
    
    @Override
    public Point getDrawPoint(){
        return new Point(drawX, drawY);
    }
    
    public void draw(Graphics g){
        float dash1[] = {2.5f};
        BasicStroke dashed =
        new BasicStroke(1.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        2.5f, dash1, 0.0f);
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(dashed);
        g.setColor(new Color(0,0,0,125));
        for(Link l : getLinks()){
            l.draw(g);
        }
        g.setColor(Color.BLACK);
        g.drawOval(drawX - neuronRadius, drawY - neuronRadius, 2 * neuronRadius, 2 * neuronRadius );
        g2.setStroke(new BasicStroke(1));
        g.setColor(Color.MAGENTA);
        
        g.drawString(Double.toString(biasValue), drawX - 10, drawY + 10);
        g.setColor(Color.BLACK);
    }

}
