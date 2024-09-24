// useReducer는 무엇인가..!!!
// useState보다 더 다양한 컴포넌트 상태 관리가 가능한 훅!

import { useReducer } from "react";

// const [state, setState] = useState(initialValue);
// const [state, dispatch] = useReducer(reducer, initialValue);
//      - 첫번째 매개변수 : reducer...?
//          reducer는 함수이다.
//          reducer = (state, action) => {}
//              state : 상태값
//              action : 특정 타입에 따라서 변화하도록 하는 조건?
//      - 두번째 매개변수 : initialValue...?
//              initialValue : 초기값
//      - dispatch...? [보내다..?]
//              action 객체를 파라미터 받아, reducer 함수를 호출하는 함수
//              dispatch(action객체);

const ReducerPractice = () => {
    const reducer = (state, action) => {
        switch(action.type) {
            case 'plus':
                return {value : state.value + 1};
            case 'minus':
                return {value : state.value - 1};
            case 'reset':
                return {value : 0};
        }
    };

    const [state, dispatch] = useReducer(reducer, {value : 0});

    return (
        <>
            <h1>useReducer</h1>
            <h2>숫자 상태 : {state.value}</h2>
            <button onClick={()=>dispatch({type: "plus"})}>더하기</button>
            <button onClick={()=>dispatch({type: "minus"})}>빼기</button>
            <button onClick={()=>dispatch({type: "reset"})}>초기화</button>
        </>
    );
}
 
export default ReducerPractice;