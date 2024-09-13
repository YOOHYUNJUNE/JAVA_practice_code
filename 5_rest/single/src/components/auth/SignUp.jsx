import { Box, Button, Divider, FormControl, FormLabel, Grid2, InputBase, Paper, Stack, TextField, Typography } from "@mui/material";
import { useEffect, useId, useState } from "react";
import { useForm } from "react-hook-form";
import { userAPI } from "../../api/services/user";
import { useNavigate, useParams } from "react-router-dom";
import Swal from "sweetalert2";


// 유저 회원가입 (요청 : name, email, password)

const SignUp = () => {
    /* REACT HOOK FORM */
    const { register, formState: { errors }, handleSubmit, setValue, getValues, setError, clearErrors } = useForm();

    /* navigate */
    const navigate = useNavigate();

  
    /* 회원가입 */
    const onSubmit = async (data) => {
        try {
            console.log("회원가입 로직", data);
            const res = await userAPI.addUser(data);
            if (res.status === 201) {
                // 회원가입 성공 시 메시지와 함께 메인으로 이동
                Swal.fire({
                    html: "가입에 성공했습니다.",
                    timer: 1500,
                    timerProgressBar: true,
                }).then(navigate("/userlogin"));
            }
        } catch (error) {
            navigate("/error", {state: error.message});
            console.error(error);
            console.log(data)
        }
    }

    return (
        <>
        { /* handleSubmit : onSubmit 동작 전 입력값 유효 검증 */ }
        <form onSubmit={handleSubmit(onSubmit)}>
            {/* ...register("이름") 값 전달. 필수값, 유효성 검증 등을 추가할 수 있음 */}
            <Grid2 container direction={"column"} spacing={3} sx={{width: {xs:'250px', sm:'500px'}}}>
                {/* 유저 이름 */}
                    <TextField 
                        variant="outlined" 
                        label="이름" 
                        error={errors.name ? true : false}
                        helperText={errors.name && "이름을 적어주세요."}
                        {...register("name", {required:true})}
                    />

                {/* 유저 이메일 */}
                    <TextField
                        label="이메일"
                        error={errors.email ? true : false}
                        helperText={errors.email && "이메일을 적어주세요."}
                        {...register("email", {required:true})}
                    />

                {/* 유저 비밀번호 */}
                    <TextField
                        label="비밀번호"
                        type="password"
                        error={errors.password ? true : false}
                        helperText={errors.password && "비밀번호를 적어주세요."}
                        {...register("password", {required:true})}
                    />
                

            <div style={{ textAlign: 'right'}}>
                <Button type="submit" variant="contained" color="sub">가입하기</Button>
                <Button variant="outlined" color="primary" onClick={() => navigate("/")}>돌아가기</Button>            
            </div>
                
            </Grid2>


        </form>
        </>
    );
}
 
export default SignUp;