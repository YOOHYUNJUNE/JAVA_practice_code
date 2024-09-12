import { useForm } from 'react-hook-form';
import { TextField, Button, Container, Typography, Box } from '@mui/material';
import { userAPI } from '../services/user';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';


const LoginPage = () => {
    const { register, handleSubmit, formState: { errors }, setError } = useForm();
    const [, setCookie] = useCookies(['accessToken']);
    const navigate = useNavigate();

    const onSubmit = async (data) => {
        try {
            const response = await userAPI.login(data);
            console.log("로그인 성공");
            setCookie('accessToken', response.data.accessToken, { path: '/' });
            navigate('/products');
        } catch (error) {
            if (error.status === 401) {
                setError("email", { type: "manual", message: "로그인 실패! 아이디를 확인해주세요" });
                setError("password", { type: "manual", message: "로그인 실패! 비밀번호를 확인해주세요" });
            }
        }
    };
    
    return (
        <Container maxWidth="xs">
            <Box mt={5}>
                <Typography variant="h4" align="center">Login</Typography>
                <form onSubmit={handleSubmit(onSubmit)}>
                <TextField
                    fullWidth
                    margin="normal"
                    label="Email"
                    variant="outlined"
                    {...register('email', { required: 'Email is required' })}
                    error={!!errors.email}
                    helperText={errors.email?.message}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    label="Password"
                    type="password"
                    variant="outlined"
                    {...register('password', { required: 'Password is required' })}
                    error={!!errors.password}
                    helperText={errors.password?.message}
                />
                <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    fullWidth
                    sx={{ mt: 2 }}
                >
                    Login
                </Button>
                </form>
            </Box>
        </Container>
    );
}
 
export default LoginPage;