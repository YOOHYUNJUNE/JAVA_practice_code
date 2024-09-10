import axios from "axios";

const api = axios.create({
    baseURL: `${process.env.REACT_APP_REST_SERVER}`
})



// 서버 상태에 따른 분기 설정

api.interceptors.request.use(
    (config) => {
        // 로그인을 하면 토큰을 보내서 권한에 따른 행동 가능하게 하기
        const token = localStorage.getItem("token");
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
        // const data = res.data;
        // if (data) return data;
        return res;
    },
    (err) => {
        if (err.response.status == 403) {
            window.location.href="/";
        }
        return Promise.reject(err);
        // 만약 권한이 없다는 에러시, 토큰 재발급
    }
);

export default api;