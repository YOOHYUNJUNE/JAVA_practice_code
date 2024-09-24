import { useState } from "react";

const StatePractice = () => {
    const [result, setResult] = useState(0);
    const [x, setX] = useState(0);
    return (
        <>
            <div>
                <h1>{result}</h1>
                <div>
                    <input type="number" value={x} onChange={(e)=> setX(e.target.value)}/>
                </div>
                <div style={{width: '50%', display:'flex', margin: "0 auto"}}>
                    {/* parseInt(x) -> x를 숫자로 변환 */}
                    <button onClick={() => {}}>더하기</button>
                    <button onClick={() => {}}>빼기</button>
                    <button onClick={() => {}}>곱하기</button>
                    <button onClick={() => {}}>나누기</button>
                </div>
            </div>
        </>
    );
}
 
export default StatePractice;