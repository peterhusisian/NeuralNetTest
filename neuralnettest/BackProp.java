package neuralnettest;

import java.util.ArrayList;

/**
 *
 * @author phusisian
 */
public class BackProp {
    NeuronLayers neuronLayers;
    public static final double learnRate = 0.5;
    public static final double biasLearnRate = learnRate * 4;
    private ArrayList<Link> links = new ArrayList<Link>();
    public BackProp(NeuronLayers neuronLayersIn){
        neuronLayers = neuronLayersIn;
        fillLinks();
    }
    
    public ArrayList<Link> getLinks(){
        return links;
    }
    
    private void fillLinks(){
        for(int i = 0; i < neuronLayers.getNeuronLayers().length; i++){
            for(int j = 0; j < neuronLayers.getNeuronLayers()[i].getNeurons().size(); j++){
                for(Link l : neuronLayers.getNeuronLayers()[i].getNeurons().get(j).getLinks()){
                    links.add(l);
                }
            }
        }
    }
    
    public double getCost(Input input){
        double[] expectedOutput = input.getExpectedOutput();
        NeuronLayer topLayer = neuronLayers.getTopLayer();
        double error = 0;
        for(int i = 0; i < expectedOutput.length; i++){
            error += Math.pow(expectedOutput[i] - topLayer.getNeurons().get(i).getNeuronOutput(), 2);
        }
        error *= 0.5;
        return error;
    }
    
    public void backPropagate(Input input){
        input.calculateInput();
        double[] gradient = getGradient(input);
        for(int i = 0; i < links.size(); i++){
            if(links.get(i).isBiasLink()){
                links.get(i).setWeight(links.get(i).getWeight() - biasLearnRate * gradient[i]);
            }else{
                links.get(i).setWeight(links.get(i).getWeight() - learnRate * gradient[i]);
            }
            
        }
    }
    
    public double[] getGradient(Input input){
        double[] gradient = new double[links.size()];
        for(int i = 0; i < gradient.length; i++){
            gradient[i] = getPartialOfWeight(links.get(i), input);
        }
        return gradient;
    }
    
    public double getPartialOfWeight(Link link, Input input){
        double slopeNum = 0.000001;
        double errorLeft, errorRight;
        double originalWeight = link.getWeight();
        double originalError = getCost(input);
        link.setWeight(originalWeight - slopeNum);
        input.calculateInput();
        errorLeft = getCost(input);
        link.setWeight(originalWeight + slopeNum);
        input.calculateInput();
        errorRight = getCost(input);
        link.setWeight(originalWeight);
        input.calculateInput();
        
        double slopeLeft = (originalError - errorLeft)/(slopeNum);
        double slopeRight = (errorRight - originalError)/(slopeNum);
        System.out.println("Slope: " + ((slopeLeft + slopeRight)/2.0));
        return (slopeLeft + slopeRight)/2.0;
        
    }
    
    
    public void addLink(Link l){
        links.add(l);
    }
    
    
}
