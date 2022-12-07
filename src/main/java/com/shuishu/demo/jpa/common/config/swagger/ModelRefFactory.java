package com.shuishu.demo.jpa.common.config.swagger;


import com.google.common.base.Optional;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.ModelReference;

import java.util.Objects;

/**
 * @author ：shuishu
 * @date   ：2022/3/22 14:50
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */
@SuppressWarnings("all")
public class ModelRefFactory {
    public static Optional<ModelReference> newInstance(ModelReference oldModelReference) {
        if (Objects.isNull(oldModelReference)) {
            return Optional.absent();
        }
        ModelReference itemType = null;
        if (oldModelReference.itemModel().isPresent()) {
            itemType = oldModelReference.itemModel().get();
        }
        ModelReference modelRef = new ModelRef(
                oldModelReference.getType(),
                oldModelReference.getTypeSignature().orElse(null),
                itemType,
                oldModelReference.getAllowableValues(),
                oldModelReference.isMap(),
                oldModelReference.getModelId().orElse(null));

        return Optional.of(modelRef);
    }

    public static Optional<ModelReference> newInstance(ModelReference oldModelReference, String type) {
        if (Objects.isNull(oldModelReference)) {
            return Optional.absent();
        }
        ModelReference itemType = null;
        if (oldModelReference.itemModel().isPresent()) {
            itemType = oldModelReference.itemModel().get();
        }
        ModelReference modelRef = new ModelRef(
                type,
                oldModelReference.getTypeSignature().orElse(null),
                itemType,
                oldModelReference.getAllowableValues(),
                oldModelReference.isMap(),
                oldModelReference.getModelId().orElse(null));

        return Optional.of(modelRef);
    }

    public static Optional<ModelReference> newInstance(ModelReference oldModelReference,
                                                       ModelReference itemModelReference) {
        if (Objects.isNull(oldModelReference)) {
            return Optional.absent();
        }

        ModelReference modelRef = new ModelRef(
                oldModelReference.getType(),
                oldModelReference.getTypeSignature().orElse(null),
                itemModelReference,
                oldModelReference.getAllowableValues(),
                oldModelReference.isMap(),
                oldModelReference.getModelId().orElse(null));

        return Optional.of(modelRef);
    }
}
