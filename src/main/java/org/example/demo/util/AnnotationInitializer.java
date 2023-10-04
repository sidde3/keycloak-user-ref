package org.example.demo.util;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes("org.example.demo.annotation.AllowedRoles")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class AnnotationInitializer extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.err.printf("I am here, %s", this.getClass().getName());
        return false;
    }
}
