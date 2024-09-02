import { Avatar, Card, CardHeader, CardMedia, CardContent, Typography } from "@mui/material"; 

const PostCard = ({post}) => {
    return (
        <Card sx={{ maxWidth: 345 }}>
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
    );
}
 
export default PostCard;