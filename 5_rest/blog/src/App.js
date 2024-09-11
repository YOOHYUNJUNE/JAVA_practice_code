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
          <Route path='*' element={<h1>NOT FOUND</h1>} />

          {/* 게시글 */}
          <Route path='/post' element={<Post/>} />
          <Route path='/post/write' element={<PostForm />} />
          <Route path='/post/modify/:postId' element={<PostForm/>} />
          <Route path='/post/:postId' element={<PostDetail />} />

          {/* 즐겨찾기 */}
          <Route path='/favorite' element={<Favorite/>} />
          <Route path='/favorite/write' element={<FavoriteForm />} />
          <Route path='/favorite/modify/:favId' element={<FavoriteForm />} />

          {/* 유저 */}
          <Route path='/user' element={<User/>} />
          <Route path='/signup' element={<SignUp/>} />
          <Route path='/user/modify/:userEmail' element={<SignUp/>} />
          <Route path='/login' element={<Login/>} />
          <Route path='/logout' element={<Login/>} />





        </Routes>
      </Layout>

    </LoginContext.Provider>
  );
}

export default App;
