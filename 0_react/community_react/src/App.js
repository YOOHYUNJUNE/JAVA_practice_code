import './App.css';
import { Navigate, Outlet, Route, Routes } from "react-router-dom";
import Home from "./components/pages/Home";
import Login from "./components/pages/Login";
import SignUp from "./components/pages/SignUp";
import TimeLine from './components/pages/TimeLine';
import Search from './components/pages/Search';
import Profile from './components/pages/Profile';
import MyProfile from './components/pages/MyProfile';
import NotFound from './components/pages/NotFound';

import Layout from './components/layouts/Layout';

function App() {
  return (
    <Layout>
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/post" element={<TimeLine />} />
        <Route path="/search" element={<Search />} />
        
        {/* 게스트만 접근 가능 */}
        <Route element={<GuestRoute/>}>
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} />
        </Route>

        {/* 로그인한 회원만 접근 가능 */}
        <Route element={<UserRoute />}>
            <Route path="/profile" element={<MyProfile />} />
        </Route>

        <Route path="/profile/:id" element={<Profile />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Layout>
  );
}

const GuestRoute = () => {
  const loginUser = localStorage.getItem("loginUser");
  const isLogin = !!loginUser;
  // const isLogin = loginUser ? true : false;
  return (
      isLogin ? <Navigate to="/info" /> : <Outlet />
  )
}

const UserRoute = () => {
  const loginUser = localStorage.getItem("loginUser");
  const isLogin = !!loginUser;
  // const isLogin = loginUser ? true : false;
  return (
      isLogin ? <Outlet /> : <Navigate to="/login" />
  )
}

export default App;
