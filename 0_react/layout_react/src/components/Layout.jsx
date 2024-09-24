import "./Layout.css";

const Layout = ({children}) => {
    return ( 
        <>
            <header>
                <a href="/">메인로고</a>
                <nav>
                    <li><a href="/#">menu1</a></li>
                    <li><a href="/#">menu2</a></li>
                    <li><a href="/#">menu3</a></li>
                    <li><a href="/#">menu4</a></li>
                </nav>
            </header>
            <main>
                {children}
            </main>
            <footer>연락처 정보</footer>
        </>
     );
}
 
export default Layout;