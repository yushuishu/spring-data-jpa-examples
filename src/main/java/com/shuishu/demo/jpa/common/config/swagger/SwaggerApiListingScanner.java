package com.shuishu.demo.jpa.common.config.swagger;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiListingBuilder;
import springfox.documentation.schema.Model;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.ApiListingContext;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spi.service.contexts.RequestMappingContext;
import springfox.documentation.spring.web.paths.PathMappingAdjuster;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.scanners.ApiDescriptionReader;
import springfox.documentation.spring.web.scanners.ApiListingScanner;
import springfox.documentation.spring.web.scanners.ApiListingScanningContext;
import springfox.documentation.spring.web.scanners.ApiModelReader;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.google.common.collect.FluentIterable.from;
import static com.shuishu.demo.jpa.common.config.swagger.SwaggerResourceGroups.belongsTo;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.concat;
import static java.util.stream.StreamSupport.stream;
import static springfox.documentation.builders.BuilderDefaults.nullToEmptyList;
import static springfox.documentation.spi.service.contexts.Orderings.methodComparator;
import static springfox.documentation.spi.service.contexts.Orderings.resourceGroupComparator;
import static springfox.documentation.spring.web.paths.Paths.ROOT;

/**
 * @author ：shuishu
 * @date   ：2022/3/22 14:53
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */
@Primary
@Component
@ConditionalOnProperty(prefix = "knife4j", name = "enable", havingValue = "true")
@SuppressWarnings("all")
public class SwaggerApiListingScanner extends ApiListingScanner {
    private final ApiDescriptionReader apiDescriptionReader;
    private final ApiModelReader apiModelReader;
    private final DocumentationPluginsManager pluginsManager;

    @Resource
    private SwaggerModelExpander swaggerModelExpander;

    public SwaggerApiListingScanner(ApiDescriptionReader apiDescriptionReader, ApiModelReader apiModelReader, DocumentationPluginsManager pluginsManager) {
        super(apiDescriptionReader, apiModelReader, pluginsManager);
        this.apiDescriptionReader = apiDescriptionReader;
        this.apiModelReader = apiModelReader;
        this.pluginsManager = pluginsManager;
    }

    static Optional<String> longestCommonPath(List<ApiDescription> apiDescriptions) {
        List<String> commons = new ArrayList<>();
        if (null == apiDescriptions || apiDescriptions.isEmpty()) {
            return empty();
        }
        List<String> firstWords = urlParts(apiDescriptions.get(0));

        for (int position = 0; position < firstWords.size(); position++) {
            String word = firstWords.get(position);
            boolean allContain = true;
            for (int i = 1; i < apiDescriptions.size(); i++) {
                List<String> words = urlParts(apiDescriptions.get(i));
                if (words.size() < position + 1 || !words.get(position).equals(word)) {
                    allContain = false;
                    break;
                }
            }
            if (allContain) {
                commons.add(word);
            }
        }
        return of("/" + commons.stream()
                .filter(Objects::nonNull)
                .collect(joining("/")));
    }

    private static List<String> urlParts(ApiDescription apiDescription) {
        return Stream.of(apiDescription.getPath().split("/"))
                .filter(((Predicate<String>) String::isEmpty).negate())
                .map(String::trim)
                .collect(toList());
    }

    private final static String PARAMETERS_FIELD_NAME = "parameters";

    @Override
    public Map<String, List<ApiListing>> scan(ApiListingScanningContext context) {
        final Map<String, List<ApiListing>> apiListingMap = new HashMap<>();
        int position = 0;

        Map<ResourceGroup, List<RequestMappingContext>> requestMappingsByResourceGroup = context
                .getRequestMappingsByResourceGroup();
        Collection<ApiDescription> additionalListings = pluginsManager.additionalListings(context);
        Set<ResourceGroup> allResourceGroups =
                concat(
                        stream(
                                SwaggerResourceGroups.collectResourceGroups(additionalListings).spliterator(),
                                false),
                        requestMappingsByResourceGroup.keySet().stream())
                        .collect(toSet());

        List<SecurityReference> securityReferences = new ArrayList<>();

        Map<String, Set<Model>> globalModelMap = new HashMap<>();
        for (final ResourceGroup resourceGroup : sortedByName(allResourceGroups)) {

            DocumentationContext documentationContext = context.getDocumentationContext();
            Set<String> produces = new LinkedHashSet<>(documentationContext.getProduces());
            Set<String> consumes = new LinkedHashSet<>(documentationContext.getConsumes());
            String host = documentationContext.getHost();
            Set<String> protocols = new LinkedHashSet<>(documentationContext.getProtocols());
            Set<ApiDescription> apiDescriptions = new HashSet<>();


            final Map<String, Model> models = new LinkedHashMap<>();
            List<RequestMappingContext> requestMappings = nullToEmptyList(requestMappingsByResourceGroup.get(resourceGroup));
            for (RequestMappingContext each : sortedByMethods(requestMappings)) {
                Map<String, Set<Model>> currentModelMap = apiModelReader.read(each.withKnownModels(globalModelMap));
                currentModelMap.values().forEach(list -> {
                    for (Model model : list) {
                        models.put(
                                model.getName(),
                                model);
                    }
                });
                globalModelMap.putAll(currentModelMap);
                List<ApiDescription> apiDescriptionList = apiDescriptionReader.read(each.withKnownModels(currentModelMap));
                if (each.findAnnotation(ApiGroup.class).isPresent()) {
                    apiDescriptions.addAll(swaggerModelExpander.getNewApiDescriptionList(resourceGroup, each, models, apiDescriptionList));
                } else {
                    apiDescriptions.addAll(apiDescriptionList);
                }
            }

            List<ApiDescription> additional = additionalListings.stream()
                    .filter(belongsTo(resourceGroup.getGroupName()).and(onlySelectedApis(documentationContext))).collect(toList());

            apiDescriptions.addAll(additional);

            List<ApiDescription> sortedApis = apiDescriptions.stream()
                    .sorted(documentationContext.getApiDescriptionOrdering()).collect(toList());

            String resourcePath = new SwaggerResourcePathWrapper(resourceGroup)
                    .resourcePath()
                    .orElse(
                            longestCommonPath(sortedApis)
                                    .orElse(null));


            PathAdjuster adjuster = new PathMappingAdjuster(documentationContext);
            ApiListingBuilder apiListingBuilder = new ApiListingBuilder(context.apiDescriptionOrdering())
                    .apiVersion(documentationContext.getApiInfo().getVersion())
                    .basePath(adjuster.adjustedPath(ROOT))
                    .resourcePath(resourcePath)
                    .produces(produces)
                    .consumes(consumes)
                    .host(host)
                    .protocols(protocols)
                    .securityReferences(securityReferences)
                    .apis(sortedApis)
                    .models(models)
                    .position(position++)
                    .availableTags(documentationContext.getTags());

            ApiListingContext apiListingContext = new ApiListingContext(
                    context.getDocumentationType(),
                    resourceGroup,
                    apiListingBuilder);
            apiListingMap.putIfAbsent(
                    resourceGroup.getGroupName(),
                    new LinkedList<>());
            apiListingMap.get(resourceGroup.getGroupName()).add(pluginsManager.apiListing(apiListingContext));
        }
        return apiListingMap;
    }

    private Predicate<ApiDescription> onlySelectedApis(final DocumentationContext context) {
        return input -> context.getApiSelector().getPathSelector().test(input.getPath());
    }

    private Iterable<RequestMappingContext> sortedByMethods(List<RequestMappingContext> contexts) {
        return contexts.stream().sorted(methodComparator()).collect(toList());
    }

    private Iterable<ResourceGroup> sortedByName(Set<ResourceGroup> resourceGroups) {
        return from(resourceGroups).toSortedList(resourceGroupComparator());
    }

    private void setFieldValue(Object object, String fieldName, Object fieldValue) {
        if (Objects.isNull(object) || StringUtils.isEmpty(fieldName)) {
            return;
        }

        Class<?> supperClass = object.getClass();
        do {
            try {
                Field[] parameterList = supperClass.getDeclaredFields();
                if (!ObjectUtils.isEmpty(parameterList)) {
                    for (Field parameter : parameterList) {
                        if (Objects.equals(fieldName, parameter.getName())) {
                            parameter.setAccessible(true);
                            parameter.set(object, fieldValue);
                            break;
                        }
                    }
                }
            } catch (IllegalAccessException ignored) {

            }
            supperClass = supperClass.getSuperclass();
        } while (Objects.nonNull(supperClass));
    }
}
