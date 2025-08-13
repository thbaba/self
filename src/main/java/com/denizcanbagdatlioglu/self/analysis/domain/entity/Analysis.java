package com.denizcanbagdatlioglu.self.analysis.domain.entity;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;

public class Analysis {
    
    private final ID id;
    
    private final String insight;
    
    private final String analysis;

    private Analysis(ID id, String insight, String analysis) {
        this.id = id;
        this.insight = insight;
        this.analysis = analysis;
    }

    public ID id() {
        return id;
    }

    public String insight() {
        return insight;
    }

    public String analysis() {
        return analysis;
    }


    public static AnalysisBuilder builder() {
        return new AnalysisBuilder();
    }

    public static class AnalysisBuilder {
        private ID id;
        private String insight;
        private String analysis;

        private AnalysisBuilder() {
        }

        public AnalysisBuilder id(ID id) {
            this.id = id;
            return this;
        }

        public AnalysisBuilder insight(String insight) {
            this.insight = insight;
            return this;
        }

        public AnalysisBuilder analysis(String analysis) {
            this.analysis = analysis;
            return this;
        }

        public Analysis build() {
            return new Analysis(id, insight, analysis);
        }
    }


}
