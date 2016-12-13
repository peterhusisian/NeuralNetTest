package neuralnettest;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author phusisian
 */
public class Panel extends JPanel implements ActionListener
{
    private JButton resetButton, funcButton, backPropButton;
    public static double scale = 2.0;
    public static final int MID_SCREEN_X = 720;
    //private NeuronLayer layer1 = new NeuronLayer(800);
    NeuronLayers nls = new NeuronLayers();
    public Panel()
    {
        setBounds(0,0,1440,900);
        setOpaque(true);
        setDoubleBuffered(true);
        setFocusable(true);
        resetButton = new JButton("Reset Neurons");
        resetButton.addActionListener(this);
        resetButton.setActionCommand("resetNeurons");
        resetButton.setBounds(100, 100, 100, 100);
        add(resetButton);
        resetButton.setVisible(true);
        
        funcButton = new JButton("Function Button");
        funcButton.addActionListener(this);
        funcButton.setActionCommand("funcButton");
        funcButton.setBounds(100, 100, 100, 100);
        add(funcButton);
        funcButton.setVisible(true);
        
        backPropButton = new JButton("Back Prop");
        backPropButton.addActionListener(this);
        backPropButton.setActionCommand("backProp");
        backPropButton.setBounds(100, 100, 100, 100);
        add(backPropButton);
        backPropButton.setVisible(true);
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        nls.draw(g);
        try {
            Thread.sleep(50);
        } catch (Exception e) {
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getActionCommand().equals("resetNeurons")){
            nls = new NeuronLayers();
        }else if(e.getActionCommand().equals("funcButton")){
            nls.chooseNewInput();
        }else if(e.getActionCommand().equals("backProp")){
            nls.toggleBackProp();
        }
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
