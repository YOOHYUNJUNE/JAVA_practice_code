import { Typography } from "@mui/material";
import { useEffect, useReducer } from "react";
import { postReducer } from "../../hooks/reducer";
import PostList from "../timeline/PostList";
import PostWrite from "../timeline/PostWrite";
import { postApi } from "../../api/services/posts";
import { userApi } from "../../api/services/users";

const TimeLine = () => {
    const [posts, dispatch] = useReducer(postReducer, []);

    const getPosts = async() => {
        try {
            const res = await postApi.getPosts();
            const posts = res.data;
            const postList = [];
            for (let post of posts) {
                const res2 = await userApi.getUser(post.userId);
                post.user = res2.data;
                postList.push(post);
            }
            if (res.status === 200) {
                dispatch({type: "SET_POSTS", payload: postList});
            }
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        getPosts();
    }, []);

    return (
        <>
            <Typography variant='h4'>타임라인</Typography>
            <PostWrite dispatch={dispatch} />
            <PostList posts={posts} />
        </>
    );
}
 
export default TimeLine;