package teamproject.lam_server.paging;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import teamproject.lam_server.paging.sort.SortOption;
import teamproject.lam_server.paging.sort.SortPair;

import java.util.ArrayList;
import java.util.List;

public class PageableArgumentResolver implements HandlerMethodArgumentResolver {
    private final String PAGE = "page";
    private final String SIZE = "size";
    private final String SORT = "sort";
    private final String SEPARATE = ",";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(PageableDTO.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        List<SortPair<String, SortOption>> sorts = new ArrayList<>();

        final var sortArr = webRequest.getParameterMap().get(SORT);
        if (sortArr != null) {
            for (var v : sortArr) {
                String[] keywords = v.split(SEPARATE);
                sorts.add(
                        SortPair.of(
                                (keywords[0] + "_" + keywords[1]).toUpperCase(),
                                SortOption.valueOf(keywords[1].toUpperCase())
                        ));
            }
            SortPair defaultSort = SortPair.of("ID_DESC", SortOption.DESC);
            if (!sorts.contains(defaultSort)) sorts.add(defaultSort);
        }
        return PageableDTO.builder()
                .page(getValue(webRequest.getParameter(PAGE)))
                .size(getValue(webRequest.getParameter(SIZE)))
                .sorts(sorts)
                .build();
    }

    private Integer getValue(String param) {
        return param != null ? Integer.parseInt(param) : null;
    }
}
