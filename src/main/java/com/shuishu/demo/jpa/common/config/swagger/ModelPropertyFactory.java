package com.shuishu.demo.jpa.common.config.swagger;


import com.google.common.base.Optional;
import springfox.documentation.schema.ModelProperty;

import java.util.Objects;

/**
 * @author shuishu
 * @date 2022/3/22 14:48
 */
@SuppressWarnings("all")
public class ModelPropertyFactory {
    public static Optional<ModelProperty> newInstance(ModelProperty oldModelProperty) {
        return newInstance(oldModelProperty, oldModelProperty.isRequired());
    }

    public static Optional<ModelProperty> newInstance(ModelProperty oldModelProperty, boolean isRequired) {
        if (Objects.isNull(oldModelProperty)) {
            return Optional.absent();
        }
        ModelProperty newModelProperty = new ModelProperty(
                oldModelProperty.getName(),
                oldModelProperty.getType(),
                oldModelProperty.getQualifiedType(),
                oldModelProperty.getPosition(),
                isRequired,
                oldModelProperty.isHidden(),
                oldModelProperty.isReadOnly(),
                oldModelProperty.isAllowEmptyValue(),
                oldModelProperty.getDescription(),
                oldModelProperty.getAllowableValues(),
                oldModelProperty.getExample(),
                oldModelProperty.getPattern(),
                oldModelProperty.getDefaultValue(),
                oldModelProperty.getXml(),
                oldModelProperty.getVendorExtensions()
        );
        return Optional.of(newModelProperty);
    }
}
