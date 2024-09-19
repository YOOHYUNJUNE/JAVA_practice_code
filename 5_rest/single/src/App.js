import logo from './logo.svg';
import './App.css';
import { useProvideAuth } from './hooks/useProvideAuth';
import { LoginContext } from './contexts/LoginContext';
import Layout from './components/layouts/Layout';
import { Route, Routes } from 'react-router-dom';
import Home from './components/common/Home';
import NotFound from './components/common/NotFound';
import Error from './components/common/Error';
import AccessControl from './components/common/AccessControl';
import SignUp from './components/auth/SignUp';
import User from './components/users/User';
import Login from './components/auth/Login';
import Product from './components/products/Product';
import ProductForm from './components/products/ProductForm';
import ProductDetail from './components/products/ProductDetail';
import { oauthAPI } from './api/services/oauth';
import { useEffect } from 'react';

function App() {

  const auth = useProvideAuth();

  return (
    // 사용자 정보를 기억하게 하는 커스텀 훅
    <LoginContext.Provider value={auth}>
      <Layout>
        <Routes>

          <Route path='/' element={<Home/>} />
          <Route path='/error' element={<Error/>} />
          <Route path='*' element={<NotFound/>} />
          <Route path='/oauth/google' element={<GoogleLogin />} />
          

          {/* 유저 */}
          {/* 관리자 */}
          <Route path='/userlist' element={<AccessControl roleList={["ROLE_ADMIN"]} > <User/> </AccessControl>} />
          {/* 로그인한 사용자 */}
          <Route path='/logout' element={<AccessControl roleList={["ROLE_USER", "ROLE_ADMIN"]} > <Login/> </AccessControl>} />
          {/* 아무나 */}
          <Route path='/userjoin' element={<SignUp/>} />
          <Route path='/userlogin' element={<AccessControl roleList={["none"]} > <Login/> </AccessControl>} />

          {/* 상품 */}
          {/* 관리자 */}
          <Route path='/product' element={<AccessControl roleList={["ROLE_ADMIN"]} > <Product/> </AccessControl>} />
          <Route path='/product/add' element={<AccessControl roleList={["ROLE_ADMIN"]} > <ProductForm/> </AccessControl>} />
          <Route path='/product/modify/:productId' element={<AccessControl roleList={["ROLE_ADMIN"]} > <ProductForm/> </AccessControl>} />
          <Route path='/product/:productId' element={<AccessControl roleList={["ROLE_ADMIN"]} > <ProductDetail/> </AccessControl>} />

        </Routes>
      </Layout>
    </LoginContext.Provider>
  );
}

// 구글 로그인
const GoogleLogin = () => {
  const code = new URLSearchParams(window.location.search).get("code");
  console.log("서버에 전달해야하는 코드 : ", code);
  const login = async () => {
    try {
      const response = await oauthAPI.googleLogin(code);
      if (response.status !== 400) {
        throw new Error("로그인 실패");      
      } 
      
    } catch (error) {
    console.error(error);
    }
  }
  
  useEffect(() => {
    login();
  }, [code]);

  return ( <div>로그인 처리중...</div> );

}

export default App;
