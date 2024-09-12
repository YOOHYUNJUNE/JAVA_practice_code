import { CssBaseline, ThemeProvider } from '@mui/material';
import './App.css';
import { useDarkMode } from './hooks/useDarkMode';
import { lightTheme, darkTheme } from './theme/theme';
import { useCookies } from 'react-cookie';
import { jwtDecode } from 'jwt-decode';
import { useEffect } from 'react';
import Navbar from './components/Navbar';
import { Route, BrowserRouter, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage.jsx';
import JoinPage from './pages/JoinPage';
import ProductPage from './pages/ProductPage';
import AdminPage from './pages/AdminPage';
import { LoginUserRoute, AdminRoute } from './components/ProtectedRoutes';

function App() {
  const { isDarkMode, toggleDarkMode } = useDarkMode();
  const [ cookies, setCookie, removeCookie ] = useCookies(['accessToken']);

  useEffect(() => {
    if (cookies.accessToken) {
      const decodeToken = jwtDecode(cookies.accessToken);
      console.log("로그인한 사용자 정보", decodeToken);
    }
  }, [cookies.accessToken]);

  return (
    <ThemeProvider theme={isDarkMode ? darkTheme : lightTheme}>
      <CssBaseline />
        <BrowserRouter>
          <Navbar toggleDarkMode={toggleDarkMode} isDarkMode={isDarkMode} />
          <Routes>
            <Route path="" element={<LoginPage />} />
            <Route path="/join" element={<JoinPage />} />
            <Route path="/products" element={<LoginUserRoute comp={ProductPage} />} />
            <Route path="/admin" element={<AdminRoute comp={AdminPage} />} />
          </Routes>
        </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;
