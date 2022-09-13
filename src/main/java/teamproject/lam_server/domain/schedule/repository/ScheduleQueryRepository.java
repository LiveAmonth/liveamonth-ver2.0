package teamproject.lam_server.domain.schedule.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;
import teamproject.lam_server.global.repository.BasicRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.schedule.entity.QSchedule.schedule;
import static teamproject.lam_server.domain.schedule.entity.QScheduleContent.scheduleContent;

@Repository
@RequiredArgsConstructor
public class ScheduleQueryRepository extends BasicRepository {

    private final JPAQueryFactory queryFactory;

    public Page<Schedule> search(ScheduleSearchCond cond, Pageable pageable) {
        List<Schedule> elements = getSearchElementsQuery(cond)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(mapToOrderSpec(pageable.getSort(), Schedule.class, schedule))
                .fetch();
        JPAQuery<Long> countQuery = getSearchCountQuery(cond);

        return PageableExecutionUtils.getPage(
                elements,
                pageable,
                countQuery::fetchOne);
    }

    public Optional<Schedule> findByIdAndMember(Long id, String loginId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(schedule)
                        .leftJoin(schedule.member, member).fetchJoin()
                        .where(
                                scheduleIdEq(id),
                                memberLoginIdEq(loginId)
                        )
                        .fetchOne()
        );
    }

    public List<ScheduleContent> getScheduleContents(Long scheduleId) {
        queryFactory.update(schedule)
                .set(schedule.viewCount, schedule.viewCount.add(1))
                .where(
                        scheduleIdEq(scheduleId)
                ).execute();

        return queryFactory.selectFrom(scheduleContent)
                .join(scheduleContent.schedule, schedule).fetchJoin()
                .where(
                        scheduleIdEq(scheduleId)
                )
                .fetch();
    }

    public List<Schedule> getScheduleByMember(String loginId) {
        return queryFactory.selectFrom(schedule)
                .leftJoin(schedule.member, member).fetchJoin()
                .where(
                        memberLoginIdEq(loginId)
                )
                .orderBy(schedule.id.desc())
                .fetch();
    }

    private JPAQuery<Schedule> getSearchElementsQuery(ScheduleSearchCond cond) {
        return queryFactory.selectFrom(schedule)
                .join(schedule.member, member).fetchJoin()
                .where(getSearchPredicts(cond));
    }


    private JPAQuery<Long> getSearchCountQuery(ScheduleSearchCond cond) {
        return queryFactory.select(schedule.count())
                .from(schedule)
                .leftJoin(schedule.member, member)
                .where(getSearchPredicts(cond));
    }

    private Predicate[] getSearchPredicts(ScheduleSearchCond cond) {
        return new Predicate[]{
                memberNicknameEq(cond.getMemberNickname()),
                cityNameEq(cond.getCityName()),
                startDateGoe(cond.getStartDate()),
                titleContain(cond.getTitle())
        };
    }

    private BooleanExpression scheduleTitleEq(String title) {
        return hasText(title) ? schedule.title.eq(title) : null;
    }

    private BooleanExpression scheduleIdEq(Long id) {
        return id != null ? schedule.id.eq(id) : null;
    }

    private BooleanExpression memberNicknameEq(String nickname) {
        return hasText(nickname) ? member.nickname.eq(nickname) : null;
    }

    private BooleanExpression memberLoginIdEq(String loginId) {
        return hasText(loginId) ? member.loginId.eq(loginId) : null;
    }

    private BooleanExpression titleContain(String title) {
        return hasText(title) ? schedule.title.contains(title) : null;
    }

    private BooleanExpression cityNameEq(CityName cityName) {
        return cityName != null ? schedule.cityName.eq(cityName) : null;
    }

    private BooleanExpression startDateGoe(LocalDate start) {
        return start != null ? schedule.period.startDate.goe(start) : null;
    }
}