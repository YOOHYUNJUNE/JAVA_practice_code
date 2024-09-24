import './App.css';

function App() {
  const name = "React";
  const url = "https://naver.com";
  const style = {color: 'red', backgroundColor: 'black'};
  const num = 10;

  return (
    <div>
      {/* 중괄호를 이용해 변수 삽입 가능 */}
      <h1>{name + "JS"} 시작</h1>
      <a href={url}>네이버</a>

      {/* 단일태그도 태그를 닫야아 한다 */}
      <br></br>
      <input type='text'></input>
      <br/>
      <input type='password' />

      {/* class, for => className, htmlFor */}
      <div className="title">제목</div>
      <label htmlFor="user_id">아이디</label>
      <input type='text' id="user_id"></input>

      {/* style */}
      <div style={{color: 'red', backgroundColor: 'black'}}>안녕하세요</div>
      <div style={style}>안녕하세요</div>

      <div>
        {
          num > 10 ? 
          <h1>num은 10보다 크다.</h1> :
          <h1>num은 10보다 같거나 작다.</h1>
        }
      </div>
    </div>
  );
}

export default App;
