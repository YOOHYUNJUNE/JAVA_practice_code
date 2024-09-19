import { useState } from "react";
import { userAPI } from "../api/services/user";
import { jwtDecode } from "jwt-decode";
import { getCookie, removeCookie, setCookie } from "../utils/cookieUtil";

// 훅 생성
export const useProvideAuth = () => {
    const [userInfo, setUserInfo] = useState({});

    const refreshUserInfo = () => {
        const token = getCookie("accessToken");
        if (token) {
            console.log("로그인 상태");
            const jwtpayload = jwtDecode(token);
            setUserInfo({
                id : jwtpayload.id,
                email : jwtpayload.sub,
                role : jwtpayload.role
            });
        } else {
            // setUserInfo({});
            console.log("로그아웃 상태");
        }
    }

    // 로그인 메소드
    const login = async(data, successCallBack = null) => {
        try {
            const res = await userAPI.login(data);
            if (res.status === 200) {
                const token = res.data.accessToken;
                // localStorage.setItem("token", token);
                setCookie("accessToken", token, { path: '/' });
                refreshUserInfo(token);
                if(successCallBack) successCallBack();
            }
        } catch (error) {
            console.error(error);
        }
    }

    // 로그아웃
    const logout = (callBack = null) => {
        // localStorage.removeItem("token");
        console.log("로그아웃함")
        removeCookie("accessToken");
        setUserInfo({});
        if(callBack) callBack();
    }

    // 토큰 체크
    const tokenCheck = () => {
        // const token = localStorage.getItem("token");
        const token = getCookie("accessToken");
        if (token) {
            const jwtPayload = jwtDecode(token);
            // if (jwtPayload.exp > Date.now() / 1000)
            return jwtPayload.role;
        }
        // logout();
        return false;
    }

    return {
        userInfo,
        refreshUserInfo,
        tokenCheck,
        login,
        logout
    }


}

