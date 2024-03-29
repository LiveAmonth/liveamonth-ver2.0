package teamproject.lam_server.domain.city.dto.response.api;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.city.dto.response.CityTransportResponse;
import teamproject.lam_server.domain.city.dto.response.CityWeatherResponse;
import teamproject.lam_server.domain.city.entity.CityTransport;
import teamproject.lam_server.domain.city.entity.CityWeather;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ExtraCityResponse {

    //== 도시 교통 정보 ==//
    private List<CityTransportResponse> transports;

    //== 날씨 정보 ==//
    private List<CityWeatherResponse> weathers;

    public static ExtraCityResponse of(List<CityTransport> transports, List<CityWeather> weathers) {
        return ExtraCityResponse.builder()
                .transports(transports.stream().map(CityTransportResponse::of).collect(Collectors.toList()))
                .weathers(weathers.stream().map(CityWeatherResponse::of).collect(Collectors.toList()))
                .build();
    }
}
