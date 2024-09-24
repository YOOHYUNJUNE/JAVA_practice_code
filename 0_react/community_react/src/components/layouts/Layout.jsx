import { BrowserRouter } from "react-router-dom";
import Header from "./Header";
import Main from "./Main";

const Layout = ({children}) => {
    return (
        <BrowserRouter>
            <Header />
            <Main>
                {children}
            </Main>
        </BrowserRouter>
    );
}
 
export default Layout;