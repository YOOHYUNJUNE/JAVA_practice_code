import styled from '@emotion/styled';
import { Link } from 'react-router-dom';
import { SiBarclays } from "react-icons/si";

const Header = () => {
    const loginUser = localStorage.getItem("loginUser");
    return (
        <StyledHeader>
            <Link to="/"><SiBarclays /></Link>
            <nav>
                <Link to="/">HOME</Link>
                <Link to="/post">TIMELINE</Link>
                <Link to="/search">SEARCH</Link>
                {/* 로그인 후에는 이 버튼은 로그아웃으로 변경 */}
                {
                    loginUser ?
                    <>
                        <Link to="/info">{loginUser}</Link> 
                    </> :
                    <>
                        <Link to="/login">LOGIN</Link> 
                        <Link to="/signup">SIGNUP</Link> 
                    </>
                }
                
            </nav>
        </StyledHeader>
    );
}
 
const StyledHeader = styled.header`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0.5rem 1rem;
    background-color: #aaa;
    color: #fff;
    & > a {
        color: #fff;
        text-decoration: none;
        font-size: 2.5rem;
    }
    nav {
        display: flex;
        a {
            font-size: 2rem;
            color: #fff;
            text-decoration: none;
            margin: 0 1rem;
            &:hover {
                text-decoration: underline;
            }
        }
    }
`

export default Header;