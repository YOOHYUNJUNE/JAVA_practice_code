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



const UserCard = ({ user }) => {
    // react-hook-form
    const { register, handleSubmit, watch, formState: { errors }, setValue } = useForm();

    const navigate = useNavigate();

    // 수정을 위해 데이터 가져오기
    const {state} = useLocation();

    useEffect(() => {
        if (state) {
            setValue("name", state.name);
            setValue("url", state.email);
            setValue("password", state.password);
        }
    }, []);


    // 삭제
    // 삭제 메소드
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
            const  { value:password } = await Swal.fire({
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
                console.log("비밀번호: ", password)
                try {
                    await userAPI.deleteUser({email:user.email, password});
                    Swal.fire({
                        title: "안녕",
                        text: `${user.name}님 또 만나요.`,
                        icon: "success"
                    });
                    navigate('/userlist');
                } catch (error) {
                    navigate("/error", {state:error.message})
                    console.log("삭제할 유저정보 : ",user.id, user.email, user.password);
                    
                }
            }
        } // 삭제 분기
            
        } // 삭제 버튼


        // 수정
        const handleModify = async() => {

            // 비밀번호 입력
            const { value:newPassword } = await Swal.fire({
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
            if(newPassword) {
                try {
                    // 수정창
                    const { value:newPassword } = await Swal.fire({
                        title : "비밀번호 변경",
                        input : "text",
                        inputLabel : "비밀번호를 변경합니다.",
                        inputPlaceholder : "비밀번호",
                        showCancelButton : true
                    }); 
                    if (newPassword) {
                        console.log("신규 비밀번호 : ", newPassword) 
                        await userAPI.modifyUser({email:user.email, password:newPassword});
                        console.log("수정완료")
                        navigate("/user")
                    }

                } catch (error) {
                    navigate("/error", {state:error.message})
                    console.log("수정 실패", user.email)                
                }
            }

           

        } // 수정 버튼
        
        
        return (
            
            <>
            <tr>
                <td>{user.id}</td>
                <td>{user.name}</td>
                <td>{user.email}</td>
                <td>{user.createdAt}</td>
                <td><Button variant="outlined" color="primary" onClick={handleModify}>수정</Button></td>
                <td><Button variant="outlined" color="secondary" onClick={handleDelete}>탈퇴</Button></td>
            </tr>

        </>


    );
}
 
export default UserCard;