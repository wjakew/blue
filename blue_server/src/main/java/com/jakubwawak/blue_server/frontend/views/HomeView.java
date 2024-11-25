package com.jakubwawak.blue_server.frontend.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

/**
 * Object for rendering home view
 */
@Route("/welcome")
@RouteAlias(value = "/")
public class HomeView extends VerticalLayout {

    H1 header;
    Paragraph paragraph;

    /**
     * Constructor
     */
    public HomeView() {
        addClassName("page");
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        header = new H1("blue.");
        paragraph = new Paragraph("out of the");
        paragraph.addClassName("text");
    }

    /**
     * Function for preparing layout
     */
    void prepareLayout(){
        prepareComponents();
        add(paragraph,header);
    }

}
