import { Avatar, Card, CardContent, CardHeader, CardMedia, Typography } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const PostDetail = () => {
    // 필요값 : id (주소창(/api/post/id)에 있음)
    const {postId} = useParams();
    const navigate = useNavigate();

    const [post, setPost] = useState();

    const getPost = async () => {
        try {
            const res = await axios.get(`http://localhost:8080/api/post/${postId}`)
            const data = res.data;
            setPost(data);
            // PostDetail은 setPost(state가 바뀔때)마다 실행되기때문에 무한 반복
            // -> useEffect() 사용
            console.log(res); 
        } catch (error) {
            navigate("/error")
        }
    }
    // 가져온 정보 보여주기
    useEffect(() => {
        getPost();        
    }, []);

    return (
        <>
        <h1>게시물 상세정보</h1>
        {post &&
        <Card sx={{width: {xs:'250px', sm:'500px', md:'800px'}}}>
            <CardHeader
                avatar={
                    <Avatar>
                        {post.id}
                    </Avatar>
                }
                title={post.title}
                subheader={post.createdAt}
            />
            <CardMedia
                component="img"
                height="194"
                image=""
                alt="게시글 이미지"
            />
            <CardContent>
                <Typography gutterBottom sx={{ color: 'text.secondary', fontSize:12 }}>
                    {post.author.name} - {post.author.email}
                </Typography>
                <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                    {post.content}
                </Typography>
            </CardContent>
        </Card>
        }
        </>
    );
}

export default PostDetail;