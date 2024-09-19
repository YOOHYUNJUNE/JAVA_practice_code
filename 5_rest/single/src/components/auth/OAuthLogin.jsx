import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { setCookie } from "../../utils/cookieUtil";
import { oauthAPI } from "../../api/services/oauth";

// 로그인 공통 메소드
const OAuthLogin = () => {
    const code = new URLSearchParams(window.location.search).get("code");
    const oAuthAPI = {
        "kakao" : (code) => oauthAPI.kakaoLogin(code),
        "google" : (code) => oauthAPI.googleLogin(code)
    }

    const login = async () => {
        try {
          const response = await oAuthAPI[provider](code);
          if (response.status !== 200) {
            throw new Error("로그인 실패");      
          } else {
            setCookie("accessToken", response.data.accessToken, {path:"/"});
            window.location.href = "/";
          }
          
        } catch (error) {
        console.error(error);
        }
      }

    const { provider } = useParams();

    useEffect(() => {
        login();
      }, [code]);
    
      return (
        
        <div>{provider === "google" ? <h1>GOOGLE 로그인중...</h1> 
        : (provider === "kakao" ? <h1>KAKAO 로그인중...</h1> 
        : <h1>로그인 정보 알 수 없음</h1>)}
        </div>
             
    
    );
}


 
export default OAuthLogin;