import { useState } from "react";
import { userAPI } from "../api/services/user";
import { jwtDecode } from "jwt-decode";

// 훅 생성
export const useProvideAuth = () => {
    const [userInfo, setUserInfo] = useState(null);

    // 로그인 메소드
    const login = async(data, successCallBack = null) => {
        try {
            const res = await userAPI.login(data);
            if (res.status === 200) {
                const token = res.data.accessToken;
                localStorage.setItem("token", token);
                const jwtpayload = jwtDecode(token);
                setUserInfo({
                    id : jwtpayload.id,
                    email : jwtpayload.sub,
                    role : jwtpayload.role
                });
                console.log(jwtpayload);
                if(successCallBack) successCallBack();
            }
        } catch (error) {
            console.error(error);
        }
    }

    // 로그아웃
    const logout = (callBack = null) => {
        localStorage.removeItem("token");
        setUserInfo(null);
        if(callBack) callBack();
    }

    // 토큰 체크
    const tokenCheck = () => {
        const token = localStorage.getItem("token");
        if (token !== null) {
            const jwtPayload = jwtDecode(token);
            if (jwtPayload.exp > Date.now() / 1000)
                return true;
        }
        return false;
    }

    return {
        userInfo,
        tokenCheck,
        login,
        logout
    }


}

