package teamproject.lam_server.domain.schedule.dto.response;


import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.member.dto.response.SimpleProfileResponse;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.entity.Period;

/**
 * for schedule list view
 */
@Getter
@Builder
public class ScheduleCardResponse {

    private Long id;
    private String title;
    private SimpleProfileResponse profile;
    private int cost;
    private CityName city;
    private int hits;
    private int likes;
    private Period period;

    public static ScheduleCardResponse of(Schedule schedule){
        return ScheduleCardResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .profile(SimpleProfileResponse.of(schedule.getMember()))
                .cost(schedule.getTotalCost())
                .city(schedule.getCityName())
                .hits(schedule.getViewCount())
                .likes(schedule.getLikeCount())
                .period(schedule.getPeriod())
                .build();
    }
}
