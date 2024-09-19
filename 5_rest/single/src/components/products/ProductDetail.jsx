import { Avatar, Button, Card, CardActions, CardContent, CardHeader, CardMedia, Typography } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import HouseSharpIcon from '@mui/icons-material/HouseSharp';
import Swal from "sweetalert2";
import { productAPI } from "../../api/services/product";
import { useAuth } from "../../hooks/useAuth";

const ProductDetail = () => {
    // 필요값 : id (주소창(/api/product/id)에 있음)
    const {productId} = useParams();
    const navigate = useNavigate();

    const [product, setProduct] = useState();

    const getProduct = async () => {
        try {
            const res = await productAPI.getProduct(productId);
            const data = res.data;
            setProduct(data);
            // ProductDetail은 setProduct(state가 바뀔때)마다 실행되기때문에 무한 반복
            // -> useEffect() 사용
        } catch (error) {
            navigate("/error", {state:error.message})
        }
    }
    // 가져온 정보 보여주기
    useEffect(() => {
        getProduct();        
    }, []);

    // 상품 삭제
    const handleDelete = async () => {
       const result = await Swal.fire({
            title: "이런",
            text: `${product.name} 을 삭제합니다.`,
            showCancelButton: true,
            confirmButtonText: "네",
            cancelButtonText: "조금만 더 생각해볼게요"            
          });

        // 삭제 분기
        if (result.isConfirmed) {
            try {
                await productAPI.deleteProduct(product.id, product.author);
                Swal.fire({
                    title: "안녕",
                    text: `${product.name} 삭제되었습니다.`,
                    icon: "success"
                });
                navigate('/product');
            } catch (error) {
                navigate("/error", {state:error.message})
                
            }
        
        }
    }

    // 로그인 여부 확인
    const { userInfo } = useAuth();

    return (
        <>
        <h1>게시물 상세정보</h1>
        {product &&
        <Card sx={{width: {xs:'250px', sm:'500px', md:'800px'}}}>
            <CardHeader
                avatar={
                    <Avatar>
                        {product.id}
                    </Avatar>
                }
                title={product.title}
                subheader={product.createdAt}
            />

            {
                product.image && <CardMedia
                                component="img"
                                image={`${process.env.REACT_APP_SERVER}/img/${product.image.saved}`}
                                alt="상품 이미지"
                                />
            }
            
            <CardContent>
                <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                    {product.detail}
                </Typography>
                <Typography gutterBottom sx={{ color: 'text.secondary', fontSize:12, textAlign: 'right'}} >
                    {product.author.name} {product.author.email}
                </Typography>
            </CardContent>

            <CardActions>
                {
                    userInfo?.role === "ROLE_ADMIN" &&
                    <>
                    <Button 
                        variant="contained" 
                        color="bg2" 
                        size="small" 
                        startIcon={<EditIcon/>}
                        onClick={() => navigate(`/product/modify/${product.id}`)}
                    >수정</Button>
                    <Button 
                        variant="contained" 
                        color="sub" 
                        size="small" 
                        startIcon={<DeleteIcon/>}
                        onClick={handleDelete}
                    >삭제</Button>                    
                    </>
                }
            <Button 
                variant="contained" 
                color="bg1" 
                size="small"
                startIcon={<HouseSharpIcon/>}
                onClick={() => navigate("/product")}
            >메뉴</Button>
            </CardActions>

        </Card>
        }
        </>
    );
}

export default ProductDetail;