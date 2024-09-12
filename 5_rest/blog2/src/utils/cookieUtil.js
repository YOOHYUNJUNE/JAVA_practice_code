import { Cookies } from "react-cookie";

const cookies = new Cookies();

// setter
export const setCookie = (name, value, options) => {
    return cookies.set(name, value, {...options}); 
}

// getter
export const getCookie = (name) => {
    return cookies.get(name); 
}

// 로그아웃 시 쿠키 제거
export const removeCookie = (name) => {
    return cookies.remove(name);
}
