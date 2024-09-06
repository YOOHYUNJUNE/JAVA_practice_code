import { useEffect, useState } from "react";
import { userAPI } from "../../api/services/user";
import { Navigate, useNavigate } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Box, Button, Divider, Grid2 } from '@mui/material';
import Swal from "sweetalert2";
import UserCard from "./UserCard";


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
        <h1>회원 목록</h1>
        <Button variant="contained" color='main' onClick={() => navigate("/signup")}>유저추가</Button>
        <table className="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>이름</th>
                            <th>Email</th>
                            <th>가입일</th>
                            <th>수정</th>
                            <th>탈퇴</th>
                        </tr>
                    </thead>
                    
                    <tbody>
                        {
                            userList.map(user => (
                                <UserCard key={user.id} user={user}/>
                            ))
                        }

                    </tbody>

        </table>
        </>
    );
}
 
export default User;