package com.denizcanbagdatlioglu.self.wiki.domain.entity;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;

public class Symbol extends Figure {

    private Symbol(ID id, String name, String brief, String description) {
        super(id, name, brief, description);
    }

    public static FigureBuilder<Symbol> builder() {
        return new Figure.FigureBuilder<Symbol>(Symbol.class);
    }

}
