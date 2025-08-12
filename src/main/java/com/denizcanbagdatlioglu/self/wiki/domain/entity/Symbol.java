package com.denizcanbagdatlioglu.self.wiki.domain.entity;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;

public class Symbol extends Feature {

    private Symbol(ID id, String name, String brief, String description) {
        super(id, name, brief, description);
    }

    public static FeatureBuilder<Symbol> builder() {
        return new Feature.FeatureBuilder<Symbol>(Symbol.class);
    }

}
