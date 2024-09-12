import logo from './logo.svg';
import './App.css';
import Layout from './components/layouts/Layout';
import { Route, Routes } from 'react-router-dom';
import Post from './components/posts/Post';
import PostForm from './components/posts/PostForm';
import PostDetail from './components/posts/PostDetail';
import Favorite from './components/favorites/Favorite';
import FavoriteForm from './components/favorites/FavoriteForm';
import SignUp from './components/auth/SignUp';
import User from './components/users/User';
import Error from './components/common/Error';
import { LoginContext } from './contexts/LoginContext';
import { useProvideAuth } from './hooks/useProvideAuth';
import Login from './components/auth/Login';
import Home from './components/common/Home';
import AccessControl from './components/common/AccessControl';
import NotFound from './components/common/NotFound';

function App() {

  const auth = useProvideAuth();
  
  return (
    // 사용자 정보를 기억하게 하는 custom hook
    <LoginContext.Provider value={auth}>

      <Layout>
        <Routes>
          <Route path='/' element={<Home/>} />
          <Route path='/search' element={<Post/>} />
          <Route path='/error' element={<Error/>} />
          <Route path='*' element={<NotFound/>} />

      {/* 게시글 */}
          {/* 로그인한 사용자 */}
          <Route path='/post/write' element={<AccessControl roleList={["ROLE_USER", "ROLE_ADMIN"]} >  <PostForm /> </AccessControl>} />
          <Route path='/post/modify/:postId' element={<AccessControl roleList={["ROLE_USER", "ROLE_ADMIN"]} > <PostForm/> </AccessControl>} />
          {/* 아무나 */}
          <Route path='/post' element={<Post/>} />
          <Route path='/post/:postId' element={<PostDetail />} />

      {/* 즐겨찾기 */}
          {/* 로그인한 사용자 */}
          <Route path='/favorite' element={<AccessControl roleList={["ROLE_USER", "ROLE_ADMIN"]} >  <FavoriteForm /> </AccessControl>} />
          <Route path='/favorite/write' element={<AccessControl roleList={["ROLE_USER", "ROLE_ADMIN"]} >  <FavoriteForm /> </AccessControl>} />
          <Route path='/favorite/modify/:favId' element={<AccessControl roleList={["ROLE_USER", "ROLE_ADMIN"]} > <FavoriteForm /> </AccessControl>} />

      {/* 유저 */}
          {/* 관리자 */}
          <Route path='/user/modify/:userEmail' element={<AccessControl roleList={["ROLE_USER", "ROLE_ADMIN"]} > <SignUp/> </AccessControl>} />
          <Route path='/user' element={<AccessControl roleList={["ROLE_USER", "ROLE_ADMIN"]} > <User/> </AccessControl>} />
          {/* 로그인한 사용자 */}
          <Route path='/logout' element={<AccessControl roleList={["ROLE_USER", "ROLE_ADMIN"]} > <Login/> </AccessControl>} />
          {/* 아무나 */}
          <Route path='/signup' element={ <SignUp/> } />
          <Route path='/login' element={<AccessControl roleList={["none"]} > <Login/> </AccessControl>} />





        </Routes>
      </Layout>

    </LoginContext.Provider>
  );
}

export default App;
