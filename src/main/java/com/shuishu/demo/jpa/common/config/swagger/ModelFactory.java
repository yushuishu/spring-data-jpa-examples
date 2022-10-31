package com.shuishu.demo.jpa.common.config.swagger;


import com.google.common.base.Optional;
import springfox.documentation.schema.Model;
import springfox.documentation.schema.ModelProperty;

import java.util.Map;
import java.util.Objects;

/**
 * @author shuishu
 * @date 2022/3/22 14:47
 */
@SuppressWarnings("all")
public class ModelFactory {
    public static Optional<Model> newInstance(Model oldModel, String id, String name) {
        if (Objects.isNull(oldModel)) {
            Optional.absent();
        }
        // 创建新的对象模型
        Model newModel = new Model(
                id,
                name,
                oldModel.getType(),
                oldModel.getQualifiedType(),
                oldModel.getProperties(),
                oldModel.getDescription(),
                oldModel.getBaseModel(),
                oldModel.getDiscriminator(),
                oldModel.getSubTypes(),
                oldModel.getExample(),
                oldModel.getXml());
        return Optional.of(newModel);
    }

    public static Optional<Model> newInstance(Model oldModel, String id, String name,
                                              Map<String, ModelProperty> properties) {
        if (Objects.isNull(oldModel)) {
            Optional.absent();
        }
        // 创建新的对象模型
        Model newCheckModel = new Model(
                id,
                name,
                oldModel.getType(),
                oldModel.getQualifiedType(),
                properties,
                oldModel.getDescription(),
                oldModel.getBaseModel(),
                oldModel.getDiscriminator(),
                oldModel.getSubTypes(),
                oldModel.getExample(),
                oldModel.getXml());
        return Optional.of(newCheckModel);
    }
}
