import logo from './logo.svg';
import './App.css';
import EffectInfo from './components/EffectInfo';
import { useState } from 'react';

function App() {
  const [show, setShow] = useState(true);
  return (
    <div className="App">
      <button onClick={()=> setShow(!show)}>보였다 안보였다</button>
      { show && <EffectInfo /> }
    </div>
  );
}

export default App;
