package org.protege.editor.core.ui.tabbedpane;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.View;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Mar 23, 2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class CloseableTabbedPaneUI extends BasicTabbedPaneUI {

    public static final int TAB_HEIGHT = 20;

    public static final Color TEXT_COLOR = new Color(84, 84, 84);

    public static final Color SEL_TEXT_COLOR = new Color(32, 32, 32);

    public static final String CLOSE_SYMBOL = "\u00D7";

    public static final int TAB_PADDING = 5;

    public static final int CLOSE_SYMBOL_PADDING = 3;


    public static final Color TAB_TOP_COLOR = new Color(197, 197, 197);

    public static final Color TAB_BOTTOM_COLOR = new Color(189, 189, 189);

    public static final Color[] tabColorGradient = new Color[]{
            TAB_TOP_COLOR,
            TAB_BOTTOM_COLOR
    };

    public static final float[] tabGradient = new float[]{0.0f, 1.0f};

    public static final Color SEL_TAB_TOP_COLOR = new Color(220, 220, 220);

    public static final Color SEL_TAB_BOTTOM_COLOR = new Color(213, 213, 213);

    public static final Color[] selTabColorGradient = new Color[]{
            SEL_TAB_TOP_COLOR,
            SEL_TAB_BOTTOM_COLOR
    };

    public static final Color TAB_BORDER_COLOR = new Color(184, 184, 184);

    public static final Insets emptyInsets = new Insets(0, 0, 0, 0);

    public static final Font OS_X_FONT = new Font("Helvetica Neue", Font.PLAIN, 12);

    public static enum TabClosability {
        CLOSABLE,
        NOT_CLOSEABLE
    }

    private final TabClosability tabClosability;

    private final TabCloseHandler closeHandler;

    public CloseableTabbedPaneUI(TabClosability tabClosability, TabCloseHandler closeHandler) {
        this.tabClosability = tabClosability;
        this.closeHandler = closeHandler;
    }

    protected void installDefaults() {
        super.installDefaults();
        tabAreaInsets.left = 0;
        selectedTabPadInsets = emptyInsets;
        tabInsets = selectedTabPadInsets;
        if (UIManager.getLookAndFeel().getName().contains("OS X")) {
            tabPane.setFont(OS_X_FONT);
        }
    }

    @Override
    protected int getTabRunOverlay(int tabPlacement) {
        // All runs do not overlap.
        return 0;
    }

    @Override
    protected void layoutLabel(int tabPlacement, FontMetrics metrics, int tabIndex, String title, Icon icon, Rectangle tabRect, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        super.layoutLabel(tabPlacement, metrics, tabIndex, title, icon, tabRect, iconRect, textRect, isSelected);
        textRect.x = tabRect.x + TAB_PADDING;
    }

    @Override
    protected void installListeners() {
        super.installListeners();
        tabPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int tabIndex = tabForCoordinate(tabPane, e.getX(), e.getY());
                if(tabIndex != -1) {
                    handleTabClicked(e, tabIndex);
                }
            }
        });
    }

    private void handleTabClicked(MouseEvent e, int tabIndex) {
        if(tabClosability == TabClosability.NOT_CLOSEABLE) {
            return;
        }
        if(isInCloseRect(e, tabIndex)) {
            handleCloseTabAt(tabIndex);
        }
    }

    private boolean isInCloseRect(MouseEvent e, int tabIndex) {
        FontMetrics fm = getFontMetrics();
        int width = (int) fm.getStringBounds(CLOSE_SYMBOL, tabPane.getGraphics()).getWidth() + TAB_PADDING;
        Rectangle rectangle = getTabBounds(tabPane, tabIndex);
        return rectangle.getX() + rectangle.getWidth() - e.getX() < width;
    }

    private void handleCloseTabAt(int tabIndex) {
        if(closeHandler.shouldCloseTab(tabIndex, tabPane)) {
            closeHandler.handleCloseTab(tabIndex, tabPane);
        }
    }

    @Override
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        Graphics2D g2 = (Graphics2D) g;
        Object antiAliasing = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (isSelected) {
            g.setColor(SEL_TEXT_COLOR);
        }
        else {
            g.setColor(TEXT_COLOR);
        }
        g.drawString(title, textRect.x, textRect.y + metrics.getAscent());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAliasing);
    }

    @Override
    protected View getTextViewForTab(int tabIndex) {
        return null;
    }

    protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect,
                            Rectangle textRect) {
        super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
    }


    protected Insets getContentBorderInsets(int tabPlacement) {
        return emptyInsets;
    }


    protected Insets getTabInsets(int tabPlacement, int tabIndex) {
        return emptyInsets;
    }


    protected Insets getSelectedTabPadInsets(int tabPlacement) {
        return emptyInsets;
    }


    protected Insets getTabAreaInsets(int tabPlacement) {
        return emptyInsets;
    }


    protected int getTabLabelShiftX(int tabPlacement, int tabIndex, boolean isSelected) {
        return 0;
    }


    protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
        return 0;
    }

    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
                                  boolean isSelected) {
    }

    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex,
                                       Rectangle iconRect, Rectangle textRect, boolean isSelected) {
    }


    public Rectangle getTabBounds(JTabbedPane pane, int i) {
        Rectangle tabBounds = super.getTabBounds(pane, i);
        tabBounds.height = TAB_HEIGHT;
        return tabBounds;
    }

    @Override
    protected int calculateMaxTabHeight(int tabPlacement) {
        return TAB_HEIGHT;
    }

    @Override
    protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
        return TAB_HEIGHT * horizRunCount;
    }

    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        if (tabPane.getComponentCount() > 1) {
            return TAB_HEIGHT;
        }
        else {
            return 0;
        }
    }


    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        int defaultWidth = super.calculateTabWidth(tabPlacement, tabIndex, metrics) + TAB_PADDING * 2;
        if(tabClosability == TabClosability.CLOSABLE) {
            return defaultWidth + getFontMetrics().stringWidth(CLOSE_SYMBOL) + CLOSE_SYMBOL_PADDING * 2;
        }
        else {
            return defaultWidth;
        }
    }


    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
                                      boolean isSelected) {
        Graphics2D g2 = (Graphics2D) g;
        if(isSelected) {
            g2.setPaint(new LinearGradientPaint(
                    x, y, x, y + h,
                    tabGradient,
                    selTabColorGradient));
        }
        else {
            g2.setPaint(new LinearGradientPaint(x, y, x, y + h,
                    tabGradient,
                    tabColorGradient));
        }
        g.fillRect(x,  y, w, h);
        if (isSelected) {
            g.setColor(SEL_TEXT_COLOR);
        }
        else {
            g.setColor(TEXT_COLOR);
        }
        if(tabClosability == TabClosability.CLOSABLE) {
            FontMetrics fm = getFontMetrics();
            int width = (int) fm.getStringBounds(CLOSE_SYMBOL, g).getWidth();
            g.drawString(CLOSE_SYMBOL, x + w - width - TAB_PADDING, y + (h - fm.getHeight()) / 2 + fm.getAscent());
        }
        g.setColor(TAB_BORDER_COLOR);
        g.drawRect(x, y, w, h);
    }


    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        // No border
    }


    protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w,
                                             int h) {
        // No border
    }


    protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w,
                                              int h) {
        // No border
    }


    protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w,
                                                int h) {
        // No border
    }


    protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w,
                                               int h) {
        // No border
    }

    @Override
    protected LayoutManager createLayoutManager() {
        return new BasicTabbedPaneUI.TabbedPaneLayout() {

            protected void normalizeTabRuns( int tabPlacement, int tabCount,
                                             int start, int max ) {
                if ( tabPlacement == TOP || tabPlacement == BOTTOM ) {
                    super.normalizeTabRuns( tabPlacement, tabCount, start, max );
                }
            }

            // Don't rotate runs!
            protected void rotateTabRuns( int tabPlacement, int selectedRun ) {
            }

            // Don't pad selected tab
            protected void padSelectedTab( int tabPlacement, int selectedIndex ) {
            }
        };
    }
}