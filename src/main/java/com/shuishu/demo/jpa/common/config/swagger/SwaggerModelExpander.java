package com.shuishu.demo.jpa.common.config.swagger;


import com.google.common.base.Optional;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import springfox.documentation.schema.Model;
import springfox.documentation.schema.ModelProperty;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.Operation;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResourceGroup;
import springfox.documentation.spi.service.contexts.RequestMappingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：shuishu
 * @date   ：2022/3/22 14:56
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */
@Component
@SuppressWarnings("all")
public class SwaggerModelExpander {
    public enum ApiModelTypeEnum {

        /**
         * 请求类型
         */
        REQUEST("request"),

        /**
         * 响应类型
         */
        RESPONSE("response");

        private String modelType;

        ApiModelTypeEnum(String modelType) {
            this.modelType = modelType;
        }

        public String getModelType() {
            return modelType;
        }
    }

    private class EnhanceParameter {
        /**
         * 请求路径
         */
        private String path;
        /**
         * 请求方式
         */
        private String uniqueid;
        /**
         * 模型定义类型
         */
        private ApiModelTypeEnum modelType;
        /**
         * 请求映射上下文
         */
        private RequestMappingContext requestMappingContext;
        /**
         * SwaggerApi定义列表
         */
        private Map<String, Model> models;

        EnhanceParameter(RequestMappingContext requestMappingContext, Map<String, Model> models) {
            this.requestMappingContext = requestMappingContext;
            this.models = models;
        }

        String getPath() {
            return path;
        }

        void setPath(String path) {
            this.path = path;
        }

        public String getUniqueid() {
            return uniqueid;
        }

        public void setUniqueid(String uniqueid) {
            this.uniqueid = uniqueid;
        }

        ApiModelTypeEnum getModelType() {
            return modelType;
        }

        void setModelType(ApiModelTypeEnum modelType) {
            this.modelType = modelType;
        }

        void removeModelType() {
            this.modelType = null;
        }

        RequestMappingContext getRequestMappingContext() {
            return requestMappingContext;
        }

        Map<String, Model> getModels() {
            return models;
        }
    }

    public class ModelNameManager {


        /**
         * 下划线分隔符
         */
        private final static String UNDERLINE_SEPARATOR = "_";
        /**
         * 需要转换的字符
         */
        private final static String CHARACTERS_TO_REPLACE = "/";
        /**
         * 路径起始符号
         */
        private final static String PATH_START = "(";
        /**
         * 路径结束符号
         */
        private final static String PATH_END = ")";

        public String getModelPlusName(String path, String uniqueid, ApiModelTypeEnum apiModelTypeEnum,
                                       String originalModelName) {
            String apiModelType = Objects.nonNull(apiModelTypeEnum) ? apiModelTypeEnum.getModelType() : "";
            return originalModelName
                    + PATH_START
                    + path.replace(CHARACTERS_TO_REPLACE, UNDERLINE_SEPARATOR)
                    + UNDERLINE_SEPARATOR
                    + uniqueid
                    + UNDERLINE_SEPARATOR
                    + apiModelType
                    + PATH_END;
        }
    }

    private ModelNameManager modelNameManager = new ModelNameManager();

    public List<ApiDescription> getNewApiDescriptionList(ResourceGroup resourceGroup,
                                                         RequestMappingContext requestMappingContext, Map<String, Model> models,
                                                         List<ApiDescription> apiDescriptionList) {

        if (!requestMappingContext.findAnnotation(ApiGroup.class).isPresent()) {
            return apiDescriptionList;
        }

        EnhanceParameter enhanceParameter = new EnhanceParameter(requestMappingContext, models);

        for (ApiDescription apiDescription : apiDescriptionList) {
            List<Operation> operationList = apiDescription.getOperations();
            if (CollectionUtils.isEmpty(operationList)) {
                continue;
            }
            enhanceParameter.setPath(apiDescription.getPath());
            for (Operation operation : operationList) {
                enhanceParameter.setUniqueid(operation.getUniqueId());
                enhanceOperation(enhanceParameter, operation);
            }
        }
        return apiDescriptionList;
    }

    private Operation enhanceOperation(EnhanceParameter enhanceParameter, Operation operation) {
        SwaggerOperationWrapper operationWrapper = SwaggerOperationWrapper.swaggerOperationWrapper(operation);
        // 请求参数增强
        List<Parameter> parameterList = operation.getParameters();
        if (!CollectionUtils.isEmpty(parameterList)) {
            List<Parameter> newParameterList = new ArrayList<>(parameterList.size());
            for (Parameter parameter : parameterList) {
                newParameterList.add(enhanceParameter(enhanceParameter, parameter));
            }
            operationWrapper.setParameters(newParameterList);
        }

//        // 响应参数增强
//        ModelReference responseModel = enhanceModelReference(enhanceParameter, ApiModelTypeEnum.RESPONSE, operation.getResponseModel());
//        operationWrapper.setResponseModel(responseModel);
//
//        // 响应成功时的参数增强
//        operationWrapper.setResponseMessages(enhanceResponseMessage(operation.getResponseMessages(), responseModel));

        return operationWrapper.getOperation();
    }

    private Parameter enhanceParameter(EnhanceParameter enhanceParameter, Parameter parameter) {
        return new Parameter(parameter.getName(),
                parameter.getDescription(),
                parameter.getDefaultValue(),
                parameter.isRequired(),
                parameter.isAllowMultiple(),
                parameter.isAllowEmptyValue(),
                enhanceModelReference(enhanceParameter, ApiModelTypeEnum.REQUEST, parameter.getModelRef()),
                parameter.getType(),
                parameter.getAllowableValues(),
                parameter.getParamType(),
                parameter.getParamAccess(),
                parameter.isHidden(),
                parameter.getPattern(),
                parameter.getCollectionFormat(),
                parameter.getOrder(),
                parameter.getScalarExample(),
                parameter.getExamples(),
                parameter.getVendorExtentions()
        );
    }

    private ModelReference enhanceModelReference(EnhanceParameter enhanceParameter, ApiModelTypeEnum modelTypeEnum,
                                                 ModelReference modelReference) {
        try {
            enhanceParameter.setModelType(modelTypeEnum);
            Optional<ModelReference> newModelReferenceOptional = getNewModelReference(enhanceParameter, modelReference);
            if (newModelReferenceOptional.isPresent()) {
                return newModelReferenceOptional.get();
            }
        } finally {
            enhanceParameter.removeModelType();
        }
        return modelReference;
    }

    private Optional<ModelReference> getNewModelReference(
            EnhanceParameter enhanceParameter, ModelReference modelReference) {
        if (Objects.isNull(modelReference)) {
            return Optional.absent();
        }
        // 检查是否为特殊字段
        // 特殊字段
        if (modelReference.itemModel().isPresent()) {
            ModelReference specialItemModelReference = modelReference.itemModel().get();
            Optional<ModelReference> finalSpecialItemModelOptional = getSpecialItemModel(enhanceParameter, specialItemModelReference);
            if (finalSpecialItemModelOptional.isPresent()) {
                return ModelRefFactory.newInstance(modelReference, finalSpecialItemModelOptional.get());
            }
            // 普通字段
        } else {
            String type = modelReference.getType();
            Optional<Model> modelOptional = getAndCreaeMtodel(enhanceParameter, type);
            if (modelOptional.isPresent()) {
                return ModelRefFactory.newInstance(modelReference, modelOptional.get().getId());
            }
        }
        return Optional.of(modelReference);
    }

    private Optional<ModelReference> getSpecialItemModel(
            EnhanceParameter enhanceParameter, ModelReference specialItemModelReference) {
        ModelReference newModelReference = specialItemModelReference;
        Optional<ModelReference> newModelReferenceOptional = ModelRefFactory.newInstance(specialItemModelReference);
        if (newModelReferenceOptional.isPresent()) {
            newModelReference = newModelReferenceOptional.get();
        }
        java.util.Optional<ModelReference> modelReferenceOptional = specialItemModelReference.itemModel();
        if (modelReferenceOptional.isPresent()) {
            Optional<ModelReference> nextSpecialItemModelOptional
                    = getSpecialItemModel(enhanceParameter, modelReferenceOptional.get());
            if (nextSpecialItemModelOptional.isPresent()) {
                Optional<ModelReference> newNextModelReferenceOptional
                        = ModelRefFactory.newInstance(newModelReference, nextSpecialItemModelOptional.get());
                if (newNextModelReferenceOptional.isPresent()) {
                    newModelReference = newNextModelReferenceOptional.get();
                }
            }
        } else {
            Optional<Model> modelOptional = getAndCreaeMtodel(enhanceParameter, specialItemModelReference.getType());
            if (modelOptional.isPresent()) {
                Optional<ModelReference> newNextModelReferenceOptional
                        = ModelRefFactory.newInstance(newModelReference, modelOptional.get().getId());
                if (newNextModelReferenceOptional.isPresent()) {
                    newModelReference = newNextModelReferenceOptional.get();
                }
            }
        }
        return Optional.of(newModelReference);
    }

    private Optional<Model> getAndCreaeMtodel(EnhanceParameter enhanceParameter, String type) {
        Map<String, Model> models = enhanceParameter.getModels();
        String path = enhanceParameter.getPath();
        String uniqueid = enhanceParameter.getUniqueid();
        ApiModelTypeEnum modelType = enhanceParameter.getModelType();
        String newDefinitionsKey = modelNameManager.getModelPlusName(path, uniqueid, modelType, type);
        Model newDefinitionsModel = models.get(newDefinitionsKey);
        // 原定义列表中没有此定义
        if (Objects.isNull(newDefinitionsModel)) {
            Model oldModel = models.get(type);
            // 原定义中也没有定义, 忽略此定义类型
            if (Objects.isNull(oldModel)) {
                return Optional.absent();
            } else {
                return createModel(enhanceParameter, oldModel, newDefinitionsKey);
            }
        } else {
            return Optional.of(newDefinitionsModel);
        }
    }

    private Optional<Model> createModel(EnhanceParameter enhanceParameter, Model oldModel, String newDefinitionsKey) {
        Map<String, Model> models = enhanceParameter.getModels();
        // 创建一个新的定义
        // 创建新的对象模型(未检测字段列表)
        Optional<Model> newModelOptional = ModelFactory.newInstance(oldModel, newDefinitionsKey, newDefinitionsKey);
        if (newModelOptional.isPresent()) {
            models.put(newDefinitionsKey, newModelOptional.get());
        }
        // 新字段列表
        Map<String, ModelProperty> excludePropertyMap = new ConcurrentHashMap<>(5);
        Map<String, ModelProperty> includePropertyMap = new ConcurrentHashMap<>(5);
        // 遍历对象中属性
        Map<String, ModelProperty> oldPropertyMap = oldModel.getProperties();
        if (!CollectionUtils.isEmpty(oldPropertyMap)) {
            Class<?> erasedTypeClass = oldModel.getType().getErasedType();
            for (Map.Entry<String, ModelProperty> oldModelPropertyEntry : oldPropertyMap.entrySet()) {
                boolean requestRequired = isRequestRequired(enhanceParameter.getRequestMappingContext(), enhanceParameter.getModelType(),
                        erasedTypeClass, oldModelPropertyEntry.getKey());
                ;
                ModelProperty newModelProperty = getNewModelProperty(enhanceParameter, oldModelPropertyEntry.getValue(), requestRequired);

                if (isIncludeFieldFilter(enhanceParameter.getRequestMappingContext(), enhanceParameter.getModelType(), erasedTypeClass, oldModelPropertyEntry.getKey())) {
                    includePropertyMap.put(oldModelPropertyEntry.getKey(), newModelProperty);
                }

                if (!isExcludeFieldFilter(enhanceParameter.getRequestMappingContext(), enhanceParameter.getModelType(), erasedTypeClass, oldModelPropertyEntry.getKey())) {
                    excludePropertyMap.put(oldModelPropertyEntry.getKey(), newModelProperty);
                }
            }
            // 创建新的对象模型(包含所有新字段)
            if (includePropertyMap.size() > 0) {
                newModelOptional = ModelFactory.newInstance(oldModel, newDefinitionsKey, newDefinitionsKey, includePropertyMap);
            } else {
                newModelOptional = ModelFactory.newInstance(oldModel, newDefinitionsKey, newDefinitionsKey, excludePropertyMap);
            }
            if (newModelOptional.isPresent()) {
                models.put(newDefinitionsKey, newModelOptional.get());
            }
        }
        return newModelOptional;
    }

    private ModelProperty getNewModelProperty(EnhanceParameter enhanceParameter,
                                              ModelProperty oldModelProperty, boolean requestRequired) {
        ModelProperty newModelProperty = oldModelProperty;
        Optional<ModelProperty> newModelPropertyOptional = ModelPropertyFactory.newInstance(oldModelProperty, requestRequired);
        if (newModelPropertyOptional.isPresent()) {
            newModelProperty = newModelPropertyOptional.get();
            Optional<ModelReference> newModelReferenceOptional = getNewModelReference(enhanceParameter, oldModelProperty.getModelRef());
            if (newModelReferenceOptional.isPresent()) {
                newModelProperty.updateModelRef(input -> newModelReferenceOptional.get());
            }
        }
        return newModelProperty;
    }

    public boolean isRequestRequired(RequestMappingContext requestMappingContext, ApiModelTypeEnum apiModelTypeEnum, Class<?> erasedTypeClass, String fieldName) {
        if (requestMappingContext.findAnnotation(ApiGroup.class).isPresent()) {
            if (Objects.equals(apiModelTypeEnum, ApiModelTypeEnum.REQUEST)) {
                ApiGroup apiGroup = requestMappingContext.findAnnotation(ApiGroup.class).get();
                Class<?>[] apiGroups = apiGroup.value();
                ApiRequestFieldRequired apiRequestFieldRequired = SwaggerObjectUtil.getFieldAnnotation(erasedTypeClass, fieldName, ApiRequestFieldRequired.class);
                return !Objects.isNull(apiRequestFieldRequired) && SwaggerObjectUtil.isSingleEquals(apiRequestFieldRequired.groups(), apiGroups);
            }
        }
        return false;
    }

    public boolean isIncludeFieldFilter(RequestMappingContext requestMappingContext, ApiModelTypeEnum apiModelTypeEnum, Class<?> erasedTypeClass, String fieldName) {
        if (requestMappingContext.findAnnotation(ApiGroup.class).isPresent()) {
            if (Objects.equals(apiModelTypeEnum, ApiModelTypeEnum.REQUEST)) {
                ApiGroup apiGroup = requestMappingContext.findAnnotation(ApiGroup.class).get();
                Class<?>[] apiGroups = apiGroup.value();
                ApiRequestInclude apiRequestInclude = SwaggerObjectUtil.getFieldAnnotation(erasedTypeClass, fieldName, ApiRequestInclude.class);
                return !Objects.isNull(apiRequestInclude) && SwaggerObjectUtil.isSingleEquals(apiRequestInclude.groups(), apiGroups);
            }
        }
        return false;
    }

    public boolean isExcludeFieldFilter(RequestMappingContext requestMappingContext, ApiModelTypeEnum apiModelTypeEnum, Class<?> erasedTypeClass, String fieldName) {
        if (requestMappingContext.findAnnotation(ApiGroup.class).isPresent()) {
            ApiGroup apiGroup = requestMappingContext.findAnnotation(ApiGroup.class).get();
            Class<?>[] apiGroups = apiGroup.value();
            if (Objects.equals(apiModelTypeEnum, ApiModelTypeEnum.REQUEST)) {
                ApiRequestExclude apiRequestExclude = SwaggerObjectUtil.getFieldAnnotation(erasedTypeClass, fieldName, ApiRequestExclude.class);
                return !Objects.isNull(apiRequestExclude) && SwaggerObjectUtil.isSingleEquals(apiRequestExclude.groups(), apiGroups);
            }
        }
        return false;
    }
}
