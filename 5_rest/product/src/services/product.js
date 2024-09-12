import api from './api';

export const productAPI = {
    getAll : () => api.get("/product"),
    getProduct : (id) => api.get('/product', { params : {id} }),
    addProduct : (data) => api.post("/product", data),
    updateProduct : (data) => api.patch("/product", data),
    deletePost : (id) => api.delete(`/product`, { params : {id} })
}