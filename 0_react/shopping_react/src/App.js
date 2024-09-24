import logo from './logo.svg';
import './App.css';
import Header from './components/layout/Header';
import Product from './components/Product';
import { Routes, Route } from "react-router-dom";
import ProdDetail from './components/product/ProdDetail';
import styled from 'styled-components';

function App() {
  return (
    <div className="App">
      <Header/>
      <Title>안녕하세요</Title>
      <Routes>
        <Route path='/' element={<h1>홈</h1>} />
        <Route path='/about' element={<h1>정보</h1>} />
        <Route path='/service' element={<h1>써비스</h1>} />
        <Route path='/product' element={<Product />} />
        <Route path='/product/:id' element={<ProdDetail />} />
      </Routes>
    </div>
  );
}

const Title = styled.h1`
  font-size: 1.5em;
  text-align: center;
  color: #BF4F74;
`;

export default App;
