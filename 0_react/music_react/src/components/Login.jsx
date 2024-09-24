import { useState } from "react";

const Login = () => {
    // usf
    const [userId, setUserId] = useState("");
    const [userPw, setUserPw] = useState("");

    const handleLogin = () => {
        // alert(`로그인 시도 : ${userId} / ${userPw}`);
        // 비동기통신 axios (id, pw)
        // 가정: 만약에 회원가입한 아이디는 1이고 비밀번호는 2
        if (userId === "1" && userPw === "2") {
            alert('성공')
        } else {
            alert('실패')
            setUserId("");
            setUserPw("");
        }
    }
    return ( 
        <>
            <div>
                <label htmlFor="userId">아이디</label>
                <input type="text" id="userId" value={userId} onChange={(e) => setUserId(e.target.value)}></input>
            </div>
            <div>
                <label htmlFor="userPw">비밀번호</label>
                <input type="text" id="userPw" value={userPw} onChange={(e) => setUserPw(e.target.value)}></input>
            </div>
            <button onClick={handleLogin}>로그인</button>
        </>
     );
}
 
export default Login;