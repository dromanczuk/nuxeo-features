package org.nuxeo.opensocial.container.client.ui;

import org.nuxeo.opensocial.container.client.ui.enume.ColorsEnum;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * @author Stéphane Fourrier
 */
public class ColorsPanelWidget extends Composite implements HasClickHandlers,
        HasValue<String>, HasName {

    private HorizontalPanel panel;
    private Color selectedColor;
    private String name;

    public ColorsPanelWidget() {
        panel = new HorizontalPanel();

        for (ColorsEnum color : ColorsEnum.values()) {
            final Color colorWidget;
            colorWidget = new Color(color, false);

            colorWidget.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent arg0) {
                    if (selectedColor != null) {
                        selectedColor.setSelected(false);
                    }
                    colorWidget.setSelected(true);
                    selectedColor = colorWidget;
                }
            });

            panel.add(colorWidget);
        }

        initWidget(panel);
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String color) {
        Color colorWidget = getColor(color);

        if (selectedColor != null) {
            selectedColor.setSelected(false);
        }

        if (colorWidget == null) {
            colorWidget = getColor(ColorsEnum.TRANSPARENT.getCssColor());
            colorWidget.setSelected(true);
            selectedColor = colorWidget;
        } else {
            colorWidget.setSelected(true);
            selectedColor = colorWidget;
        }
    }

    private Color getColor(String color) {
        for (int i = 0; i < panel.getWidgetCount(); i++) {
            if (((Color) panel.getWidget(i)).getColorAsString()
                    .equals(color))
                return (Color) panel.getWidget(i);
        }
        return null;
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    public String getValue() {
        return getSelectedColor().getColorAsString();
    }

    public void setValue(String value) {
        setSelectedColor(value);
    }

    public void setValue(String value, boolean fireEvents) {
        setValue(value);
    }

    public HandlerRegistration addValueChangeHandler(
            ValueChangeHandler<String> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}