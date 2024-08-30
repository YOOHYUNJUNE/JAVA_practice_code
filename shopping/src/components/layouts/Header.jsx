import Navigation from "./Navigation";
import "./Header.css";

const Header = () => {
    return ( 
        <>
            <header>
                <a href="/">메인 로고</a>
                <Navigation />
            </header>
        </>
    );
}
 
export default Header;