import { Divider, Grid2 } from '@mui/material';
import axios from "axios";
import { useEffect, useState } from 'react';
import PostCard from './PostCard';

const Post = () => {

    // 백엔드로부터 가져오기
    // state만들기 -> axios사용해서 set -> useEffect 함수 적용 -> 화면
    const [postList, setPostList] = useState([]);

    const getPostList = async() => {
        try {
            const res = await axios.get("http://localhost:8080/api/post");
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
        <Divider />
        {/* 전체 리스트 */}
        <Grid2 container spacing={5}>
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