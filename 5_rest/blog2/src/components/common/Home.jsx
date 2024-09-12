import { useEffect } from "react";
import { useAuth } from "../../hooks/useAuth";

const Home = () => {
    const { userInfo } = useAuth();

    return (
        <>
        {
            userInfo && userInfo.email ?
            <>
                로그인 유저 정보 : {userInfo.email}, {userInfo.role}
            </>
            :
            <>
                로그인합시다
            </>
        }
        </>
    );
}
 
export default Home;