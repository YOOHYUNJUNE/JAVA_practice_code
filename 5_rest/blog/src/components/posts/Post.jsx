import { Button, Divider, Grid2 } from '@mui/material';
import axios from "axios";
import { useEffect, useState } from 'react';
import PostCard from './PostCard';
import { useNavigate } from 'react-router-dom';
import { postAPI } from '../../api/services/post';

const Post = () => {

    // onClick 이동 경로 지정
    const navigate = useNavigate();

    // 백엔드로부터 가져오기
    // state만들기 -> axios사용해서 set -> useEffect 함수 적용 -> 화면
    const [postList, setPostList] = useState([]);

    const getPostList = async() => {
        try {
            const res = await postAPI.getPostList();
            const data = res.data;            
            setPostList(data);
        } catch (error) {
            console.error(error);
        }
    }
    
    useEffect(() => {
        getPostList();
    }, []);
   
    return (
        <>
        <h1>포스트</h1>
        {/* 글쓰기 양식 */}
        <Button variant="contained" color='main' onClick={() => navigate("/post/write")}>글쓰기</Button>
        <Divider />
        {/* 전체 리스트 */}
        <Grid2 container direction={"column"} spacing={2}>
        {
            postList.map(post => (
                <PostCard key={post.key} post={post}></PostCard>
            ))
        }
        </Grid2>
        </>
    );
}
 
export default Post;