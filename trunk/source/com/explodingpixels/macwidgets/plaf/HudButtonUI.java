package com.explodingpixels.macwidgets.plaf;

import sun.swing.SwingUtilities2;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.awt.*;

/**
 * Creates a Heads Up Display (HUD) style button, similar to that seen in various iApps (e.g.
 * iPhoto).
 * <br>
 * <img src="../../../../../graphics/HUDButtonUI.png">
 */
public class HudButtonUI extends BasicButtonUI {

    private final HudPaintingUtils.Roundedness fRoundedness;

    private static final int TOP_AND_BOTTOM_MARGIN = 2;

    private static final int LEFT_AND_RIGHT_MARGIN = 16;

    /**
     * Creates a HUD style {@link javax.swing.plaf.ButtonUI} with full rounded edges.
     */
    public HudButtonUI() {
        this(HudPaintingUtils.Roundedness.ROUNDED_BUTTON);
    }

    /**
     * Creates a HUD style {@link javax.swing.plaf.ButtonUI} with the given edge rounded ness.
     *
     * @param roundedness the rounded style to use for the button edges.
     */
    public HudButtonUI(HudPaintingUtils.Roundedness roundedness) {
        fRoundedness = roundedness;
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);

        // TODO save original values.

        HudPaintingUtils.initHudComponent(b);
        b.setHorizontalTextPosition(AbstractButton.CENTER);
        int bottomMargin = TOP_AND_BOTTOM_MARGIN + HudPaintingUtils.getHudControlShadowSize(b);
        b.setBorder(BorderFactory.createEmptyBorder(TOP_AND_BOTTOM_MARGIN, LEFT_AND_RIGHT_MARGIN,
                bottomMargin, LEFT_AND_RIGHT_MARGIN));
    }

    @Override
    public void paint(Graphics g, JComponent c) {

        AbstractButton button = (AbstractButton) c;
        Graphics2D graphics = (Graphics2D) g.create();
        HudPaintingUtils.updateGraphisToPaintDisabledControlIfNecessary(graphics, button);

        int buttonHeight = button.getHeight() - HudPaintingUtils.getHudControlShadowSize(button);
        HudPaintingUtils.paintHudControlBackground(graphics, button, button.getWidth(),
                buttonHeight, fRoundedness);

        graphics.dispose();

        super.paint(g, c);
    }

    @Override
    protected void paintText(Graphics g, AbstractButton button, Rectangle textRect, String text) {
        FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(button, g);
        int mnemonicIndex = button.getDisplayedMnemonicIndex();

        HudPaintingUtils.updateGraphisToPaintDisabledControlIfNecessary((Graphics2D) g, button);

//        g.setColor(button.isEnabled()
//                ? button.getForeground() : HudPaintingUtils.FONT_DISABLED_COLOR);
        g.setColor(button.getForeground());
        BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, mnemonicIndex,
                textRect.x + getTextShiftOffset(),
                textRect.y + fontMetrics.getAscent() + getTextShiftOffset());
    }

}
