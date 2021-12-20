package teamproject.lam_simple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_simple.domain.CityInfo;
import teamproject.lam_simple.repository.CityInfoRepository;
import teamproject.lam_simple.repository.CityTransportRepository;
import teamproject.lam_simple.repository.CityWeatherRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static teamproject.lam_simple.constants.AttrConstants.CITY_TRANSPORTS;
import static teamproject.lam_simple.constants.AttrConstants.CITY_WEATHERS;
import static teamproject.lam_simple.constants.CategoryConstants.CityInfoCategory;
import static teamproject.lam_simple.constants.CategoryConstants.CityNames;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityService {
    private final CityInfoRepository cityInfoRepository;
    private final CityTransportRepository cityTransportRepository;
    private final CityWeatherRepository cityWeatherRepository;

    public List<CityInfo> findCityInfoByCategory(CityInfoCategory category) {
        return cityInfoRepository.findCityInfosByCityInfoCategory(category);
    }

    public Map<String, Object> getDataAboutCity(CityNames menu) {
        Map<String, Object> cityInfoMap = new HashMap<>();
        for (CityInfoCategory category : CityInfoCategory.values()) {
            cityInfoMap.put(category.name(), cityInfoRepository.findCityInfosByCityInfoCategoryAndCity_Name(category,menu));
        }
        cityInfoMap.put(CITY_TRANSPORTS, cityTransportRepository.findCityTransportsByCity_Name(menu));
        cityInfoMap.put(CITY_WEATHERS, cityWeatherRepository.findCityWeathersByCity_Name(menu));
        return cityInfoMap;
    }
}
