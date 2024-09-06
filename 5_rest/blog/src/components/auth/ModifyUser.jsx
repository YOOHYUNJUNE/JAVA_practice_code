import { Box, Button, Divider, FormControl, FormLabel, Grid2, InputBase, Paper, Stack, TextField, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { userAPI } from "../../api/services/user";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";


// 유저 정보수정 (요청 : name, email, password)

const ModifyUser = () => {
    /* REACT HOOK FORM */
    const { register, formState: { errors }, handleSubmit, setValue, getValues, setError, clearErrors } = useForm();

    /* navigate */
    const navigate = useNavigate();

    /* 수정 */
    const onSubmit = async (data) => {
        try {
            console.log("수정 로직", data);
            const res = await userAPI.modifyUser(data);
            if (res.status === 200) {
                // 수정 성공 시 메시지와 함께 메인으로 이동
                Swal.fire({
                    html: "회원정보가 변경되었습니다.",
                    timer: 1500,
                    timerProgressBar: true,
                }).then(navigate("/user"));
            }
        } catch (error) {
            navigate("/error", {state: error.message});
            console.error(error);
            console.log(data)
        }
    }

    return (
        <>
       
       
        </>
    );
}
 
export default ModifyUser;