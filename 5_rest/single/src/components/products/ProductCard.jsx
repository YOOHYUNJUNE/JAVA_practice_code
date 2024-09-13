import { useTheme } from "@emotion/react";
import { Avatar, Card, CardHeader, CardMedia, CardContent, Typography, IconButton } from "@mui/material"; 
import { useNavigate } from "react-router-dom";
import SaveIcon from "@mui/icons-material/Save";

const ProductCard = ( { product }) => {
    const navigate = useNavigate();
    const theme = useTheme();

    // const handleDownload = (e) => {
    //     e.stopPropagation();
    //     window.location.href = `${process.env.REACT_APP_REST_SERVER}/product/download/${product.image.id}`
    // }

    return (
        <Card 
            sx={{ 
                maxWidth: 500, maxHeight: 600, cursor: 'pointer',
                transition:'transform 0.2s', '&:hover':{transform:'scale(1.05)'}
            }} 
            onClick={() => navigate(`/product/${product.id}`)}>
            <CardHeader
                avatar={<Avatar>{product.id}</Avatar>}
                name={product.name}
                subheader={product.ename}
                // // 이미지 첨부 여부를 아이콘으로 보여줌 -> iceOrHot으로 이용
                // action={product.image && 
                //     <IconButton>
                //         <SaveIcon onClick={(e) => handleDownload(e)} />
                //     </IconButton> }
            />
            {
                product.image &&
                <CardMedia
                component="img" height="194" alt="상품 이미지"
                image={`${process.env.REACT_APP_SERVER}/img/${product.image.saved}`}                
                />
            }
            <CardContent>
                <Typography gutterBottom sx={{ color: 'text.secondary', fontSize:12 }}>
                    {product.price}원
                </Typography>
                <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                    {product.detail}
                </Typography>
                <Typography gutterBottom sx={{ color: 'text.secondary', fontSize:12 }}>
                    {product.caffeine}mg | {product.calorie}kcal
                </Typography>
                <Typography gutterBottom sx={{ color: 'text.secondary', fontSize:12 }}>
                    알레르기 : {product.allergy}
                </Typography>
                <Typography gutterBottom sx={{ color: 'text.secondary', fontSize:12 }}>
                    {product.author.name} - {product.author.email}
                </Typography>
                
                
            </CardContent>
        </Card>
    );
}
 
export default ProductCard;