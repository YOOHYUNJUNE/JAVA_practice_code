import { useEffect, useState } from "react";
import { userAPI } from "../../api/services/user";
import { Navigate, useNavigate } from "react-router-dom";

const User = () => {
    const navigate = useNavigate();

    // 백엔드에서 user 데이터 불러오기
    const [userList, setUserList] = useState([]);

    const getUserList = async() => {
        try {
            const res = await userAPI.getUserList();
            const data = res.data;
            setUserList(data);
        } catch (error) {
            navigate("/error", {state:error.message})
        }
    }

    useEffect(() => {
        getUserList();
    }, []);


    return (
        <>
            회원관리 ㅋㅋ
            {
                userList.map(user => (
                    <div key={user.id}>
                        {user.name}
                    </div>
                ))
            }
        </>
    );
}
 
export default User;