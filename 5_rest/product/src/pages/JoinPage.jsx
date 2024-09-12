import { userAPI } from '../services/user';
import { useForm } from 'react-hook-form';
import { TextField, Button, Container, Typography, Box } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const JoinPage = () => {
    const { register, handleSubmit, formState: { errors } } = useForm();
    const navigate = useNavigate();

    const onSubmit = async (data) => {
        try {
            await userAPI.join(data);
            console.log("회원가입 성공");
            navigate('/login');
        } catch (error) {
            console.error('Join failed:', error);
        }
    };
    return (
        <Container maxWidth="xs">
            <Box mt={5}>
                <Typography variant="h4" align="center">Sign Up</Typography>
                <form onSubmit={handleSubmit(onSubmit)}>
                <TextField
                    fullWidth
                    margin="normal"
                    label="Name"
                    variant="outlined"
                    {...register('name', { required: 'Name is required' })}
                    error={!!errors.name}
                    helperText={errors.name?.message}
                />
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
                    Sign Up
                </Button>
                </form>
            </Box>
        </Container>
    );
}
 
export default JoinPage;