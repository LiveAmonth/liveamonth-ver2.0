import { defineStore } from "pinia";
import jwtDecode from "jwt-decode";
import AuthApiService from "@/services/AuthApiService";
import type { LoginType } from "@/modules/types/form/FormType";
import type { JWTType, TokenType } from "@/modules/types/auth/AuthType";
import type { TokenMemberInfoType } from "@/modules/types/member/MemberType";
import type { initDataType } from "@/modules/types/common/initDataType";

const storageToken: TokenType = localStorage["token-info"]
  ? JSON.parse(localStorage["token-info"])
  : null;
const initTokenInfo: initDataType = storageToken
  ? { state: true, data: storageToken }
  : { state: false, data: {} as TokenType };

export const useAuthStore = defineStore("auth", {
  state: () => ({
    tokenInfo: initTokenInfo as initDataType,
  }),
  getters: {
    accessToken: (state): string =>
      (state.tokenInfo.data as TokenType).accessToken,
    memberInfo: (state): TokenMemberInfoType => {
      if (state.tokenInfo.state) {
        const token = (state.tokenInfo.data as TokenType).accessToken;
        const jwt: JWTType = jwtDecode(token);
        return jwt.profile;
      } else {
        return {} as TokenMemberInfoType;
      }
    },
    isExpired: (state): boolean => {
      if (state.tokenInfo.state) {
        const token = (state.tokenInfo.data as TokenType).accessToken;
        const jwt: JWTType = jwtDecode(token);
        return Date.now() >= jwt.exp * 1000;
      } else {
        return false;
      }
    },
    loggedIn: (state): boolean => state.tokenInfo.state,
  },
  actions: {
    async login(data: LoginType) {
      await AuthApiService.login(data)
        .then((response: TokenType) => {
          localStorage.setItem("token-info", JSON.stringify(response));
          this.tokenInfo.state = true;
          this.tokenInfo.data = response;
        })
        .catch((error) => {
          throw error;
        });
    },
    async logout() {
      const tokenInfo = this.tokenInfo.data as TokenType;
      await AuthApiService.logout(tokenInfo.grantType, tokenInfo.accessToken)
        .then(() => {
          localStorage.removeItem("token-info");
          this.tokenInfo.state = false;
          this.tokenInfo.data = {} as TokenType;
        })
        .catch((error) => {
          throw error;
        });
    },
    async reissue() {
      await AuthApiService.reissue()
        .then((response: TokenType) => {
          localStorage.setItem("token-info", JSON.stringify(response));
          this.tokenInfo.state = true;
          this.tokenInfo.data = response;
        })
        .catch((error) => {
          throw error;
        });
    },
  },
});
