import { useState } from "react";
import { userAPI } from "../api/services/user";
import { jwtDecode } from "jwt-decode";

// 훅 생성
export const useProvideAuth = () => {
    const [accessToken, setAccessToken] = useState(localStorage.getItem("token"));

    // 로그인 메소드
    const login = async(data, successCallBack, failCallBack) => {
        try {
            const res = await userAPI.login(data);
            if (res.status === 200) {
                const token = res.data.accessToken;
                localStorage.setItem("token", token);
                setAccessToken(token);
                successCallBack();
            }
        } catch (error) {
            console.error(error);
            failCallBack();
        }
    }

    // 로그아웃
    const logout = (callBack) => {
        localStorage.removeItem("token");
        setAccessToken(null);
        callBack();
    }

    // 토큰 체크
    const tokenCheck = () => {
        if (accessToken !== null && accessToken === localStorage.getItem("token")) {
            const jwtPayload = jwtDecode(accessToken);
            if (jwtPayload.exp > Date.now() / 1000)
                return true;
        }
        return false;
    }

    return {
        accessToken,
        tokenCheck,
        login,
        logout
    }


}

