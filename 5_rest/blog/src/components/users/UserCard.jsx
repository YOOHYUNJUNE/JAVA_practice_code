import { useTheme } from "@emotion/react";
import { Avatar, Card, CardHeader, CardMedia, CardContent, Typography, IconButton, CardActions, Button } from "@mui/material"; 
import { useLocation, useNavigate, useParams } from "react-router-dom";
import SaveIcon from "@mui/icons-material/Save";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import Swal from "sweetalert2";
import { useEffect, useState } from "react";
import { userAPI } from "../../api/services/user";
import { useForm } from "react-hook-form";



const UserCard = ( { user }) => {
    // react-hook-form
    const { register, handleSubmit, watch, formState: { errors }, setValue } = useForm();

    const navigate = useNavigate();

    // 수정을 위해 데이터 가져오기
    const {state} = useLocation();

    useEffect(() => {
        if (state) {
            setValue("name", state.name);
            setValue("url", state.email);
            setValue("url", state.password);
        }
    }, []);


    // 삭제
    const handleDelete = async () => {
        const result = await Swal.fire({
            title: "이런",
            text: `${user.name}님, 탈퇴하실건가요?`,
            showCancelButton: true,
            confirmButtonText: "네",
            cancelButtonText: "조금만 더 생각해볼게요"            
          });

        // 삭제 분기
        if (result.isConfirmed) {

            // 비밀번호 입력
            const {value:password} = await Swal.fire({
                title: "비밀번호 입력",
                input: "password",
                inputLabel: '비밀번호를 입력해주세요',
                inputPlaceholder: '비밀번호',
                inputAttributes: {
                    autocapitalize: 'off'
                },
                showCancelButton: true
            });

            // 비밀번호 일치 여부
            if(password) {
                try {
                    await userAPI.deleteUser({email:user.email, password});
                    Swal.fire({
                        title: "안녕",
                        text: `${user.name}님 또 만나요.`,
                        icon: "success"
                    });
                    navigate('/user');
                } catch (error) {
                    navigate("/error", {state:error.message})
                    console.log(user.id);
                    console.log(user.name);
                    console.log(user.email);
                    
                }
            }
        } // 삭제 분기
            
        } // 삭제 버튼


        // 수정
        const handleModify = async() => {

            // 수정 창
            const result = await Swal.fire({
                title : "유저 확인",
                html:
                '<input id="email" class="swal2-input" placeholder="이메일">' +
                '<input id="password" class="swal2-input" placeholder="비밀번호">'
                ,
                focusConfirm : false,
                showCancelButton: true,
                preConfirm: async () => {
                    const email = document.getElementById("email").value;
                    const password = document.getElementById("password").value;
                    if(!email || !password) {
                        Swal.showValidationMessage("이메일 또는 비밀번호를 입력해주세요.");
                        return false;
                    }
                    // 일치 여부
                    // DB에서 유저 정보 가져오기
                    if(email && password) {
                        try {
                            await userAPI.modifyUser({email:user.email, password});
                            Swal.fire({
                                title: "안녕",
                                text: `${user.name}님`,
                                icon: "success"
                            });
                            navigate('/user');
                        } catch (error) {
                            navigate("/error", {state:error.message})
                            console.log(user);
                            console.log(user.id);
                            console.log(user.name);
                            console.log(user.email);
                            console.log(user.password);                            
                        }
                    }

                } // 입력창

            }) // 수정 창


           

        } // 수정 버튼
        
        
        return (
            
            <>
            <tr>
                <td>{user.id}</td>
                <td>{user.name}</td>
                <td>{user.email}</td>
                <td>{user.createdAt}</td>
                <td><button onClick={handleModify}>수정</button></td>
                <td><button onClick={handleDelete}>탈퇴</button></td>
            </tr>

        </>


    );
}
 
export default UserCard;