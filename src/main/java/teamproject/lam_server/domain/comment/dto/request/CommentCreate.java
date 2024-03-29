package teamproject.lam_server.domain.comment.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import teamproject.lam_server.domain.comment.entity.ReviewComment;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.schedule.entity.Schedule;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreate {

    @NotBlank
    @Length(max = 1000)
    private String comment;

    private Long parentId;

    @Builder
    public CommentCreate(String comment, Long parentId) {
        this.comment = comment;
        if (parentId != null) {
            this.parentId = parentId;
        }
    }

    public ReviewComment toReviewEntity(Member member, Review review) {
        return ReviewComment.builder()
                .content(this.comment)
                .review(review)
                .member(member)
                .build();
    }

    public ReviewComment toReviewEntity(Member member, Review review, ReviewComment parent) {
        return ReviewComment.builder()
                .content(this.comment)
                .review(review)
                .member(member)
                .parent(parent).build();
    }

    public ScheduleComment toScheduleEntity(Member member, Schedule schedule) {
        return ScheduleComment.builder()
                .content(this.comment)
                .schedule(schedule)
                .member(member)
                .build();
    }

    public ScheduleComment toScheduleEntity(Member member, Schedule schedule, ScheduleComment parent) {
        return ScheduleComment.builder()
                .content(this.comment)
                .schedule(schedule)
                .member(member)
                .parent(parent)
                .build();
    }
}
