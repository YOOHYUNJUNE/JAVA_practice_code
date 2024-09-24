import api from "../api";

export const userApi = {
    getUser : (userId) => api.get(`/users/${userId}`),
    loginUser: (email, password) => api.get(`/users?email=${email}&password=${password}`),
    getUserByEmail : (email) => api.get(`/users?email=${email}`),
    postUser : (user) => api.post('/users', user)
}