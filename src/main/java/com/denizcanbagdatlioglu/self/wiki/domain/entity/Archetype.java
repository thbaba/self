package com.denizcanbagdatlioglu.self.wiki.domain.entity;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;

public class Archetype extends Feature {

    private Archetype(ID id, String name, String brief, String description) {
        super(id, name, brief, description);
    }

    public static Feature.FeatureBuilder<Archetype> builder() {
        return new Feature.FeatureBuilder<Archetype>(Archetype.class);
    }

}
