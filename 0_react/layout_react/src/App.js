import './App.css';
import Layout from './components/Layout';

function App() {
  return (
    <div className="App">
      <Layout>
        <div>
          <h1>게시판 화면</h1>
          <table>
            <tr>
              <th>번호</th>
              <th>제목</th>
              <th>작성자</th>
            </tr>
            <tr>
              <td>1</td>
              <td>안녕하세요</td>
              <td>최인규</td>
            </tr>
            <tr>
              <td>2</td>
              <td>오늘은 비가 오네요</td>
              <td>최인규</td>
            </tr>
          </table>
        </div>
      </Layout>
    </div>
  );
}

export default App;
