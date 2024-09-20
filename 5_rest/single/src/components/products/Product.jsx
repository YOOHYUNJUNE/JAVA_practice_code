import { Box, Button, Divider, Grid2, Typography } from '@mui/material';
import axios from "axios";
import { useEffect, useState } from 'react';
import ProductCard from './ProductCard';
import { useLocation, useNavigate } from 'react-router-dom';
import { productAPI } from '../../api/services/product';
import { useAuth } from '../../hooks/useAuth';

const Product = () => {
    // 검색결과 state로 가져오기
    const {state} = useLocation();

    const navigate = useNavigate();

    // 백엔드로부터 가져오기
    // state만들기 -> axios사용해서 set -> useEffect 함수 적용 -> 화면
    const [productList, setProductList] = useState([]);

    const getProductList = async() => {

        // 검색 결과에 따른 분기
        if (state) {
            setProductList(state);
        } else {
            try {
                const res = await productAPI.getProductList();
                const data = res.data;            
                setProductList(data);
                console.log("상품 리스트 출력", data);
            } catch (error) {
                navigate("/error", {state:error.message})
            }
        }

    }
    
    useEffect(() => {
        getProductList();
    }, [state]); // state가 변경시에도 useEffect가 작동되게
   
    // 로그인 여부 확인
    const { userInfo } = useAuth();

    const productP1Style = {fontSize: {md: "16px", lg: "20px"}, fontWeight:"lighter"}
    const productP2Style = {fontSize: {md: "34.56px", lg: "50.4px"}, fontWeight:"bold"}
    const productP3Style = {fontSize: {md: "10.8px", lg: "11.7px"}, margin:"10px"}

    return (
        <>
        <Typography component='p' sx={productP1Style}>메가MGC커피의 엄선된 메뉴</Typography>
        <Typography component='p' sx={productP2Style}>MEGA MENU</Typography>
        <Typography component='p' sx={productP3Style}>※메뉴 이미지는 연출컷이라 실물과 다를 수 있습니다.</Typography>

        {/* 글쓰기 양식 */}
        {
            userInfo?.role === "ROLE_ADMIN" &&
            <>
            <Button variant="contained" color='main' onClick={() => navigate("/product/add")}>상품 추가</Button>
            <Divider />
            </>
        }
        {/* 전체 리스트 */}
        <Box sx={{flexGrow:1, textAlign:'center', justifyContent:'center', display: 'flex'}}>
            <Grid2 container spacing={2} columns={12} width={'70%'} border={"none"}>
            {
                productList.map(product => (
                    <Grid2 size={{xs:12, sm:6, md:4, lg:3}}>
                    <ProductCard key={product.key} product={product}></ProductCard>
                    </Grid2>
                ))
            }
            </Grid2>
        </Box>

        </>
    );
}
 
export default Product;