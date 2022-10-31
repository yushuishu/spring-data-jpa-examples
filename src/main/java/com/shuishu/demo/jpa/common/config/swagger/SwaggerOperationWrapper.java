package com.shuishu.demo.jpa.common.config.swagger;


import springfox.documentation.schema.ModelReference;
import springfox.documentation.service.Operation;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;

import java.util.List;
import java.util.Set;

/**
 * @author shuishu
 * @date 2022/3/22 14:57
 */
@SuppressWarnings("all")
public class SwaggerOperationWrapper {
    private final static String PARAMETERS_FIELD_NAME = "parameters";
    private final static String RESPONSE_MODEL_FIELD_NAME = "responseModel";
    private final static String RESPONSE_MESSAGES_FIELD_NAME = "responseMessages";

    private Operation operation;

    private SwaggerOperationWrapper(Operation operation) {
        this.operation = operation;
    }

    public static SwaggerOperationWrapper swaggerOperationWrapper(Operation operation) {
        return new SwaggerOperationWrapper(operation);
    }

    public Operation getOperation() {
        return this.operation;
    }

    public void setParameters(List<Parameter> parameters) {
        SwaggerObjectUtil.setFieldValue(this.operation, PARAMETERS_FIELD_NAME, parameters);
    }

    public void setResponseModel(ModelReference responseModel) {
        SwaggerObjectUtil.setFieldValue(this.operation, RESPONSE_MODEL_FIELD_NAME, responseModel);
    }

    public void setResponseMessages(Set<ResponseMessage> newResponseMessages) {
        SwaggerObjectUtil.setFieldValue(this.operation, RESPONSE_MESSAGES_FIELD_NAME, newResponseMessages);
    }
}
