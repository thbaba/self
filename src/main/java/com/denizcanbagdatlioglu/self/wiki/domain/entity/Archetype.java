package com.denizcanbagdatlioglu.self.wiki.domain.entity;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;

public class Archetype extends Figure {

    private Archetype(ID id, String name, String brief, String description) {
        super(id, name, brief, description);
    }

    public static Figure.FigureBuilder<Archetype> builder() {
        return new Figure.FigureBuilder<Archetype>(Archetype.class);
    }

}
