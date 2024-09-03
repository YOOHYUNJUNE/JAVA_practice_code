import { Button, Grid2, TextField, Typography } from "@mui/material";
import axios from "axios";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

const PostForm = () => {

    // react-hook-form
    const { register, handleSubmit,  watch, formState: { errors }, } = useForm();

    // 경로 이동
    const navigate = useNavigate();

    const onSubmit = async (data) => {
        data.authorId = 1; // 로그인 기능 전까지 임의로 지정
        console.log("서버에 게시글 요청을 보낼 데이터 : ", data);

        try {
            // 서버에 요청
            const res = await axios.post("http://localhost:8080/api/post", data);
            // 정상이면 게시글 목록으로 보냄
            navigate("/post");
        } catch (error) {
            // 비정상이면 에러페이지로
            navigate("/error");
        }




    }
    // watch("name") : 입력된 "name"값을 가져옴
    // console.log(watch("name"))


    return (
        <>
        { /* handleSubmit : onSubmit 동작 전 입력값 유효 검증 */ }
        <form onSubmit={handleSubmit(onSubmit)}>
            {/* ...register("이름") 값 전달. 필수값, 유효성 검증 등을 추가할 수 있음 */}
            <Grid2 container direction={"column"} spacing={3} sx={{width: {xs:'250px', sm:'500px'}}}>
                {/* 제목(필수, 50자 이하) */}
                    <TextField 
                        variant="outlined" 
                        label="제목" 
                        error={errors.title && true} 
                        helperText={errors.title && "제목입력은 필수이며, 50자 이내로 작성해주세요."}
                        {...register("title", {required:true, maxLength:50})}
                    />

                {/* 내용(필수, 제한없음) */}
                    <TextField
                        label="내용"
                        multiline
                        rows={4}
                        error={errors.content && true}
                        helperText={errors.content && "내용은 필수입력입니다."}
                        {...register("content", {required:true})}
                    />

                {/* 비밀번호(필수, 영어+숫자 8자리 이상) */}
                    <TextField
                        id="outlined-password-input"
                        label="비밀번호"
                        error={errors.password && true}
                        helperText={errors.password && "영어와 숫자를 포함해 8자 이상 20자 이하로 생성해주세요."}
                        {...register("password", {required:true})}
                    />
            <Button type="submit" variant="outlined" color="main" sx={{width:"50%"}}>제출</Button>
            </Grid2>


        </form>
        </>
    );
}
 
export default PostForm;