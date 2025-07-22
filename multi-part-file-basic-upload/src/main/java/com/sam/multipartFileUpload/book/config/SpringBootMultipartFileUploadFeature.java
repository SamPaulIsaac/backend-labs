package com.sam.multipartFileUpload.book.config;

import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum SpringBootMultipartFileUploadFeature implements Feature {
    @Label("CSV READER")
    CSV_READER,
    @Label("INPUT STREAM")
    INPUT_STREAM;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }
}

