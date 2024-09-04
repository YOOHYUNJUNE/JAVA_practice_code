import { useTheme } from "@emotion/react";
import { Avatar, Card, CardHeader, CardMedia, CardContent, Typography, IconButton, CardActions, Button } from "@mui/material"; 
import { useNavigate, useParams } from "react-router-dom";
import SaveIcon from "@mui/icons-material/Save";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import Swal from "sweetalert2";
import { favAPI } from "../../api/services/favorite";
import { useEffect, useState } from "react";



const FavoriteCard = ( { favorite }) => {
    const navigate = useNavigate();
    const theme = useTheme();

    const [fav, setFav] = useState();

    // 가져온 정보 보여주기
    useEffect(() => {
        setFav();        
    }, []);

    // 즐겨찾기 삭제
    const handleDelete = async () => {
        const result = await Swal.fire({
            title: "즐겨찾기 삭제",
            text: `${favorite.title}이 삭제됩니다.`,
            showCancelButton: true,
            confirmButtonText: "삭제",
            cancelButtonText: "취소"
            
          });

          // // 취소 분기
        //   if (result.dismiss === "close") {
        //     console.log("닫았네");
        //   } else if (result.dismiss === "cancel") {
        //     console.log("취소했네");
        //   } else {
        //     console.log("딴데 눌렀네");
        //   }

        if (result.isConfirmed) {
            try {
                await favAPI.deleteFav(favorite.id);
                Swal.fire({
                    title: "Good job!",
                    text: `${favorite.title} 즐겨찾기가 삭제되었습니다.`,
                    icon: "success"
                });
                navigate('/favorite');
            } catch (error) {
                console.error(error)
                // navigate('/error');
            }
        }
        
        
    }

    return (
        <Card sx={{ maxWidth: 345, cursor: 'pointer' }}>
            <CardHeader                
                title={favorite.title}
                subheader={favorite.url}
                // onClick={() => window.location.href = favorite.url}   
                onClick={() => window.open(favorite.url)}                             
            />
            <CardActions>
            <Button 
                variant="contained" 
                color="bg2" 
                size="small" 
                startIcon={<EditIcon/>}
                onClick={() => navigate(`/favorite/modify/${favorite.id}`, {state:fav})}
            >수정</Button>
            <Button 
                variant="contained" 
                color="sub" 
                size="small" 
                startIcon={<DeleteIcon/>}
                onClick={handleDelete}
            >삭제</Button>
            </CardActions>

        </Card>
    );
}
 
export default FavoriteCard;