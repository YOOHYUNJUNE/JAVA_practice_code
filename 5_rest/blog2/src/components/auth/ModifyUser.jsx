import { useEffect, useMemo, useState } from "react";
import { userAPI } from "../../api/services/user";
import { useNavigate } from "react-router-dom";
import { Box, Button, Checkbox, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow, TableSortLabel, TextField, Typography } from "@mui/material";
import DeleteForeverTwoToneIcon from '@mui/icons-material/DeleteForeverTwoTone';
import DriveFileRenameOutlineTwoToneIcon from '@mui/icons-material/DriveFileRenameOutlineTwoTone';
import { useForm } from "react-hook-form";

const ModifyUser = () => {
    const navigate = useNavigate();

    /* 사용자 목록 state */
    const [userList, setUserList] = useState([]);

  
    /* 사용자 목록 가져오기 */
    const getUserList = async() => {
        try {
            const res = await userAPI.getUserList();
            const data = res.data;
            setUserList(data);
        } catch (error) {
            console.error(error);
            navigate("/error", {state: error.message});
        }
    }

    useEffect(() => {
        getUserList();
    }, []);


    /* 수정 화면 열림 유무 state */
    const [openEditDialog, setOpenEditDialog] = useState(false);
    /* 수정 또는 삭제 오류 내용 state */
    const [dialogErr, setDialogErr] = useState({});
    /* 수정 또는 삭제에 선택된 사용자 정보 state */
    const [seletedUserInfo, setSeletedUserInfo] = useState({});

    /* 수정 시 입력 값 state */
    const [editInput, setEditInput] = useState({name: "", password : ""});

    /* 수정 화면 값 초기화 동작 */
    const handleEditDialog = (user) => {
        setOpenEditDialog(true);
        setSeletedUserInfo(user);
    }

    /* 수정 화면 닫기 동작 */
    const closeEditDialog = () => {
        setOpenEditDialog(false);
        setDialogErr({});
    }

    /* 수정 동작 */
    const handleEdit = async (user) => {
        try {
            const name = editInput.name;
            if (name == null || name.trim() === "") {
                setDialogErr({name : "이름을 입력하세요"});
                return;
            }
            const email = user.email;
            const password = editInput.password;
            const data = {email, name, password}
            const res = await userAPI.modifyUser(data);
            if (res.status === 200) {
                setUserList(prevState => prevState.map(u => u.email == email ? res.data : u));
                closeEditDialog();
            }
        } catch (error) {
            setDialogErr({edit : "변경 실패"});
        }
    }


    return (
        <>
            <Box>
                <Typography component="h1" variant="h6" gutterBottom>회원 관리</Typography>
                <Paper>
                    <TableContainer component={Paper}>
                        <Table size="small">
                            <TableHead>
                                <TableRow>
                                    <TableCell align="center">이름</TableCell>
                                    <TableCell align="center">이메일</TableCell>
                                    <TableCell align="center">버튼</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                            {viewList.map((user) => (
                                <TableRow
                                    key={user.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell component="th" scope="row" align="center">
                                        {user.name}
                                    </TableCell>
                                    <TableCell align="center">{user.email}</TableCell>
                                    <TableCell align="center">
                                        <DeleteForeverTwoToneIcon
                                            color="main"
                                            sx={{cursor: "pointer"}}
                                            onClick={() => handleDeleteDialog(user)}
                                        />
                                        <DriveFileRenameOutlineTwoToneIcon
                                            color="main"
                                            sx={{cursor: "pointer"}}
                                            onClick={() => handleEditDialog(user)}
                                        />
                                    </TableCell>
                                </TableRow>
                            ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Paper>

                {/* 수정 창 */}
                <Dialog open={openEditDialog} onClose={closeEditDialog}>
                    <DialogTitle>해당 사용자({seletedUserInfo.email})의 이름을 변경하시겠습니까?</DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            해당 사용자의 새로운 이름과 비밀번호를 모두 입력한 후 변경 버튼을 눌러주세요.
                        </DialogContentText>
                        <TextField
                            label="Name"
                            id="name"
                            name="name"
                            placeholder={seletedUserInfo.name}
                            variant="standard"
                            margin="normal"
                            focused
                            color="main"
                            autoComplete="name"
                            onInput={(e) => setEditInput(prevState => ({...prevState, [e.target.name]: e.target.value}))}
                            sx={{ mr: 2 }}
                            helperText={dialogErr.name}
                        />
                        <TextField
                            label="Password"
                            name="password"
                            variant="standard"
                            margin="normal"
                            type="password"
                            focused
                            color="main"
                            autoComplete="password"
                            onInput={(e) => setEditInput(prevState => ({...prevState, [e.target.name]: e.target.value}))}
                            helperText={dialogErr.edit}
                        />
                    </DialogContent>
                    <DialogActions>
                        <Button size="small" variant="contained" color="main" onClick={() => handleEdit(seletedUserInfo)}>변경</Button>
                        <Button size="small" variant="contained" color="main" onClick={closeEditDialog}>취소</Button>
                    </DialogActions>
                </Dialog>

            </Box>
        </>
    );
}

export default ModifyUser;