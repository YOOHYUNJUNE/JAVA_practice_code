import logo from './logo.svg';
import './App.css';
import Layout from './components/layouts/Layout';
import { Route, Routes } from 'react-router-dom';
import Post from './components/posts/Post';

function App() {
  return (
    <Layout>
      <Routes>
        <Route path='/' element={<h1>홈</h1>} />
        <Route path='/post' element={<Post/>} />
        <Route path='/post/:id' element={<h1>특정 포스트</h1>} />
        <Route path='/search' element={<h1>검색</h1>} />
        <Route path='/error' element={<h1>에러</h1>} />
        <Route path='*' element={<h1>NOT FOUND</h1>} />
      </Routes>
    </Layout>

  );
}

export default App;
