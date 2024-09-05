import { Button, Divider, Grid2 } from '@mui/material';
import axios from "axios";
import { useEffect, useState, useReducer } from 'react';
import PostCard from './FavoriteCard';
import { useNavigate } from 'react-router-dom';
import { postAPI } from '../../api/services/post';
import { favAPI } from '../../api/services/favorite';
import FavoriteCard from './FavoriteCard';



// // 실시간 반영
// const favoriteReducer = (state, action) => {
//     switch (action.type) {
//         case "SET_FAV":
//             return action.payload;
//         case "ADD_FAV":
//             return [...state, action.payload];
//         case "EDIT_FAV":
//             return state.map(p => p.id == action.payload.id ? action.payload : p);
//         case "DELETE_FAV":
//             return state.filter(p => p.id != action.payload.id);
//     }
// }



const Favorite = () => {
    // // reducer : 실시간 반영
    // const [favList, dispatch] = useReducer(favoriteReducer, []);

    // onClick 이동 경로 지정
    const navigate = useNavigate();

    // 백엔드로부터 가져오기
    const [favList, setFavList] = useState([]);

    const getFavList = async() => {
        try {
            const res = await favAPI.getFavList();
            const data = res.data;            
            setFavList(data);
        } catch (error) {
            console.error(error);
        }
    }
    
    useEffect(() => {
        getFavList();
    }, []);
   
    return (
        <>
        <h1>즐겨찾기 목록</h1>
        {/* 즐겨찾기 양식 */}
        <Button variant="contained" color='main' onClick={() => navigate("/favorite/write")}>즐겨찾기 추가</Button>
        <Divider />
        {/* 전체 리스트 */}
        <Grid2 container spacing={2}>
        {
            favList.map(fav => (
                <FavoriteCard key={fav.key} favorite={fav}></FavoriteCard>
            ))
        }
        </Grid2>
        </>
    );
}
 
export default Favorite;