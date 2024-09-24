import api from '../api';
// .js는 생략가능

export const userAPI = {

    login : (data) => api.post("/userlogin", data),
    getUserList : () => api.get("/userlist"),
    addUser : (data) => api.post("/userjoin", data),
    emailCheck : (email) => api.get("/duplicate", {params:{email}}),
    // modifyUser : (data) => api.patch("/usermodify", {data}),
    modifyUser : (data) => api.patch("/usermodify", data),
    deleteUser : (data) => api.delete("/userdelete", {data})
    

}



