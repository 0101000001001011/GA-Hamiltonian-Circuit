/*
 *  LinesComponent it's used to print the GUI
 *
 *  Not important :)
 *
 */
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.text.*;

public class LinesComponent extends JComponent{
    private int pathDistance;
    private int iteration;

    private static class Line{
        final int x1; 
        final int y1;
        final int x2;
        final int y2;   
        final int distance;

        public Line(int x1, int y1, int x2, int y2, int distance) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.distance = distance;
        }               
    }

    private final LinkedList<Line> lines = new LinkedList<Line>();

    public void addLine(int x1, int x2, int x3, int x4, int distance) {
        lines.add(new Line(x1,x2,x3,x4, distance));        
        repaint();
    }

    public void clearLines() {
        lines.clear();
        repaint();
    }
    
    public void setPathDistance(int pathDistance){
        this.pathDistance = pathDistance;
    }
    
    public void setIteration(int iteration){
        this.iteration = iteration;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        DecimalFormat dFormatter = new DecimalFormat("000");
        
        g.setColor(Color.BLACK);
        g.drawString("Best path: "+Integer.toString(this.pathDistance),780,560);
        String ite = dFormatter.format(this.iteration);
        g.drawString("Iteration: "+ite,780,580);
                
        for(final int[] city: CitiesMap.getCities()){
            g.setColor(Color.ORANGE);
            g.fillRect(city[0]-3, city[1]-3, 6, 6);
        }
        
        for (final Line line : lines) {
            g.setColor(Color.GREEN);
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
            
            g.setColor(Color.RED);
            String dist = dFormatter.format(line.distance);
            g.drawString(dist,((line.x1+line.x2)/2),((line.y1+line.y2)/2));
        }
    }
}
