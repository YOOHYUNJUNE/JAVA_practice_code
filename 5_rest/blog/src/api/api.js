import axios from "axios";

const api = axios.create({
    baseURL: `${process.env.REACT_APP_REST_SERVER}`
})



// 서버 상태에 따른 분기 설정

api.interceptors.response.use(
    (config) => {
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
        return Promise.reject(err);
        // 만약 권한이 없다는 에러시, 토큰 재발급
    }
)

export default api;