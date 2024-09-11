import { BrowserRouter } from "react-router-dom";
import Header from "./Header";
import Main from "./Main";
import { useAuth } from "../../hooks/useAuth";
import { useEffect } from "react";

const Layout = ({children}) => {

    const { userInfo, refreshUserInfo } = useAuth();

    useEffect(() => {
        refreshUserInfo();
    }, []);

    // children을 보낼 때 role을 확인해서 처리
    


    return ( 
        <BrowserRouter>
            <Header />
            <Main>
                { children }
            </Main>
        </BrowserRouter>
    );
}
 
export default Layout;