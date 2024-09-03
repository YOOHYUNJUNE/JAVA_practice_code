import { AppBar, Box, Button, IconButton, List, ListItem, ListItemButton, ListItemText, Menu, Toolbar } from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import AccessibilityIcon from '@mui/icons-material/Accessibility';
import HouseSharpIcon from '@mui/icons-material/HouseSharp';
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Drawer from "./Drawer";


const Header = () => {
    const [menu, setMenu] = useState([
        {path:'/post', name:'게시물'},
        {path:'/search', name:'검색'},
    ]);

    const [menuOpen, setMenuOpen] = useState(false);

    // onClick 이동 경로 지정
    const navigate = useNavigate();

    const toggleDrawer = () => {
        setMenuOpen(prev => !prev);
    }
    
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

                <Box sx={{display: {xs: 'none', sm: 'block'}}}>
                    {
                        menu.map((m, idx) => {
                            return (
                                <Button key={idx} color='font' onClick={()=>navigate(m.path)}>
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
                            <ListItemButton onClick={()=>navigate(m.path)}>
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