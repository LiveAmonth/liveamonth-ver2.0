package teamproject.lam_server.global.enumMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import teamproject.lam_server.domain.city.constants.*;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;
import teamproject.lam_server.domain.interaction.constants.InteractionState;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.member.constants.AccountState;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.constants.ReviewMenuGroup;
import teamproject.lam_server.domain.review.constants.ReviewSearchType;
import teamproject.lam_server.domain.review.constants.ReviewSortType;
import teamproject.lam_server.domain.schedule.constants.ScheduleFilterType;
import teamproject.lam_server.domain.schedule.constants.ScheduleSearchType;
import teamproject.lam_server.domain.schedule.constants.ScheduleSortType;

import static teamproject.lam_server.global.enumMapper.EnumClassConst.*;

@Configuration
public class EnumConfig {

    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();

        // Member
        enumMapper.put(GENDER_TYPE, GenderType.class);
        enumMapper.put(ACCOUNT_STATE, AccountState.class);
        enumMapper.put(INQUIRY_CATEGORY, InquiryCategory.class);
        enumMapper.put(CITY_INTRO_CATEGORY, CityIntroCategory.class);
        enumMapper.put(CITY_NAME, CityName.class);
        enumMapper.put(TRANSPORT_CATEGORY, TransportCategory.class);
        enumMapper.put(TRANSPORT_GRADE, TransportGrade.class);
        enumMapper.put(MONTH_CATEGORY, MonthCategory.class);
        enumMapper.put(SCHEDULE_SEARCH_TYPE, ScheduleSearchType.class);
        enumMapper.put(SCHEDULE_FILTER_TYPE, ScheduleFilterType.class);
        enumMapper.putMetaModelType(SCHEDULE_SORT_TYPE, ScheduleSortType.class);
        enumMapper.put(REVIEW_CATEGORY, ReviewCategory.class);
        enumMapper.put(REVIEW_MENU_GROUP, ReviewMenuGroup.class);
        enumMapper.put(REVIEW_SEARCH_TYPE, ReviewSearchType.class);
        enumMapper.putMetaModelType(REVIEW_SORT_TYPE, ReviewSortType.class);
        enumMapper.put(INTERACTION_TYPE, InteractionType.class);
        enumMapper.put(INTERACTION_STATE, InteractionState.class);
        enumMapper.put(COMMENT_TYPE, CommentType.class);

        return enumMapper;
    }
}
