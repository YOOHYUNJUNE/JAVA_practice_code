import logo from './logo.svg';
import './App.css';
import GrandFather from './components/GrandFather';
import { SamsungContext } from './contexts/SamsungContext';
import { MyColorContext } from './contexts/MyColorContext';
import { useState } from 'react';
import ColorBox from './components/ColorBox';
import Color from './components/Color';
import StatePractice from './components/StatePractice';
import ReducerPractice from './components/ReducerPractice';

function App() {
  const [myColor, setMyColor] = useState('red');

  return (
    <MyColorContext.Provider value={{ myColor, setMyColor}}>
      <SamsungContext.Provider value={"삼성주식"}>
        <div className="App">
          {/* {myColor} */}
          {/* <GrandFather /> */}
          {/* <ColorBox /> */}
          {/* <Color /> */}
          {/* <StatePractice /> */}
          <ReducerPractice />
        </div>
      </SamsungContext.Provider>
    </MyColorContext.Provider>
  );
}

export default App;
