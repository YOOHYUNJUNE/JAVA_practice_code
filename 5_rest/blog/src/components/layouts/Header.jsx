import { AppBar, Box, Button, IconButton, List, ListItem, ListItemButton, ListItemText, Menu, Toolbar } from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import AccessibilityIcon from '@mui/icons-material/Accessibility';
import HouseSharpIcon from '@mui/icons-material/HouseSharp';
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Drawer from "./Drawer";
import StarBorderIcon from '@mui/icons-material/StarBorder';
import Swal from "sweetalert2";
import { favAPI } from '../../api/services/favorite';



const Header = () => {
    
    const navigate = useNavigate();

    // 메뉴 아이콘
    const [menu, setMenu] = useState([
        {path:'/favorite', name:'즐겨찾기'},
        {path:'/post', name:'게시물'},
        {path:'/search', name:'검색'},
    ]);
    // 버튼에 경로 적용
    const handleClickPath = (path) => {
        if (path == '/favorite') {
            handleFavoriteClick();
        } else {
            navigate(path);
        }
    } 


    const [menuOpen, setMenuOpen] = useState(false);

    const toggleDrawer = () => {
        setMenuOpen(prev => !prev);
    }


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

    // 즐겨찾기 수정, 삭제 함수


    //////////// 즐겨 찾기 팝업
    const handleFavoriteClick = async () => {
        await getFavList();
        const url = favList.map(fav => (
            `
            <a onclick="window.open('${fav.url}')" style="cursor:pointer;">
            <h4>
                <img
                    src="${process.env.REACT_APP_SERVER}/img/${fav.image.saved}"
                    alt="[x]"
                    style="width:30px; height:30px;"
                />                
                ${fav.title} : ${fav.url}
            </h4>
            </a>
            `
        )).join('');

        Swal.fire({
            position: 'top-end',           
            html:
            `
                <div style="display:flex; justify-content:center;">
                    <h2>즐겨찾기 목록</h2>
                    <button onclick="location.href='/favorite'" style="margin:20px;">관리</button>
                </div>
                <hr/>
                <div>${url}</div>
            `,
            showConfirmButton: false,
            showCloseButton: true,

        });
    }



    /////////////////////



    return (
        <>
        <AppBar position="static" color="main">
            <Toolbar sx={{justifyContent: 'space-between'}}>
                <IconButton
                    color="font"
                    sx={{display: {sm: 'none'}}} 
                    onClick={toggleDrawer}
                    >
                    <MenuIcon />
                </IconButton>
                <Box sx={{display: {xs: 'none', sm: 'block'}, cursor:'pointer'}}>
                    <HouseSharpIcon onClick={()=>navigate('/')} />
                </Box>


                {/* 즐겨찾기 아이콘 */}
                {/* <Box sx={{marginLeft:'auto'}}>
                    <Button color="font" onClick={handleFavoriteClick}>즐겨찾기</Button>
                </Box> */}

                {/* (임시) 즐겨찾기 목록 이동 */}
                {/* <Box sx={{display: {xs: 'none', sm: 'block'}, cursor:'pointer', marginLeft:'auto'}}>
                    <button onClick={()=>navigate("/favorite")}>즐겨찾기</button>
                </Box> */}


                {/* 메뉴 아이콘 */}
                <Box sx={{display: {xs: 'none', sm: 'block'}}}>
                    {
                        menu.map((m, idx) => {
                            return (
                                <Button key={idx} color='font' onClick={(event) => handleClickPath(m.path, event.preventDefault())}>
                                    {m.name}
                                </Button>
                            )
                        })
                    }
                </Box>



            </Toolbar>
        </AppBar>

        {/* Drawer 추가 : 화면이 작아지면 메뉴버튼에 메뉴 등장 */}
        <Drawer menuOpen={menuOpen} toggleDrawer={toggleDrawer}>
            <List>
                {
                    menu.map((m, idx) => (
                        <ListItem key={idx} disablePadding>
                            <ListItemButton onClick={(event) => handleClickPath(m.path, event.preventDefault())}>
                                <ListItemText primary={m.name} />
                            </ListItemButton>
                        </ListItem>
                    ))
                }
            </List>
        </Drawer>

        </>
    );
}

export default Header;