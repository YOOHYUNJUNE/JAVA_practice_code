import { useEffect, useState } from "react";
import { userAPI } from "../../api/services/user";
import { Navigate, useNavigate } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Box, Button, Divider, Grid2, TableContainer, Paper } from '@mui/material';
import Swal from "sweetalert2";
import UserCard from "./UserCard";


const User = () => {
    const navigate = useNavigate();

    // 백엔드에서 user 데이터 불러오기
    const [userList, setUserList] = useState([]);

    // 유저 목록 가져오기
    const getUserList = async() => {
        try {
            const res = await userAPI.getUserList();
            const data = res.data;
            setUserList(data);
            console.log("회원 목록 : ", data);
        } catch (error) {
            navigate("/error", {state:error.message})
        }
    }

    useEffect(() => {
        getUserList();
    }, []);



    return (
        <>
        <h1 class="m-3">회원 목록</h1>
        <Button variant="contained" color='main' onClick={() => navigate("/userjoin")}>유저추가</Button>
        <TableContainer style={{textAlign:'center', width:'80%'}}>

        <table className="table table-striped" aria-label="user table" >
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
        </TableContainer>
        </>
    );
}
 
export default User;