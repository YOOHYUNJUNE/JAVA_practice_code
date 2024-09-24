import styled from '@emotion/styled';
import useInputs from "../../hooks/useInputs";
import axios from 'axios';
import { Button, FormControl, OutlinedInput } from '@mui/material';
import { userApi } from '../../api/services/users';

const Login = () => {
    const {form, handleChange, handleReset} =useInputs({email: '', password: ''})
    const {email, password} = form;

    const handleLogin = async() => {
        // 가짜 데이터이기 때문에, get 방식으로 로그인 진행 (실제로는 post 방식)
        try {
            const res = await userApi.loginUser(email, password);
            if (res.status === 200 && res.data.length == 1) {
                // 로그인
                console.log(res.data[0]);
                localStorage.setItem("loginUser", res.data[0].nickname);
            } else {
                alert("로그인 불가능");
            }
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <>
            <h1>로그인 화면</h1>

            <form>
                <FormControl sx={{ width: '100%', display: 'block'}}>
                    <OutlinedInput type="email" name="email" value={email} onChange={handleChange} />
                </FormControl>
                <FormControl sx={{ width: '100%', display: 'block'}}>
                    <OutlinedInput type="password" name="password" value={password} onChange={handleChange}  />
                </FormControl>
                <Button 
                    variant='contained'
                    type="button" 
                    sx={{display: 'block', width: '100%', marginTop: '10px'}}
                    onClick={handleLogin}
                >
                    로그인
                </Button>
            </form>
        </>
    );
}

export default Login;