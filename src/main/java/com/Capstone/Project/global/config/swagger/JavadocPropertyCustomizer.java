package com.Capstone.Project.global.config.swagger;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.JavaType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.core.util.AnnotationsUtils;
import io.swagger.v3.oas.models.media.Schema;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springdoc.core.providers.JavadocProvider;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@ConditionalOnExpression("${springdoc.api-docs.enabled:true}")
@RequiredArgsConstructor
public class JavadocPropertyCustomizer implements ModelConverter {

    private final JavadocProvider javadocProvider;
    private final ObjectMapperProvider objectMapperProvider;
    @Override
    public Schema resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
        if (chain.hasNext()) {
            JavaType javaType = objectMapperProvider.jsonMapper().constructType(type.getType());
            if (javaType != null) {
                Class<?> cls = javaType.getRawClass();
                Schema<?> resolvedSchema = chain.next().resolve(type, context, chain);
                List<Field> fields = FieldUtils.getAllFieldsList(cls);
                if (!CollectionUtils.isEmpty(fields)) {
                    if (!type.isSchemaProperty()) {
                        Schema existingSchema = context.resolve(type);
                        setJavadocDescription(cls, fields, existingSchema);
                    } else if (resolvedSchema != null && resolvedSchema.get$ref() != null && resolvedSchema.get$ref().contains(AnnotationsUtils.COMPONENTS_REF)) {
                        String schemaName = resolvedSchema.get$ref().substring(21);
                        Schema existingSchema = context.getDefinedModels().get(schemaName);
                        setJavadocDescription(cls, fields, existingSchema);
                    }
                }
                return resolvedSchema;
            }
        }
        return null;
    }

    private void setJavadocDescription(Class<?> cls, List<Field> fields, Schema existingSchema) {
        if (existingSchema != null) {
            if (StringUtils.isBlank(existingSchema.getDescription())) {
                existingSchema.setDescription(javadocProvider.getClassJavadoc(cls));
            }
            Map<String, Schema> properties = existingSchema.getProperties();
            if (!CollectionUtils.isEmpty(properties))
                properties.entrySet().stream()
                        .filter(stringSchemaEntry -> StringUtils.isBlank(stringSchemaEntry.getValue().getDescription()))
                        .forEach(stringSchemaEntry -> {
                            Optional<Field> optionalField = fields.stream().filter(field1 -> field1.getName().equals(stringSchemaEntry.getKey())).findAny();
                            optionalField.ifPresent(field -> {
                                String fieldJavadoc = javadocProvider.getFieldJavadoc(field);
                                if (StringUtils.isNotBlank(fieldJavadoc))
                                    stringSchemaEntry.getValue().setDescription(fieldJavadoc);
                            });
                        });
            fields.stream().filter(f -> f.isAnnotationPresent(JsonUnwrapped.class))
                    .forEach(f -> setJavadocDescription(f.getType(), FieldUtils.getAllFieldsList(f.getType()), existingSchema));

        }
    }
}
