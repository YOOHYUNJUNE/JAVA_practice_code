import logo from "./logo.svg";
import "./App.css";
import Login from "./components/Login";
import { useState } from "react";
import Music from "./components/Music";

function App() {
  const [musicList, setMusicList] = useState([
    {
      id: new Date().getTime(),
      title: "Love wins all",
      like: 0,
      boom: true
    },
  ]);

  const [musicInput, setMusicInput] = useState("");

  const addMusic = () => {
    musicInput && setMusicList([...musicList, {
      id: new Date().getTime(),
      title: musicInput,
      like: 0,
      boom: true
    }])
  }

  return (
    <div className="App">
      {/* <Login /> */}
      <div>
        <h1>플레이리스트</h1>
      </div>
      <div className="music-box">
        <div>
          <label htmlFor="title_input"></label>
          <input
            id="title_input"
            type="text"
            placeholder="음악 제목을 입력하세요"
            value={musicInput}
            onChange={(e) => setMusicInput(e.target.value)}
          />
          <button onClick={addMusic}>추가</button>
        </div>
        {musicList.map((music, idx) => {
          return (
            <Music
              key={music.id}
              music={music} idx={idx}
              musicList={musicList} setMusicList={setMusicList} />
          );
        })}
      </div>
    </div>
  );
}

export default App;