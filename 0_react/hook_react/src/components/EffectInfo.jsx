import { useState } from "react";
import { useEffect } from "react";

const EffectInfo = () => {
    const [email, setEmail] = useState("");
    const [name, setName] = useState("");
    const handleEmail = (e) => setEmail(e.target.value);
    const handleName = (e) => setName(e.target.value);

    // 컴포넌트 사라질 때
    useEffect(() => {
        console.log("1. 렌더링~🌈");
        return () => console.log("2. 컴포넌트 사망~✋")
    }, []);

    // 컴포넌트가 처음 나타날 때 (한 번만 실행)
    // useEffect(() => {
    //     console.log("2. 최초 렌더링~🌈"); 
    // }, []);

    // useEffect(() => {
    //     for(let i=0; i < 1_000_000_000; i++) { }
    //     console.log("최초로 컴포넌트가 나타났거나, 이름이 변경되었다.");
    // }, [name]);

    // useEffect(() => {
    //     console.log("최초로 컴포넌트가 나타났거나, 이메일이 변경되었다.");
    // }, [email]);

    // useEffect(() => {
    //     console.log("최초로 컴포넌트가 나타났거나, 이름 또는 이메일이 변경되었다.");
    // }, [name, email]);



    return ( 
        <>
            <h1>useEffect 사용</h1>
            <p>
                use로 시작하면 99.9% hook이다. <br/>
                useEffect(콜백함수, [의존성배열]) <br/>
                의존성 배열 : 의존하는 값이 들어있는 배열 <br/>
                <br/>
                컴포넌트는 생명주기(Life Cycle)가 있다. <br/>
                1. 컴포넌트 처음 나타날 때 (mount) <br/>
                2. 컴포넌트 업데이트 전/후 (update) <br/>
                3. 컴포넌트 사라질 때 (unmount) <br/>
                <br/>
                useEffect를 사용하는 경우 <br/>
                1. 어려운 연산을 할 때 <br/>
                2. 서버 API를 호출 <br/>
                3. 타이머 <br/>
                4. 로컬스토리지 사용 <br/>
                5. 외부 라이브러리 사용 <br/>
                <br/>
                <div>
                    <label>
                        이메일
                        <input type='text' value={email} onChange={handleEmail} />
                    </label>
                    <label>
                        이름
                        <input type='text' value={name} onChange={handleName} />
                    </label>
                </div>
                <div>
                    {`이메일: ${email} / 이름 : ${name}`}
                </div>
            </p>
        </>
    );
}
 
export default EffectInfo;