import axios from "axios";

const api = axios.create({
    baseURL: `${process.env.REACT_APP_SERVER_ADDR}`
});

// 가로채기
api.interceptors.request.use(
    (config) => { return config },
    (err) => { return Promise.reject(err)}
)

api.interceptors.response.use(
    (response) => { return response },
    (err) => {
        // "특정 에러인 경우에는 이렇게 대처해라"라는 것을 작성
        return Promise.reject(err);
    } 
)

export default api;