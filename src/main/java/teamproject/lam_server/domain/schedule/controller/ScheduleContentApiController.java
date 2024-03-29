package teamproject.lam_server.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleContentCreate;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleContentEdit;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleContentResponse;
import teamproject.lam_server.domain.schedule.service.ScheduleContentService;
import teamproject.lam_server.global.dto.response.CustomResponse;
import teamproject.lam_server.global.dto.response.PostIdResponse;

import javax.validation.Valid;
import java.util.List;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleContentApiController {
    private final ScheduleContentService scheduleContentService;

    @PostMapping("/{schedule_id}/contents")
    public ResponseEntity<?> addScheduleContent(
            @PathVariable("schedule_id") Long scheduleId,
            @RequestBody @Valid ScheduleContentCreate request) {
        PostIdResponse result = scheduleContentService.addScheduleContent(scheduleId, request);
        return CustomResponse.success(CREATE_SCHEDULE_CONTENT, result);
    }


    @PatchMapping("/contents/{content_id}")
    public ResponseEntity<?> editScheduleContent(
            @PathVariable("content_id") Long contentId,
            @RequestBody @Valid ScheduleContentEdit request) {
        scheduleContentService.editScheduleContent(contentId, request);
        return CustomResponse.success(UPDATE_SCHEDULE);
    }


    @DeleteMapping("/contents/{content_id}")
    public ResponseEntity<?> deleteScheduleContent(@PathVariable("content_id") Long contentId) {
        scheduleContentService.deleteScheduleContent(contentId);
        return CustomResponse.success(DELETE_SCHEDULE_CONTENT);
    }

    @GetMapping("/{schedule_id}/detail")
    public ResponseEntity<?> getScheduleContents(@PathVariable("schedule_id") Long scheduleId) {
        List<ScheduleContentResponse> result = scheduleContentService.getScheduleContents(scheduleId);
        return CustomResponse.success(READ_SCHEDULE_CONTENT, result);
    }
}
