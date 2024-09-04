import logo from './logo.svg';
import './App.css';
import Layout from './components/layouts/Layout';
import { Route, Routes } from 'react-router-dom';
import Post from './components/posts/Post';
import PostForm from './components/posts/PostForm';
import PostDetail from './components/posts/PostDetail';
import Favorite from './components/favorites/Favorite';
import FavoriteForm from './components/favorites/FavoriteForm';

function App() {
  return (
    <Layout>
      <Routes>
        <Route path='/' element={<h1>홈</h1>} />
        <Route path='/post' element={<Post/>} />
        <Route path='/post/write' element={<PostForm />} />
        <Route path='/post/modify/:postId' element={<PostForm/>} />
        <Route path='/post/:postId' element={<PostDetail />} />
        <Route path='/search' element={<h1>검색</h1>} />
        <Route path='/error' element={<h1>에러</h1>} />
        <Route path='*' element={<h1>NOT FOUND</h1>} />

        {/* 즐겨찾기 */}
        <Route path='/favorite' element={<Favorite/>} />
        <Route path='/favorite/write' element={<FavoriteForm />} />
        <Route path='/favorite/modify/:favId' element={<FavoriteForm />} />


      </Routes>
    </Layout>

  );
}

export default App;
