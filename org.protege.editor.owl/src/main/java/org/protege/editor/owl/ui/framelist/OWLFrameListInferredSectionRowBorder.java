package org.protege.editor.owl.ui.framelist;

import javax.swing.border.Border;
import java.awt.*;
/*
 * Copyright (C) 2007, University of Manchester
 *
 *
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Dec-2007<br><br>
 */
public class OWLFrameListInferredSectionRowBorder implements Border {

    private Stroke stroke = new BasicStroke(1.0f,
                                            BasicStroke.CAP_ROUND,
                                            BasicStroke.JOIN_ROUND,
                                            1.0f,
                                            new float[]{3.0f, 3.0f},
                                            1.0f);


    /**
     * Paints the border for the specified component with the specified
     * position and size.
     * @param c      the component for which this border is being painted
     * @param g      the paint graphics
     * @param x      the x position of the painted border
     * @param y      the y position of the painted border
     * @param width  the width of the painted border
     * @param height the height of the painted border
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Color oldColor = g.getColor();
        Graphics2D g2 = (Graphics2D) g;
        Stroke oldStroke = g2.getStroke();
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(stroke);
        g2.drawRect(x, y, width, height);
        g2.setColor(oldColor);
        g2.setStroke(oldStroke);
    }


    /**
     * Returns the insets of the border.
     * @param c the component for which this border insets value applies
     */
    public Insets getBorderInsets(Component c) {
        return new Insets(1, 1, 1, 1);
    }


    /**
     * Returns whether or not the border is opaque.  If the border
     * is opaque, it is responsible for filling in it's own
     * background when painting.
     */
    public boolean isBorderOpaque() {
        return false;
    }
}
