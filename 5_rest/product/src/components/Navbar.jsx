import { AppBar, Button, Stack, Switch, Toolbar, Typography } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import { useEffect, useState } from 'react';
import { jwtDecode } from 'jwt-decode';

const Navbar = ({ toggleDarkMode, isDarkMode }) => {
    const [ cookies, , removeCookie ] = useCookies(['accessToken']);
    const navigate = useNavigate();
    const [role, setRole] = useState();
    const handleLogout = () => {
        removeCookie('accessToken', { path: '/' });
        navigate('/');
    };

    useEffect(() => {
        if (cookies.accessToken) {
            const role = jwtDecode(cookies.accessToken).role;
            setRole(role);
        } else {
            setRole(null);
        }
    }, [cookies.accessToken]);

    return (
        <AppBar position='static'>
            <Toolbar>
                <Typography variant='h6' sx={{ flexGrow: 1}}>

                    {
                        role === "ROLE_USER" || role === "ROLE_ADMIN" && 
                        <Button color="inherit" LinkComponent={Link} to="/products">상품</Button>
                    }
                    {
                        role === "ROLE_ADMIN" && 
                        <Button color="inherit" LinkComponent={Link} to="/admin">관리자</Button>
                    }
                    { !role &&
                        <Button color="inherit" LinkComponent={Link} to="/join">회원가입</Button>
                    }    
                    { role && 
                        <Button color="inherit" onClick={handleLogout}>
                            로그아웃
                        </Button>
                    }
                </Typography>
                <Stack direction="row" spacing={1} sx={{ alignItems: 'center' }}>
                    <Typography>Light</Typography>
                    <Switch onChange={toggleDarkMode} defaultChecked={isDarkMode} />
                    <Typography>Dark</Typography>
                </Stack>
            </Toolbar>
        </AppBar>
    );
}
 
export default Navbar;