import axios from "axios";
import { getCookie, removeCookie, setCookie } from "../utils/cookieUtil";

const api = axios.create({
    baseURL: `${process.env.REACT_APP_REST_SERVER}`,
    withCredentials: true // HttpOnly 쿠키 속성으로 저장된 리프레시 쿠키 전송
})



// 서버 상태에 따른 분기 설정

api.interceptors.request.use(
    (config) => {
        // 로그인을 하면 토큰을 보내서 권한에 따른 행동 가능하게 하기
        const token = getCookie("accessToken");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        } else {
            delete config.headers.Authorization;
        }
        return config;
    },
    (err) => {
        return Promise.reject(err);
    }
)

api.interceptors.response.use(
    (res) => {
        return res;
    },
    async (err) => {
        // 원래 403으로 실패했던 요청
        const originalReq = err.config;
        // 만약 권한이 없다는 에러 시 + 무한루프 시
        if (err.response.status == 403 && !originalReq._retry) {
            originalReq._retry = true;
            try {
                // 토큰 재발급
                const response = await refreshTokenHandler();
                // 정상 발급시
                if (response.status === 200) {
                    // // 로컬스토리지에 토큰 저장
                    // localStorage.setItem("token", response.data.accessToken);
                    // 쿠키에 토큰 저장
                    setCookie("accessToken", response.data.accessToken);
                    // 헤더에 새로운 토큰 추가
                    originalReq.headers.Authorization = `Bearer ${response.data.accessToken}`;
                    // 실패했던 요청 다시 보내기
                    return api.request(originalReq);
                }
                console.log(response);
           } catch (error) {
                console.log("토큰 재발급 실패 (401)");
                removeCookie("accessToken"); // 에러 시 로그아웃처리
                return Promise.reject(err);
           }
        }
        return Promise.reject(err);
    }
);

const refreshTokenHandler = async () => {
    try {
        if (getCookie("accessToken")) {
            const response = await api.post("/auth/refresh-token");
            return response;
        }            
    } catch (error) {
        throw error;
    }
}

export default api;