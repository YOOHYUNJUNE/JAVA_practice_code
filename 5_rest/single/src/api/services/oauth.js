import api from "../api";

export const oauthAPI = {
    googleLogin : (code) => api.get(`/oauth/google?code=${code}`),
    
}