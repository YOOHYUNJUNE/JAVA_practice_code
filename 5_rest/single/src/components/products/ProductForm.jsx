import { Button, Grid2, TextField, Typography } from "@mui/material";
import axios from "axios";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import { productAPI } from "../../api/services/product";
import { useAuth } from "../../hooks/useAuth";
import NotFound from "../common/NotFound";
import 'bootstrap/dist/css/bootstrap.min.css';


const ProductForm = () => {

    // react-hook-form
    const { register, handleSubmit, watch, formState: { errors }, setValue } = useForm();

    // 경로 이동
    const navigate = useNavigate();

    // 유저 정보 가져오기
    const { userInfo } = useAuth();
    const role = userInfo?.role;
    console.log("userInfo : ", userInfo);
    console.log("role : ", role);
    console.log("userInfo.role : ", userInfo.role);
    
    // productId가 없으면 작성, 있으면 수정
    const { productId } = useParams();

    // 게시물 수정
    const getProduct = async () => {
        try {
            const res = await productAPI.getProduct(productId);
            const data = res.data;
            console.log(data);
            // ProductDetail은 setProduct(state가 바뀔때)마다 실행되기때문에 무한 반복
            // -> useEffect() 사용
            setValue("name", data.name);
            setValue("detail", data.detail);
            setValue("ename", data.ename);
            setValue("price", data.price);
            setValue("iceOrHot", data.iceOrHot);
            setValue("calorie", data.calorie);
            setValue("sugar", data.sugar);
            setValue("caffeine", data.caffeine);
            setValue("allergy", data.allergy);
            
        } catch (error) {
            navigate("/error", {state:error.message})
        }
    }
    useEffect(() => {
        // productId가 있으면 게시물 정보 가져오기(수정)
        if (productId) {
            getProduct();
        }
    }, [productId]);

    const onSubmit = async (data) => {

        // 이미지 등록시 필요한 데이터 가져오기
        data.image = data.image[0];

        // 첨부파일은 JSON이 안되므로 Form으로 변환
        const formData = new FormData();
        
        Object.keys(data).forEach(key => {
            formData.append(key, data[key]); // (name, data.name)
        })


        try {
            if (productId) {
                formData.append("id", productId);
                // 서버에 요청
                // 수정
                const res = await productAPI.modifyProduct(formData);
            } else {
                // 추가
                const res = await productAPI.addProduct(formData);
            }
            // 정상이면 게시글 목록으로 이동
            navigate("/product");
        } catch (error) {
            // 비정상이면 에러페이지로
            navigate("/error", {state:error.message})
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

                {/* 이름(필수) */}
                    <TextField 
                        variant="outlined" 
                        label="이름" 
                        error={errors.name && true} 
                        {...register("name", {required:true, maxLength:50})}
                    />

                <div className="d-flex justify-content-between">
                {/* 영어 이름 */}
                    <TextField
                        label="영어 이름"
                        multiline
                        error={errors.ename && true}
                        {...register("ename", {required:false})}
                        />
                {/* 가격 */}
                <TextField
                        label="가격(원)"
                        value={0}
                        multiline
                        error={errors.price && true}
                        {...register("price", {required:false})}
                    />
                </div>

                {/* 내용 */}
                <TextField
                        label="내용"
                        multiline
                        rows={4}
                        error={errors.detail && true}
                        {...register("detail", {required:false})}
                    />

                <div className="d-flex justify-content-between">
                {/* 아이스or핫 */}
                <TextField
                        label="아이스or핫"
                        multiline
                        error={errors.iceOrHot && true}
                        {...register("iceOrHot", {required:false})}
                    />

                {/* 칼로리 */}
                <TextField
                        label="칼로리(kcal)"
                        multiline
                        error={errors.calorie && true}
                        {...register("calorie", {required:false})}
                    />
                </div>

                <div className="d-flex justify-content-between">
                {/* 당류 */}
                <TextField
                        label="당 함량(g)"
                        multiline
                        error={errors.sugar && true}
                        {...register("sugar", {required:false})}
                    />
                {/* 카페인 */}
                <TextField
                        label="카페인 함량(mg)"
                        multiline
                        error={errors.caffeine && true}
                        {...register("caffeine", {required:false})}
                    />
                </div>

                {/* 알레르기 */}
                <TextField
                        label="알레르기 성분"
                        multiline
                        error={errors.allergy && true}
                        {...register("allergy", {required:false})}
                    />

                {/* 이미지 등록 (선택) */}
                    <TextField 
                        type="file"
                        label="이미지 파일"
                        {...register("image", {required:false})}
                        slotProps={{ htmlInput: {"accept": "image/*"}}}
                    />

            <div style={{ textAlign: 'right'}}>
                <Button type="submit" variant="outlined" color="primary">제출</Button>
                <Button variant="outlined" color="primary" onClick={() => navigate("/product")}>돌아가기</Button>
            </div>
                
            </Grid2>


        </form>
        </>
    );
}
 
export default ProductForm;