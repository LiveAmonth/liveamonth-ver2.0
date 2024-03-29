package teamproject.lam_server.domain.interaction.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;

import static teamproject.lam_server.domain.interaction.entity.member.QFollower.follower;
import static teamproject.lam_server.domain.interaction.entity.review.QReviewLike.reviewLike;
import static teamproject.lam_server.domain.interaction.entity.schedule.QScheduleLike.scheduleLike;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.review.entity.QReview.review;
import static teamproject.lam_server.domain.schedule.entity.QSchedule.schedule;

@Repository
@RequiredArgsConstructor
public class InteractionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public boolean isMemberLikeSchedule(InteractionRequest request) {
        return queryFactory.selectOne()
                .from(scheduleLike)
                .leftJoin(scheduleLike.from, member)
                .leftJoin(scheduleLike.to, schedule)
                .where(
                        memberIdEq(request.getFrom()),
                        scheduleIdEq(request.getTo())
                ).fetchFirst() != null;
    }

    public boolean isMemberLikeReview(InteractionRequest request) {
        return queryFactory.selectOne()
                .from(reviewLike)
                .leftJoin(reviewLike.from, member)
                .leftJoin(reviewLike.to, review)
                .where(
                        memberIdEq(request.getFrom()),
                        reviewIdEq(request.getTo())

                ).fetchFirst() != null;
    }

    public boolean isMemberFollow(InteractionRequest request) {
        return queryFactory.selectOne()
                .from(follower)
                .leftJoin(follower.from, member)
                .leftJoin(follower.to, member)
                .where(
                        fromMemberIdEq(request.getFrom()),
                        toMemberIdEq(request.getTo())

                ).fetchFirst() != null;
    }

    private BooleanExpression fromMemberIdEq(Long memberId) {
        return memberId != null ? follower.from.id.eq(memberId) : null;
    }
    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? member.id.eq(memberId) : null;
    }
    private BooleanExpression toMemberIdEq(Long memberId) {
        return memberId != null ? follower.to.id.eq(memberId) : null;
    }

    private BooleanExpression scheduleIdEq(Long scheduleId) {
        return scheduleId != null ? schedule.id.eq(scheduleId) : null;
    }

    private BooleanExpression reviewIdEq(Long reviewId) {
        return reviewId != null ? review.id.eq(reviewId) : null;
    }
}
