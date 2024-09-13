import api from '../api';
// .js는 생략가능

export const productAPI = {

    getProductList : () => api.get("/product"),
    getProduct : (id) => api.get(`/product/${id}`),
    addProduct : (formData) => api.post("/product", formData, {headers: {"Content-Type": "multipart/form-data"}}),
    modifyProduct : (formData) => api.patch("/product", formData, {headers: {"Content-Type": "multipart/form-data"}}),
    deleteProduct : (id, authorId) => api.delete(`/product/${id}`, {data:{authorId}}),
    searchProduct : (keyword) => api.get("/product/search", {params:keyword}),

}







