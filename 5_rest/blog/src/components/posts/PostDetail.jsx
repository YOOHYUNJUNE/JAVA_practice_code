import { Avatar, Button, Card, CardActions, CardContent, CardHeader, CardMedia, Typography } from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import HouseSharpIcon from '@mui/icons-material/HouseSharp';
import Swal from "sweetalert2";
import { postAPI } from "../../api/services/post";

const PostDetail = () => {
    // 필요값 : id (주소창(/api/post/id)에 있음)
    const {postId} = useParams();
    const navigate = useNavigate();

    const [post, setPost] = useState();

    const getPost = async () => {
        try {
            const res = await postAPI.getPost(postId);
            const data = res.data;
            setPost(data);
            // PostDetail은 setPost(state가 바뀔때)마다 실행되기때문에 무한 반복
            // -> useEffect() 사용
        } catch (error) {
            navigate("/error")
        }
    }
    // 가져온 정보 보여주기
    useEffect(() => {
        getPost();        
    }, []);

    const handleDelete = async () => {
        const result = await Swal.fire({
            title: "비밀번호를 입력하세요.",
            input: "password",
            inputLabel: "비밀번호",
            inputPlaceholder: "비밀번호를 입력하세요.",
            inputAttributes: {
              maxlength: "20",
              autocapitalize: "off",
              autocorrect: "off"
            },
            showCancelButton: true,
            showCloseButton: true
          });

          // // 취소 분기
        //   if (result.dismiss === "close") {
        //     console.log("닫았네");
        //   } else if (result.dismiss === "cancel") {
        //     console.log("취소했네");
        //   } else {
        //     console.log("딴데 눌렀네");
        //   }

          const password = result.value;

          if (password) {
            const authorId = 1; // 로그인 기능 전 작성자 임시 부여
            try {
                const res = await postAPI.deletePost(post.id, password, authorId);
                Swal.fire({
                    title: "Good job!",
                    text: `${post.id}번 게시물이 삭제되었습니다.`,
                    icon: "success"
                });
                navigate('/post');
            } catch (error) {
                console.error(error)
                // navigate('/error');
            }
        
        }
    }

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

            {
                post.image && <CardMedia
                                component="img"
                                image={`${process.env.REACT_APP_SERVER}/img/${post.image.saved}`}
                                alt="게시글 이미지"
                                />
            }
            
            <CardContent>
                <Typography variant="body2" sx={{ color: 'text.secondary' }}>
                    {post.content}
                </Typography>
                <Typography gutterBottom sx={{ color: 'text.secondary', fontSize:12, textAlign: 'right'}} >
                    {post.author.name} {post.author.email}
                </Typography>
            </CardContent>

            <CardActions>
            <Button 
                variant="contained" 
                color="bg2" 
                size="small" 
                startIcon={<EditIcon/>}
                onClick={() => navigate(`/post/modify/${post.id}`)}
            >수정</Button>
            <Button 
                variant="contained" 
                color="sub" 
                size="small" 
                startIcon={<DeleteIcon/>}
                onClick={handleDelete}
            >삭제</Button>
            <Button 
                variant="contained" 
                color="bg1" 
                size="small"
                startIcon={<HouseSharpIcon/>}
                onClick={() => navigate("/post")}
            >메뉴</Button>
            </CardActions>

        </Card>
        }
        </>
    );
}

export default PostDetail;