import api from "../api";

export const postApi = {
    getPosts : () => api.get("/posts"),
    postPosts : (post) => api.post("/posts", post)
    
}