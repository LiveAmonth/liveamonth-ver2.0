package teamproject.lam_server.domain.review.dto.condition;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.constants.ReviewSearchType;

import java.util.Set;

@Getter
@Setter
@Builder
public class ReviewSearchCond {

    private String searchWord;
    private Set<String> tags;
    private ReviewSearchType type;
    private ReviewCategory category;
}
