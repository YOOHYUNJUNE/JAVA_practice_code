import { useTheme } from "@emotion/react";
import { Avatar, Card, CardHeader, CardMedia, CardContent, Typography, IconButton, Dialog, DialogTitle, DialogContent, Button, CardActions, Box } from "@mui/material"; 
import { useNavigate } from "react-router-dom";
import SaveIcon from "@mui/icons-material/Save";
import { useState } from "react";
import { useAuth } from "../../hooks/useAuth";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import Swal from "sweetalert2";
import { productAPI } from "../../api/services/product";

const ProductCard = ( { product }) => {
    const navigate = useNavigate();
    const theme = useTheme();

    // 로그인 여부 확인
    const { userInfo } = useAuth();

    // 모달 열림 닫힘
    const [open, setOpen] = useState(false);
    // 모달 여는 메소드
    const handleOpen = () => {
        setOpen(true);
    }
    // 모달 닫는 메소드
    const handleClose = () => {
        setOpen(false);
    }

    // 상품 삭제 메소드
    const handleDelete = async () => {
        const result = await Swal.fire({
                title: "상품 삭제",
                text: `${product.name} 삭제합니다.`,
                showCancelButton: true,
                confirmButtonText: "네",
                cancelButtonText: "조금만 더 생각해볼게요",      
            });

            // 삭제 분기
            if (result.isConfirmed) {
                try {
                    await productAPI.deleteProduct(product.id, product.author);
                    Swal.fire({
                        title: "상품 삭제",
                        text: `${product.name} 삭제되었습니다.`,
                        icon: "success",
                    });
                    setOpen(false);
                } catch (error) {
                    navigate("/error", {state:error.message})
                    
                }
            
            }
        }

    // const handleDownload = (e) => {
    //     e.stopPropagation();
    //     window.location.href = `${process.env.REACT_APP_REST_SERVER}/product/download/${product.image.id}`
    // }

    return (
        <>
        {/* Box1 : 카드 전체 영역 */}
        <Box sx={{height:"100%", cursor: 'pointer', paddingLeft:"1px",
            '&:hover .img':{transform:'scale(1.05)'} }} onClick={handleOpen}
            // onClick={() => navigate(`/product/${product.id}`)}            
        >
            {/* <CardHeader
                // avatar={<Avatar>{product.id}</Avatar>}
                // // 이미지 첨부 여부를 아이콘으로 보여줌 -> iceOrHot으로 이용
                // action={product.image && 
                //     <IconButton>
                //         <SaveIcon onClick={(e) => handleDownload(e)} />
                //     </IconButton> }
                /> */}

            {/* Box2 : 이미지 + 이름 카드 영역 */}
            <Box sx={{height:"60%"}}>                
            {
                product.image &&                
                <>
                <CardMedia
                sx={{height:"100%", objectFit:"contain", transition:'transform 0.2s'}}
                className="img" component="img" alt="상품 이미지"
                image={`${process.env.REACT_APP_SERVER}/img/${product.image.saved}`}                
                />
                </>
            }
                <Box sx={{fontSize:"13px", textAlign:"left"}}>
                    {product.name}
                    <hr/>
                </Box>
            </Box>

            {/* Box3 : 상세정보 영역 */}
            <Box sx={{textAlign:"left", paddingTop:"40px"}}>

                <Typography gutterBottom sx={{textAlign:"right", paddingRight:"1px", color:'text.secondary', fontSize:12 }}>
                    {product.price}원
                </Typography>
                <Box >
                    <Box component="div" variant="body2" 
                        sx={{color:"text.secondary",
                            fontSize:"11.7px",
                            overflow:"hidden",
                            textOverflow:"ellipsis", 
                            display:"-webkit-box", 
                            WebkitBoxOrient:"vertical", 
                            WebkitLineClamp:2
                            // whiteSpace:"normal",
                            // wordWrap:"break-word", 
                            // boxSizing:"border-box",
                            // height:"2.6em", 
                        }}>
                        {product.detail}
                    </Box>
                </Box>
                
            </Box>

        </Box>

        {/* 모달 창 */}
        <Dialog open={open} onClose={handleClose} fullWidth maxWidth="md" sx={{zIndex:1060}}>

            <Box sx={{display:'flex'}}>
            {
                product.image && 
                <CardMedia
                    sx={{width:'50%'}}                            
                    component="img"
                    image={`${process.env.REACT_APP_SERVER}/img/${product.image.saved}`}
                    alt="상품 이미지"
                />
            }
                <Box sx={{width:'50%', paddingLeft:0}}>
                    <DialogTitle>name: {product.name}</DialogTitle>

                    <IconButton
                        color="inherit"
                        onClick={handleClose}
                        aria-label="close"
                        sx={{position:'absolute', right:5, top:3}}
                    >❌</IconButton>

                    <DialogTitle className="fs-6 text-secondary">ename: {product.ename}</DialogTitle>
                    
                    <DialogContent>
                        <Typography className="text-danger">price: {product.price}</Typography>
                        <Typography className="text-primary">sugar: {product.sugar}</Typography>
                        <Typography className="text-success">caffeine: {product.caffeine}</Typography>
                        <Typography>calorie: {product.calorie}</Typography>
                        <Typography className="text-warning">allergy: {product.allergy}</Typography>
                        <Typography variant="body1" gutterBottom>detail: {product.detail}</Typography>

                    </DialogContent>

                </Box>

            </Box>

            <CardActions sx={{justifyContent:'flex-end'}}>
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
            </CardActions>
        </Dialog>

        </>

    );
}
 
export default ProductCard;