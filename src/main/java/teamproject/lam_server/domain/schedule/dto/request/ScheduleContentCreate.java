package teamproject.lam_server.domain.schedule.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;
import teamproject.lam_server.global.entity.TimePeriod;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ScheduleContentCreate {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private TimePeriod timePeriod;

    @Min(0)
    private int cost;

    @AssertTrue
    public boolean isValidTimePeriod() {
        return getTimePeriod().getStartDateTime().isBefore(getTimePeriod().getEndDateTime());
    }

    public ScheduleContent toEntity(Schedule schedule) {
        return ScheduleContent.builder()
                .title(this.title)
                .content(this.content)
                .timePeriod(this.timePeriod)
                .cost(this.cost)
                .schedule(schedule)
                .build();

    }
}