package com.shuishu.demo.jpa.common.config.swagger;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ResourceGroup;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collection;
import java.util.Set;

import static com.google.common.collect.FluentIterable.from;
import static springfox.documentation.spi.service.contexts.Orderings.resourceGroupComparator;

/**
 * @author ：shuishu
 * @date   ：2022/3/22 14:53
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @Description -
 */
@SuppressWarnings("all")
public class SwaggerResourceGroups {
    private SwaggerResourceGroups() {
        throw new UnsupportedOperationException();
    }

    static Iterable<ResourceGroup> collectResourceGroups(Collection<ApiDescription> apiDescriptions) {
        return from(apiDescriptions)
                .transform(toResourceGroups());
    }

    static Iterable<ResourceGroup> sortedByName(Set<ResourceGroup> resourceGroups) {
        return from(resourceGroups).toSortedList(resourceGroupComparator());
    }

    static Predicate<ApiDescription> belongsTo(final String groupName) {
        return new Predicate<ApiDescription>() {
            @Override
            public boolean apply(ApiDescription input) {
                return !input.getGroupName().isPresent()
                        || groupName.equals(input.getGroupName().get());
            }
        };
    }

    private static Function<ApiDescription, ResourceGroup> toResourceGroups() {
        return new Function<ApiDescription, ResourceGroup>() {
            @Override
            public ResourceGroup apply(ApiDescription input) {
                return new ResourceGroup(
                        input.getGroupName().orElse(Docket.DEFAULT_GROUP_NAME),
                        null);
            }
        };
    }
}
