package teamproject.lam_server.domain.city.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.dto.response.api.CityFoodAndViewResponse;
import teamproject.lam_server.domain.city.dto.response.api.CityGridDataResponse;
import teamproject.lam_server.domain.city.dto.response.api.TotalCityInfoResponse;
import teamproject.lam_server.domain.city.service.query.CityApiService;
import teamproject.lam_server.global.dto.CustomResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/city")
public class CityApiController {
    private final CityApiService cityApiService;

    /**
     * dependence presentation layer::home(body)
     * -> slide info(top)
     */
    @GetMapping("/slide-infos")
    public ResponseEntity<?> searchCityGridInfos() {
        List<CityGridDataResponse> result = cityApiService.searchCurrentCityInfo();
        return CustomResponse.success(result);
    }

    /**
     * dependence presentation layer::city(body)
     * -> total city info tab pane(top)
     */
    @GetMapping("{cityName}/total-infos")
    public ResponseEntity<?> getTotalCityInfo(@PathVariable("cityName") CityName cityName) {
        TotalCityInfoResponse result = cityApiService.searchTotalCityInfo(cityName);
        return CustomResponse.success(result);
    }

    /**
     * dependence presentation layer::city(body)
     * -> food & view image slide(bottom)
     */
    @GetMapping("{cityName}/food-and-view")
    public ResponseEntity<?> getCityFoodAndViewInfo(@PathVariable("cityName") CityName cityName) {
        CityFoodAndViewResponse result = cityApiService.searchCityFoodAndView(cityName);
        return CustomResponse.success(result);
    }


}
