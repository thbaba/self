package com.denizcanbagdatlioglu.self.wiki.domain.entity;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public abstract class Figure {

    private final ID id;

    private final String name;

    private final String brief;

    private final String description;

    protected Figure(ID id, String name, String brief, String description) {
        this.id = id;
        this.name = name;
        this.brief = brief;
        this.description = description;
    }

    public ID id() {
        return id;
    }

     public String name() {
        return name;
     }

    public String brief() {
        return brief;
    }

    public String description() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof Figure figure)) return false;
        return id.equals(figure.id) &&
                name.equals(figure.name) &&
                brief.equals(figure.brief) &&
                description.equals(figure.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brief, description);
    }

    public static class FigureBuilder<T> {

        private final Class<T> clazz;

        private ID id;

        private String name;

        private String brief;

        private String description;

        protected FigureBuilder(Class<T> clazz) {
            this.clazz = clazz;
        }

        public FigureBuilder<T> id(ID id) {
            this.id = id;
            return this;
        }

        public FigureBuilder<T> id(String id) {
            this.id = ID.of(id);
            return this;
        }

        public FigureBuilder<T> name(String name) {
            this.name = name;
            return this;
        }

        public FigureBuilder<T> brief(String brief) {
            this.brief = brief;
            return this;
        }

        public FigureBuilder<T> description(String description) {
            this.description = description;
            return this;
        }

        public T build() {
            if(Objects.isNull(id) || id.getValue().isBlank() || Objects.isNull(name) || name.isBlank())
                throw new IllegalStateException("ID and name should be provided.");
            try {
                Constructor<T> constructor = clazz.getDeclaredConstructor(ID.class, String.class, String.class, String.class);
                constructor.setAccessible(true);
                return constructor.newInstance(id, name, brief, description);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
