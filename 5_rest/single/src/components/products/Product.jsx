import { Box, Button, Divider, Grid2 } from '@mui/material';
import axios from "axios";
import { useEffect, useState } from 'react';
import ProductCard from './ProductCard';
import { useLocation, useNavigate } from 'react-router-dom';
import { productAPI } from '../../api/services/product';
import { useAuth } from '../../hooks/useAuth';

const Product = () => {

    // 검색결과 state로 가져오기
    const {state} = useLocation();

    // onClick 이동 경로 지정
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
                console.log("게시물 리스트 출력", data);
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

    return (
        <>
        <h1>상품 목록</h1>
        {/* 글쓰기 양식 */}
        {
            userInfo?.role === "ROLE_ADMIN" &&
            <>
            <Button variant="contained" color='main' onClick={() => navigate("/product/add")}>상품 추가</Button>
            <Divider />
            </>
        }
        {/* 전체 리스트 */}
        <Box sx={{flexGrow:1}}>
            <Grid2 container spacing={2} columns={24}>
            {
                productList.map(product => (
                    <Grid2 size={6}>
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