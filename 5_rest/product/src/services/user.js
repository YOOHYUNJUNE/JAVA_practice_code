import api from './api';

export const userAPI = {
    login: (data) => api.post("/login", data),
    join : (data) => api.post("/join", data),
    duplicate: (email) => api.get("/duplicate", { params : {email} }),
}