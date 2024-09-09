import { alpha, AppBar, Box, Button, IconButton, InputBase, List, ListItem, ListItemButton, ListItemText, Menu, styled, Toolbar } from "@mui/material";
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
import SearchIcon from '@mui/icons-material/Search';
import { postAPI } from "../../api/services/post";
import { useAuth } from "../../hooks/useAuth";
import { jwtDecode } from "jwt-decode";
import { convertLength } from "@mui/material/styles/cssUtils";



const Header = () => {
    
    const navigate = useNavigate();

    // 액세스 토큰 가져오기
    const { accessToken, tokenCheck, logout } = useAuth();

    // 메뉴 아이콘
    let allMenu = [
        {path:'/favorite', name:'즐겨찾기', auth:["ROLE_ADMIN", "ROLE_USER"]},
        {path:'/user', name:'회원 관리', auth:["ROLE_ADMIN"]},
        {path:'/post', name:'게시물', auth:["ROLE_ADMIN", "ROLE_USER", "none"]},
        {path:'/signup', name:'회원가입', auth:["none"]},
        {path:'/login', name:'로그인', auth:["none"]},
        {path:'/logout', name:'로그아웃', auth:["ROLE_ADMIN", "ROLE_USER"]},
        {path:'/search', name:'검색', auth:["ROLE_ADMIN", "ROLE_USER", "none"]},
    ]

    const [menu, setMenu] = useState([]);

    useEffect(() => {
        // 브라우저 토큰이 유효하면
        if (tokenCheck()) {
            // 권한에 맞는 메뉴 설정
            const claims = jwtDecode(accessToken);
            console.log("로그인 유저정보: ", claims);            
            const role = claims.role;
            setMenu(allMenu.filter(m => m.auth.includes(role)));

        // 유효하지 않으면
        } else {
            // 로그아웃
            logout(() => navigate("/"))
            // "none"만 볼 수 있는 메뉴 설정
            setMenu(allMenu.filter(m => m.auth.includes("none")));
        }
    }, [accessToken]); // accessToken 변경시마다 useEffect 실행

    // 버튼에 경로 적용
    const handleClickPath = (path) => {
        switch (path) {
            case '/favorite': handleFavoriteClick(); 
                break;
            default: navigate(path);
                break;
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
            navigate("/error", {state:error.message})
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
                            if(m.path === "/search") return <MySearch key={idx}/>;
                            if(m.path === "/logout") return <Button key={idx} onClick={
                                () => logout(() => navigate("/logout"))
                            }>로그아웃</Button>
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

const MySearch = () => {

    const navigate = useNavigate();
    const [keyword, setKeyword] = useState("");

    const handleSearch = (e) => {
        if (e.key === "Enter") {            
            // keyword를 http://localhost:8080/post/search?keyword={검색어} 로 보냄
            postSearch({keyword});
        }
    }

    const postSearch = async (keyword) => {

        try {
            const res = await postAPI.searchPost(keyword);
            navigate("/search", {state:res.data}); // 검색시 /search로 이동
        } catch (error) {
            console.error(error);
        }

    }

    return (
        <Search>
        <SearchIconWrapper>
          <SearchIcon />
        </SearchIconWrapper>
        <StyledInputBase
          placeholder="Search…"
          value={keyword}
          onKeyDown={(e) => handleSearch(e)}
          onChange={(e) => setKeyword(e.target.value)}
        />
      </Search>
    )
}

const Search = styled('div')(({ theme }) => ({
    display: "inline-block",
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: alpha(theme.palette.common.white, 0.15),
    '&:hover': {
      backgroundColor: alpha(theme.palette.common.white, 0.25),
    },
    marginLeft: 0,
    width: '100%',
    [theme.breakpoints.up('sm')]: {
      marginLeft: theme.spacing(1),
      width: 'auto',
    },
  }));
  
  const SearchIconWrapper = styled('div')(({ theme }) => ({
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  }));
  
  const StyledInputBase = styled(InputBase)(({ theme }) => ({
    color: 'inherit',
    width: '100%',
    '& .MuiInputBase-input': {
      padding: theme.spacing(1, 1, 1, 0),
      // vertical padding + font size from searchIcon
      paddingLeft: `calc(1em + ${theme.spacing(4)})`,
      transition: theme.transitions.create('width'),
      [theme.breakpoints.up('sm')]: {
        width: '12ch',
        '&:focus': {
          width: '20ch',
        },
      },
    },
  }));




export default Header;