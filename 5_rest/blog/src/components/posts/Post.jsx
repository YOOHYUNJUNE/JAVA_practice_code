import { Box, Button, Divider, Grid2 } from '@mui/material';
import axios from "axios";
import { useEffect, useState } from 'react';
import PostCard from './PostCard';
import { useLocation, useNavigate } from 'react-router-dom';
import { postAPI } from '../../api/services/post';

const Post = () => {

    // 검색결과 state로 가져오기
    const {state} = useLocation();

    // onClick 이동 경로 지정
    const navigate = useNavigate();

    // 백엔드로부터 가져오기
    // state만들기 -> axios사용해서 set -> useEffect 함수 적용 -> 화면
    const [postList, setPostList] = useState([]);

    const getPostList = async() => {

        // 검색 결과에 따른 분기
        if (state) {
            setPostList(state);
        } else {
            try {
                const res = await postAPI.getPostList();
                const data = res.data;            
                setPostList(data);
            } catch (error) {
                navigate("/error", {state:error.message})
            }
        }

    }
    
    useEffect(() => {
        getPostList();
    }, [state]); // state가 변경시에도 useEffect가 작동되게
   
    return (
        <>
        <h1>포스트</h1>
        {/* 글쓰기 양식 */}
        <Button variant="contained" color='main' onClick={() => navigate("/post/write")}>글쓰기</Button>
        <Divider />
        {/* 전체 리스트 */}
        <Box sx={{flexGrow:1}}>
            <Grid2 container spacing={2} columns={24}>
            {
                postList.map(post => (
                    <Grid2 size={6}>
                        <PostCard key={post.key} post={post}></PostCard>
                    </Grid2>
                ))
            }
            </Grid2>
        </Box>

        </>
    );
}
 
export default Post;