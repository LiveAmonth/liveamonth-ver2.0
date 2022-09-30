package teamproject.lam_server.domain.review.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.global.dto.response.ProfileCardResponse;

@Data
@NoArgsConstructor
public class SimpleReviewResponse {

    private ProfileCardResponse member;

    private String title;

    private String content;

    private long viewCount;

    public SimpleReviewResponse(Review review) {
        this.member = ProfileCardResponse.of(review.getMember());
        this.title = review.getTitle();
        this.content = review.getContent();
        this.viewCount = review.getViewCount();
    }
}
