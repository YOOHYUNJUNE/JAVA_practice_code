import { Button, Grid2, TextField, Typography } from "@mui/material";
import axios from "axios";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { favAPI } from "../../api/services/favorite";

const FavoriteForm = () => {


    // react-hook-form
    const { register, handleSubmit, watch, formState: { errors }, setValue } = useForm();

    // 경로 이동
    const navigate = useNavigate();

    // Id가 없으면 작성, 있으면 수정
    const { favId } = useParams();

    // 즐겨찾기 수정
    const {state} = useLocation();

    useEffect(() => {
        if (state) {
            setValue("title", state.title);
            setValue("url", state.url);
        }
    }, []);

    const onSubmit = async (data) => {

        // 이미지 등록시 필요한 데이터 가져오기
        data.image = data.image[0];

        // 첨부파일은 JSON이 안되므로 Form으로 변환
        const formData = new FormData();
        
        Object.keys(data).forEach(key => {
            formData.append(key, data[key]); // (title, data.title)
        })


        try {
            if (state) {
                formData.append("id", state.id);
                // 서버에 요청
                // 수정
                const res = await favAPI.modifyFav(formData);
                console.log("state : ", state.id);
                console.log(state.title);
                console.log(state.url);
            } else {
                // 추가
                const res = await favAPI.writeFav(formData);
            }
            // 정상이면 게시글 목록으로 이동
            navigate("/favorite");
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
                        label="이름" 
                        error={errors.title && true}
                        helperText={errors.title && "즐겨찾기할 사이트의 이름을 적어주세요."}
                        {...register("title", {required:true})}
                    />

                {/* 내용(필수, 제한없음) */}
                    <TextField
                        label="주소"
                        multiline
                        error={errors.url && true}
                        helperText={errors.url && "url을 적어주세요."}
                        defaultValue={"https://www."}
                        {...register("url", {required:true})}
                    />

                {/* 이미지 등록 (선택) */}
                    <TextField 
                        type="file"
                        label="이미지 파일"
                        {...register("image", {required:false})}
                        slotProps={{ htmlInput: {"accept": "image/*"}}}
                    />

            <div style={{ textAlign: 'right'}}>
                <Button type="submit" variant="outlined" color="primary">등록</Button>
            </div>
                
            </Grid2>


        </form>
        </>
    );
}
 
export default FavoriteForm;