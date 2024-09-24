import logo from './logo.svg';
import './App.css';
import Comp from './components/Comp';
// import * as Comps from './components/Comps';
import { Header, Navi, Article } from './components/Comps';
import Introduce from './components/Introduce';

function App() {
  return (
    <div>
      <Header></Header>
      <Navi></Navi>
      {/* props: 부모컴포넌트가 자식컴포넌트에게 전달하는 값 */}
      <Article text="환영합니다"></Article>
      <Article text="만나서 반갑습니다"></Article>
      <Comp></Comp>

      <Introduce name="유재석" gender="남자"></Introduce>
      <Introduce name="김종국" gender="남자"></Introduce>
      <Introduce name="송지효" gender="여자"></Introduce>
      <Introduce name="지석진" gender="남자"></Introduce>
      <Introduce></Introduce>
    </div>
  );
}

export default App;
