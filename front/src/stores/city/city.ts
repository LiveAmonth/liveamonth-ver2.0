import CityApiService from "@/services/city/CityApiService";
import { defineStore } from "pinia";
import type { EnumType } from "@/modules/types/common/EnumType";
import type { ImageContentType } from "@/modules/types/common/ImageContentType";
import type {
  CityCardType,
  CityExtraType,
  CityIntroType,
  CityTransportType,
  CityWeatherType,
} from "@/modules/types/city/CityType";
import type { initDataType } from "@/modules/types/common/initDataType";

const storageGridInfo: CityCardType[] = localStorage["city-grid-info"]
  ? JSON.parse(localStorage["city-grid-info"])
  : null;
const initCityGridInfo: initDataType = storageGridInfo
  ? { state: true, data: storageGridInfo }
  : { state: false, data: {} as CityCardType[] };

const storageCityNames: EnumType[] = localStorage["city-names"]
  ? JSON.parse(localStorage["city-names"])
  : null;
const initCityNames: initDataType = storageCityNames
  ? { state: true, data: storageCityNames }
  : { state: false, data: {} as EnumType[] };

const storageIntro: CityIntroType = localStorage["intro"]
  ? JSON.parse(localStorage["intro"])
  : null;
const initCityIntro: initDataType = storageIntro
  ? { state: true, data: storageIntro }
  : { state: false, data: {} as CityIntroType };

const storageExtraInfo: CityExtraType = localStorage["extra-info"]
  ? JSON.parse(localStorage["extra-info"])
  : null;
const initCityExtraInfo: initDataType = storageExtraInfo
  ? { state: true, data: storageExtraInfo }
  : { state: false, data: {} as CityExtraType };

export const useCityStore = defineStore("city", {
  state: () => ({
    cityGridInfo: initCityGridInfo as initDataType,
    cityNames: initCityNames as initDataType,
    cityCategory: ["intro", "transport", "weather"],
    cityIntro: initCityIntro as initDataType,
    cityExtraInfo: initCityExtraInfo as initDataType,
  }),
  getters: {
    cityNameList: (state): EnumType[] => state.cityNames.data as EnumType[],
    gridInfo: (state): CityCardType[] =>
      state.cityGridInfo.data as CityCardType[],
    introDetail: (state): ImageContentType[] =>
      (state.cityIntro.data as CityIntroType)["INTRO"] as ImageContentType[],
    foods: (state): ImageContentType[] =>
      (state.cityIntro.data as CityIntroType)["FOOD"] as ImageContentType[],
    views: (state): ImageContentType[] =>
      (state.cityIntro.data as CityIntroType)["VIEW"] as ImageContentType[],
    transports: (state): CityTransportType[] =>
      (state.cityExtraInfo.data as CityExtraType)
        .transports as CityTransportType[],
    weathers: (state): CityWeatherType[] =>
      (state.cityExtraInfo.data as CityExtraType).weathers as CityWeatherType[],
  },
  actions: {
    getCityNames: async function () {
      try {
        const response = await CityApiService.getCityNames();
        localStorage.setItem("city-names", JSON.stringify(response));
        this.cityNames.state = true;
        this.cityNames.data = response;
      } catch (error) {
        console.log(error);
      }
    },

    getCityIntro: async function (cityName: string) {
      try {
        const response = await CityApiService.getCityIntro(cityName);
        localStorage.setItem("intro", JSON.stringify(response));
        this.cityIntro.state = true;
        this.cityIntro.data = response;
      } catch (error) {
        console.log(error);
      }
    },

    getExtraCityInfo: async function (cityName: string) {
      try {
        const response = await CityApiService.getExtraCityInfo(cityName);
        localStorage.setItem("extra-info", JSON.stringify(response));
        this.cityExtraInfo.state = true;
        this.cityExtraInfo.data = response;
      } catch (error) {
        console.log(error);
      }
    },

    getCityGridInfo: async function () {
      try {
        const response = await CityApiService.getCityGridInfo();
        localStorage.setItem("city-grid-info", JSON.stringify(response));
        this.cityGridInfo.state = true;
        this.cityGridInfo.data = response;
      } catch (error) {
        console.log(error);
      }
    },
  },
});
